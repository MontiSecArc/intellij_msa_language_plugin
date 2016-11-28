// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface MSAConnector extends MSACompositeElement {

  @NotNull
  MSAConnectSource getConnectSource();

  @NotNull
  List<MSAConnectTarget> getConnectTargetList();

  @Nullable
  MSAJavaClassReference getJavaClassReference();

}
