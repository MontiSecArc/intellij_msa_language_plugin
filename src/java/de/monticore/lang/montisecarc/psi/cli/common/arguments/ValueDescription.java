package de.monticore.lang.montisecarc.psi.cli.common.arguments;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ValueDescription {
    @NotNull
    String value();
}
