// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import kotlin.jvm.JvmStatic;

public interface MSAComponentInstanceName extends MSACompositeElement {

  @NotNull
  PsiElement getId();

  @JvmStatic
  @NotNull
  String getName();

  @JvmStatic
  @NotNull
  PsiElement setName(String newName);

  @JvmStatic
  @NotNull
  PsiReference[] getReferences();

}