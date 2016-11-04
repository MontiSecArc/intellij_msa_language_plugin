// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface MSAIdentityStatement extends MSACompositeElement {

  @NotNull
  List<MSAComponentInstanceName> getComponentInstanceNameList();

  @NotNull
  List<MSAPortInstanceName> getPortInstanceNameList();

  @Nullable
  MSAStereotype getStereotype();

}
