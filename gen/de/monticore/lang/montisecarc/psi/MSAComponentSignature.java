// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface MSAComponentSignature extends MSACompositeElement {

  @Nullable
  MSAComponentExtension getComponentExtension();

  @Nullable
  MSAComponentInstanceName getComponentInstanceName();

  @NotNull
  MSAComponentNameWithType getComponentNameWithType();

  @NotNull
  List<MSAJavaClassReference> getJavaClassReferenceList();

  @NotNull
  MSAComponentNameWithType getComponentName();

  @Nullable
  MSAComponentNameWithType getComponentExtensionName();

}
