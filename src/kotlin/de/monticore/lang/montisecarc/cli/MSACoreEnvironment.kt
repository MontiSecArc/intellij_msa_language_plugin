package de.monticore.lang.montisecarc.cli

import com.google.common.collect.Sets
import com.intellij.codeInsight.ContainerProvider
import com.intellij.codeInsight.runner.JavaMainMethodProvider
import com.intellij.core.CoreApplicationEnvironment
import com.intellij.core.CoreJavaFileManager
import com.intellij.core.JavaCoreApplicationEnvironment
import com.intellij.core.JavaCoreProjectEnvironment
import com.intellij.lang.java.JavaParserDefinition
import com.intellij.mock.MockApplication
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.PathManager
import com.intellij.openapi.application.TransactionGuard
import com.intellij.openapi.application.TransactionGuardImpl
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.extensions.Extensions
import com.intellij.openapi.extensions.ExtensionsArea
import com.intellij.openapi.fileTypes.FileTypeExtensionPoint
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.io.FileUtilRt
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.PersistentFSConstants
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.impl.ZipHandler
import com.intellij.psi.FileContextProvider
import com.intellij.psi.PsiElementFinder
import com.intellij.psi.augment.PsiAugmentProvider
import com.intellij.psi.augment.TypeAnnotationModifier
import com.intellij.psi.compiled.ClassFileDecompilers
import com.intellij.psi.impl.JavaClassSupersImpl
import com.intellij.psi.impl.PsiElementFinderImpl
import com.intellij.psi.impl.PsiTreeChangePreprocessor
import com.intellij.psi.impl.compiled.ClsCustomNavigationPolicy
import com.intellij.psi.impl.file.impl.JavaFileManager
import com.intellij.psi.impl.source.resolve.reference.PsiReferenceContributorEP
import com.intellij.psi.meta.MetaDataContributor
import com.intellij.psi.stubs.*
import com.intellij.psi.util.JavaClassSupers
import com.intellij.util.indexing.FileBasedIndex
import com.intellij.util.indexing.FileBasedIndexExtension
import com.intellij.util.io.URLUtil
import de.monticore.lang.montisecarc.MSAFileType
import de.monticore.lang.montisecarc.MSAParserDefinition
import de.monticore.lang.montisecarc.cli.index.JavaRoot
import de.monticore.lang.montisecarc.cli.index.JvmDependenciesIndex
import de.monticore.lang.montisecarc.cli.plugins.ComponentRegistrar
import de.monticore.lang.montisecarc.psi.MSAFile
import de.monticore.lang.montisecarc.psi.cli.CompileEnvironmentUtil
import de.monticore.lang.montisecarc.psi.cli.CompilerConfiguration
import de.monticore.lang.montisecarc.psi.cli.CompilerConfigurationKey
import de.monticore.lang.montisecarc.psi.cli.CompilerMessageSeverity
import org.jetbrains.annotations.TestOnly
import java.io.File
import java.util.*

