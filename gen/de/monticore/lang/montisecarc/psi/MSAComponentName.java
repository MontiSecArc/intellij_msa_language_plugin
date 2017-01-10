// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;

public interface MSAComponentName extends MSANamedElement {

  @NotNull
  PsiElement getId();

  @NotNull
  String getName();

  @NotNull
  PsiElement setName(String newName);

  @NotNull
  PsiReference[] getReferences();

  @NotNull
  PsiElement getNameIdentifier();

}
