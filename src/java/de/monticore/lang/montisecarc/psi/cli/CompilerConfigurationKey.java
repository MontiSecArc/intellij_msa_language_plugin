package de.monticore.lang.montisecarc.psi.cli;

import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Copyright 2016 Thomas Buning
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
public class CompilerConfigurationKey<T> {
    Key<T> ideaKey;

    private CompilerConfigurationKey(@NotNull @NonNls String name) {
        ideaKey = Key.create(name);
    }

    public static <T> CompilerConfigurationKey<T> create(@NotNull @NonNls String name) {
        return new CompilerConfigurationKey<T>(name);
    }

    @Override
    public String toString() {
        return ideaKey.toString();
    }
}