/**
 * Copyright 2016 Thomas Buning
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class MSACoreEnvironment private constructor(
        parentDisposable: Disposable,
        applicationEnvironment: JavaCoreApplicationEnvironment,
        configuration: CompilerConfiguration
) {

    private val projectEnvironment: JavaCoreProjectEnvironment = object : MSACoreProjectEnvironment(parentDisposable, applicationEnvironment) {
        override fun preregisterServices() {
            registerProjectExtensionPoints(Extensions.getArea(project))
        }
    }
    private val sourceFiles = ArrayList<MSAFile>()

    val configuration: CompilerConfiguration = configuration.copy()

    init {
        PersistentFSConstants.setMaxIntellisenseFileSize(FileUtilRt.LARGE_FOR_CONTENT_LOADING)
    }

    init {
        val project = projectEnvironment.project

        registerProjectServicesForCLI(projectEnvironment)
        registerProjectServices(projectEnvironment)

        sourceFiles.addAll(CompileEnvironmentUtil.getMSAFiles(project, getSourceRootsCheckingForDuplicates(), this.configuration, {
            message ->
            report(CompilerMessageSeverity.ERROR, message)
        }))
        sourceFiles.sortedWith(Comparator<MSAFile> { o1, o2 -> o1.virtualFile.path.compareTo(o2.virtualFile.path, ignoreCase = true) })

        for (registrar in configuration.getList(ComponentRegistrar.PLUGIN_COMPONENT_REGISTRARS)) {
            registrar.registerProjectComponents(project, configuration)
        }
    }

    private val applicationEnvironment: CoreApplicationEnvironment
        get() = projectEnvironment.environment

    val application: MockApplication
        get() = applicationEnvironment.application

    val project: Project
        get() = projectEnvironment.project

    val sourceLinesOfCode: Int by lazy { countLinesOfCode(sourceFiles) }

    fun countLinesOfCode(sourceFiles: List<MSAFile>): Int  =
            sourceFiles.sumBy {
                val text = it.text
                StringUtil.getLineBreakCount(it.text) + (if (StringUtil.endsWithLineBreak(text)) 0 else 1)
            }

    fun <T>addComponent(clazz: Class<T>, instance: T) {

        with(projectEnvironment.project) {

            addComponent(clazz, instance)
        }
    }

    fun <T>addComponentToApplication(clazz: Class<T>, instance: T) {

        with(application) {

            addComponent(clazz, instance)
        }
    }

    private fun Iterable<ContentRoot>.classpathRoots(): List<JavaRoot> =
            filterIsInstance(JvmContentRoot::class.java).mapNotNull {
                javaRoot -> contentRootToVirtualFile(javaRoot)?.let { virtualFile ->

                val rootType = when (javaRoot) {
                    is JavaSourceRoot -> JavaRoot.RootType.SOURCE
                    is JvmClasspathRoot -> JavaRoot.RootType.BINARY
                    else -> throw IllegalStateException()
                }

                JavaRoot(virtualFile, rootType)
            }
            }

    private fun updateClasspathFromRootsIndex(index: JvmDependenciesIndex) {
        index.indexedRoots.forEach {
            projectEnvironment.addSourcesToClasspath(it.file)
        }
    }

    val appendJavaSourceRootsHandler = fun(roots: List<File>) {
        updateClasspath(roots.map { JavaSourceRoot(it, null) })
    }

    init {
        project.putUserData(APPEND_JAVA_SOURCE_ROOTS_HANDLER_KEY, appendJavaSourceRootsHandler)
    }

    fun updateClasspath(roots: List<ContentRoot>): List<File>? {
        return emptyList()
    }

    @Suppress("unused") // used externally
    @Deprecated("Use updateClasspath() instead.", ReplaceWith("updateClasspath(files)"))
    fun tryUpdateClasspath(files: Iterable<File>): List<File>? = updateClasspath(files.map(::JvmClasspathRoot))

    fun contentRootToVirtualFile(root: JvmContentRoot): VirtualFile? {
        when (root) {
            is JvmClasspathRoot -> {
                return if (root.file.isFile) findJarRoot(root) else findLocalDirectory(root)
            }
            is JavaSourceRoot -> {
                return if (root.file.isDirectory) findLocalDirectory(root) else null
            }
            else -> throw IllegalStateException("Unexpected root: $root")
        }
    }

    private fun findLocalDirectory(root: JvmContentRoot): VirtualFile? {
        val path = root.file
        val localFile = findLocalDirectory(path.absolutePath)
        if (localFile == null) {
            report(CompilerMessageSeverity.WARNING, "Classpath entry points to a non-existent location: $path")
            return null
        }
        return localFile
    }

    internal fun findLocalDirectory(absolutePath: String): VirtualFile? =
            applicationEnvironment.localFileSystem.findFileByPath(absolutePath)

    private fun findJarRoot(root: JvmClasspathRoot): VirtualFile? {
        val path = root.file
        val jarFile = applicationEnvironment.jarFileSystem.findFileByPath("$path${URLUtil.JAR_SEPARATOR}")
        if (jarFile == null) {
            report(CompilerMessageSeverity.WARNING, "Classpath entry points to a file that is not a JAR archive: $path")
            return null
        }
        return jarFile
    }

    private fun getSourceRootsCheckingForDuplicates(): Collection<String> {
        val uniqueSourceRoots = Sets.newLinkedHashSet<String>()

        configuration.MSASourceRoots.forEach { path ->
            if (!uniqueSourceRoots.add(path)) {
                //WARNING Duplicate
            }
        }

        return uniqueSourceRoots
    }

    fun getSourceFiles(): List<MSAFile> = sourceFiles

    private fun report(severity: CompilerMessageSeverity, message: String) {

    }

    companion object {
        init {
            System.getProperties().setProperty("idea.plugins.compatible.build", "162.9999")
        }

        private val APPLICATION_LOCK = Object()
        private var ourApplicationEnvironment: JavaCoreApplicationEnvironment? = null
        private var ourProjectCount = 0

        @JvmStatic fun createForProduction(
                parentDisposable: Disposable, configuration: CompilerConfiguration, configFilePaths: List<String>
        ): MSACoreEnvironment {
            val appEnv = getOrCreateApplicationEnvironmentForProduction(configuration, configFilePaths)
            // Disposing of the environment is unsafe in production then parallel builds are enabled, but turning it off universally
            // breaks a lot of tests, therefore it is disabled for production and enabled for tests
            if (!(System.getProperty("msa.environment.keepalive").toBooleanLenient() ?: false)) {
                // JPS may run many instances of the compiler in parallel (there's an option for compiling independent modules in parallel in IntelliJ)
                // All projects share the same ApplicationEnvironment, and when the last project is disposed, the ApplicationEnvironment is disposed as well
                Disposer.register(parentDisposable, Disposable {
                    synchronized (APPLICATION_LOCK) {
                        if (--ourProjectCount <= 0) {
                            disposeApplicationEnvironment()
                        }
                    }
                })
            }
            val environment = MSACoreEnvironment(parentDisposable, appEnv, configuration)

            synchronized (APPLICATION_LOCK) {
                ourProjectCount++
            }
            return environment
        }

        @TestOnly
        @JvmStatic fun createForTests(
                parentDisposable: Disposable, configuration: CompilerConfiguration, extensionConfigs: List<String>
        ): MSACoreEnvironment {
            // Tests are supposed to create a single project and dispose it right after use
            return MSACoreEnvironment(parentDisposable, createApplicationEnvironment(parentDisposable, configuration, extensionConfigs), configuration)
        }

        // used in the daemon for jar cache cleanup
        val applicationEnvironment: JavaCoreApplicationEnvironment? get() = ourApplicationEnvironment

        private fun getOrCreateApplicationEnvironmentForProduction(configuration: CompilerConfiguration, configFilePaths: List<String>): JavaCoreApplicationEnvironment {
            synchronized (APPLICATION_LOCK) {
                if (ourApplicationEnvironment != null)
                    return ourApplicationEnvironment!!

                val parentDisposable = Disposer.newDisposable()
                ourApplicationEnvironment = createApplicationEnvironment(parentDisposable, configuration, configFilePaths)
                ourProjectCount = 0
                Disposer.register(parentDisposable, Disposable {
                    synchronized (APPLICATION_LOCK) {
                        ourApplicationEnvironment = null
                    }
                })
                return ourApplicationEnvironment!!
            }
        }

        fun disposeApplicationEnvironment() {
            synchronized (APPLICATION_LOCK) {
                val environment = ourApplicationEnvironment ?: return
                ourApplicationEnvironment = null
                Disposer.dispose(environment.parentDisposable)
                ZipHandler.clearFileAccessorCache()
            }
        }

        private fun createApplicationEnvironment(parentDisposable: Disposable, configuration: CompilerConfiguration, configFilePaths: List<String>): JavaCoreApplicationEnvironment {
            Extensions.cleanRootArea(parentDisposable)
            registerAppExtensionPoints()
            val applicationEnvironment = JavaCoreApplicationEnvironment(parentDisposable)

            for (configPath in configFilePaths) {
                registerApplicationExtensionPointsAndExtensionsFrom(configuration, configPath)
            }

            registerApplicationServicesForCLI(applicationEnvironment)
            registerApplicationServices(applicationEnvironment)
            return applicationEnvironment
        }

        private fun registerAppExtensionPoints() {
            CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), BinaryFileStubBuilders.EP_NAME, FileTypeExtensionPoint::class.java)
            CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), FileContextProvider.EP_NAME, FileContextProvider::class.java)
            //
            CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), com.intellij.psi.meta.MetaDataContributor.EP_NAME, MetaDataContributor::class.java)
            CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), PsiAugmentProvider.EP_NAME, PsiAugmentProvider::class.java)
            CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), JavaMainMethodProvider.EP_NAME, JavaMainMethodProvider::class.java)
            //
            CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), com.intellij.codeInsight.ContainerProvider.EP_NAME, ContainerProvider::class.java)
            CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), ClsCustomNavigationPolicy.EP_NAME, ClsCustomNavigationPolicy::class.java)
            CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), ClassFileDecompilers.EP_NAME, ClassFileDecompilers.Decompiler::class.java)
            //
            CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), TypeAnnotationModifier.EP_NAME, TypeAnnotationModifier::class.java)

            CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), StubIndexExtension.EP_NAME, StubIndexExtension::class.java)
            CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), StubElementTypeHolderEP.EP_NAME, StubElementTypeHolderEP::class.java)

            //CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), PsiReferenceContributor.EP_NAME, PsiReferenceContributor::class.java)
            CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), "com.intellij.psi.referenceContributor", PsiReferenceContributorEP::class.java)
        }

        private fun registerApplicationExtensionPointsAndExtensionsFrom(configuration: CompilerConfiguration, configFilePath: String) {

            val configs = File(configFilePath)

            if(configs.exists()) {

                val pluginRoot = configs.parentFile.parentFile.parentFile

                val filePath = "extensions/" + configs.name

                CoreApplicationEnvironment.registerExtensionPointAndExtensions(pluginRoot, filePath, Extensions.getRootArea())
            }
        }

        private fun registerApplicationServicesForCLI(applicationEnvironment: JavaCoreApplicationEnvironment) {
            // ability to get text from annotations xml files‘
            applicationEnvironment.registerFileType(PlainTextFileType.INSTANCE, "xml")
            applicationEnvironment.registerParserDefinition(JavaParserDefinition())
        }

        // made public for Upsource
        @JvmStatic fun registerApplicationServices(applicationEnvironment: JavaCoreApplicationEnvironment) {
            with(applicationEnvironment) {
                registerFileType(MSAFileType.instance, MSAFileType.EXTENSION)
                registerParserDefinition(MSAParserDefinition())
                application.registerService(JavaClassSupers::class.java, JavaClassSupersImpl::class.java)
                application.registerService(TransactionGuard::class.java, TransactionGuardImpl::class.java)
            }
        }

        private fun registerProjectExtensionPoints(area: ExtensionsArea) {
            CoreApplicationEnvironment.registerExtensionPoint(area, PsiTreeChangePreprocessor.EP_NAME, PsiTreeChangePreprocessor::class.java)
            CoreApplicationEnvironment.registerExtensionPoint(area, PsiElementFinder.EP_NAME, PsiElementFinder::class.java)

            CoreApplicationEnvironment.registerExtensionPoint(area, FileBasedIndexExtension.EXTENSION_POINT_NAME, FileBasedIndexExtension::class.java)
        }

        // made public for Upsource
        @JvmStatic fun registerProjectServices(projectEnvironment: JavaCoreProjectEnvironment) {
            with (projectEnvironment.project) {

                /*val kotlinScriptDefinitionProvider = KotlinScriptDefinitionProvider()
                registerService(KotlinScriptDefinitionProvider::class.java, kotlinScriptDefinitionProvider)
                registerService(KotlinScriptExternalImportsProvider::class.java, KotlinScriptExternalImportsProvider(projectEnvironment.project, kotlinScriptDefinitionProvider))
                registerService(KotlinJavaPsiFacade::class.java, KotlinJavaPsiFacade(this))
                registerService(KtLightClassForFacade.FacadeStubCache::class.java, KtLightClassForFacade.FacadeStubCache(this))*/
            }
        }

        private fun registerProjectServicesForCLI(projectEnvironment: JavaCoreProjectEnvironment) {
            with (projectEnvironment.project) {
                registerService(CoreJavaFileManager::class.java, ServiceManager.getService(this, JavaFileManager::class.java) as CoreJavaFileManager)

                val area = Extensions.getArea(this)

                area.getExtensionPoint(PsiElementFinder.EP_NAME).registerExtension(
                        PsiElementFinderImpl(this, ServiceManager.getService(this, JavaFileManager::class.java)))
            }
        }
    }
}

