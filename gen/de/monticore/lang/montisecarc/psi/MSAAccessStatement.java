// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface MSAAccessStatement extends MSACompositeElement {

  @NotNull
  List<MSAPortAccessRole> getPortAccessRoleList();

  @NotNull
  List<MSARoleName> getRoleNameList();

  @Nullable
  MSAStereotype getStereotype();

  @Nullable
  MSASuppressAnnotation getSuppressAnnotation();

}
