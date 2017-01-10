// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface MSAIdentityIdentifier extends MSACompositeElement {

  @NotNull
  List<MSAComponentInstanceName> getComponentInstanceNameList();

  @Nullable
  MSAPortInstanceName getPortInstanceName();

  @Nullable
  MSAPortElement getReferencedPort();

  @Nullable
  MSAComponentDeclaration getReferencedComponent();

  @Nullable
  MSAComponentInstanceDeclaration getReferencedComponentInstance();

}
