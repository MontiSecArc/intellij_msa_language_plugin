package de.monticore.lang.montisecarc;

import de.monticore.lang.montisecarc.actions.MSAGenerateGraphTest;
import de.monticore.lang.montisecarc.annotation.*;
import de.monticore.lang.montisecarc.codeinsight.CodeInsightTests;
import de.monticore.lang.montisecarc.generator.graph.GraphGeneratorTest;
import de.monticore.lang.montisecarc.importing.data.NewMSAFileFromImporterTest;
import de.monticore.lang.montisecarc.parser.ParserTests;
import de.monticore.lang.montisecarc.policy.PolicyLoaderTest;
import de.monticore.lang.montisecarc.psi.impl.MSAPsiImplUtilTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Copyright 2017 thomasbuning
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        MSAGenerateGraphTest.class,
        CVEAnnotatorTest.class,
        ImportClassFixTest.class,
        MainComponentAnnotatorTest.class,
        MSAComponentInstanceAnnotatorTest.class,
        MSAComponentTypAnnotatorTest.class,
        MSAInvalidPackageAnnotatorTest.class,
        MSAInvalidSemanticAnnotatorTest.class,
        SingleStatementsAnnotatorTest.class,
        CodeInsightTests.class,
        GraphGeneratorTest.class,
        NewMSAFileFromImporterTest.class,
        ParserTests.class,
        PolicyLoaderTest.class,
        MSAPsiImplUtilTest.class
})
public class IDETestSuite {
}
