package de.monticore.lang.montisecarc.formatting.settings

import com.intellij.application.options.CodeStyleAbstractConfigurable
import com.intellij.application.options.CodeStyleAbstractPanel
import com.intellij.psi.codeStyle.CodeStyleSettings

/**
 *  Copyright 2016 thomasbuning

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

class MSACodeStyleConfigurable(settings: CodeStyleSettings, cloneSettings: CodeStyleSettings) : CodeStyleAbstractConfigurable(settings, cloneSettings, "MontiSecArc") {

    override fun createPanel(settings: CodeStyleSettings): CodeStyleAbstractPanel {
        return MSACodeStyleMainPanel(currentSettings, settings)
    }

    override fun getHelpTopic(): String? {
        return "instanceName.settingsdialog.codestyle.squirrel"
    }
}