class JVMConfigurationKeys {

    companion object {
        val CONTENT_ROOTS: CompilerConfigurationKey<List<ContentRoot>> = CompilerConfigurationKey.create<List<ContentRoot>>("content roots")
    }
}

interface ContentRoot

val APPEND_JAVA_SOURCE_ROOTS_HANDLER_KEY = Key<(List<File>) -> Unit>("AppendJavaSourceRootsHandlerKey")

fun String?.toBooleanLenient(): Boolean? = when (this?.toLowerCase()) {
    null -> false
    in listOf("", "yes", "true", "on", "y") -> true
    in listOf("no", "false", "off", "n") -> false
    else -> null
}

fun getPathUtilJar(): File {
    return getResourcePathForClass(MSACoreEnvironment::class.java)
}

fun getResourcePathForClass(aClass: Class<*>): File {
    val path = "/" + aClass.name.replace('.', '/') + ".class"
    val resourceRoot = PathManager.getResourceRoot(aClass, path) ?: throw IllegalStateException("Resource not found: " + path)
    return File(resourceRoot).absoluteFile
}

data class MSASourceRoot(val path: String): ContentRoot

fun CompilerConfiguration.addMSASourceRoot(source: String) {
    add(JVMConfigurationKeys.CONTENT_ROOTS, MSASourceRoot(source))
}

fun CompilerConfiguration.addMSASourceRoots(sources: List<String>): Unit =
        sources.forEach { addMSASourceRoot(it) }

val CompilerConfiguration.MSASourceRoots: List<String>
    get() = get(JVMConfigurationKeys.CONTENT_ROOTS)?.filterIsInstance<MSASourceRoot>()?.map { it.path }.orEmpty()