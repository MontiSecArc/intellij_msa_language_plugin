/*
 * Copyright 2010-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.monticore.lang.montisecarc.psi.cli;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.StandardFileSystems;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.VirtualFileSystem;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import de.monticore.lang.montisecarc.MSAFileType;
import de.monticore.lang.montisecarc.psi.MSAFile;
import kotlin.Unit;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CompileEnvironmentUtil {
    private static final Logger LOG = Logger.getInstance(CompileEnvironmentUtil.class);


    @NotNull
    public static List<MSAFile> getMSAFiles(
            @NotNull final Project project,
            @NotNull Collection<String> sourceRoots,
            @NotNull CompilerConfiguration configuration,
            @NotNull Function1<String, Unit> reportError
    ) throws IOException {
        final VirtualFileSystem localFileSystem = VirtualFileManager.getInstance().getFileSystem(StandardFileSystems.FILE_PROTOCOL);

        final Set<VirtualFile> processedFiles = Sets.newHashSet();
        final List<MSAFile> result = Lists.newArrayList();

        for (String sourceRootPath : sourceRoots) {
            if (sourceRootPath == null) {
                continue;
            }

            VirtualFile vFile = localFileSystem.findFileByPath(sourceRootPath);
            if (vFile == null) {
                String message = "Source file or directory not found: " + sourceRootPath;

                /*File moduleFilePath = configuration.get(JVMConfigurationKeys.MODULE_XML_FILE);
                if (moduleFilePath != null) {
                    String moduleFileContent = FileUtil.loadFile(moduleFilePath);
                    LOG.warn(message +
                              "\n\nmodule file path: " + moduleFilePath +
                              "\ncontent:\n" + moduleFileContent);
                }*/

                reportError.invoke(message);
                continue;
            }
            if (!vFile.isDirectory() && vFile.getFileType() != MSAFileType.Factory.getInstance()) {
                reportError.invoke("Source entry is not a Kotlin file: " + sourceRootPath);
                continue;
            }

            SequencesKt.forEach(FilesKt.walkTopDown(new File(sourceRootPath)), file -> {
                if (file.isFile()) {
                    VirtualFile virtualFile = localFileSystem.findFileByPath(file.getAbsolutePath());
                    if (virtualFile != null && !processedFiles.contains(virtualFile)) {
                        processedFiles.add(virtualFile);
                        PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
                        if (psiFile instanceof MSAFile) {
                            result.add((MSAFile) psiFile);
                        }
                    }
                }
                return Unit.INSTANCE;
            });
        }

        return result;
    }
}
