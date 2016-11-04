package de.monticore.lang.montisecarc.psi.impl

import com.intellij.testFramework.ParsingTestCase
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import de.monticore.lang.montisecarc.MSAParserDefinition
import org.junit.Test

/**
 * Created by thomasbuning on 01.11.16.
 */
class MSAPsiImplUtilTest: LightCodeInsightFixtureTestCase() {

    @Test
    fun componentQualifiedName() {

        myFixture.file

    }

    override fun getTestDataPath(): String {
        return "intellij/src/test/testData"
    }
}