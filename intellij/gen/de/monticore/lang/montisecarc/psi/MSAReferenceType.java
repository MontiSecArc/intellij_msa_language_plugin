// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import kotlin.jvm.JvmStatic;

public interface MSAReferenceType extends MSACompositeElement {

  @NotNull
  List<MSAComponentName> getComponentNameList();

  @NotNull
  List<MSAReferenceType> getReferenceTypeList();

  @JvmStatic
  @NotNull
  PsiReference[] getReferences();

}
