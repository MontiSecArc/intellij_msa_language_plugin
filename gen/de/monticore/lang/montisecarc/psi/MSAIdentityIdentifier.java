// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import kotlin.jvm.JvmStatic;

public interface MSAIdentityIdentifier extends MSACompositeElement {

  @NotNull
  List<MSAComponentInstanceName> getComponentInstanceNameList();

  @Nullable
  MSAPortInstanceName getPortInstanceName();

  @JvmStatic
  @Nullable
  MSAPortElement getReferencedPort();

  @JvmStatic
  @Nullable
  MSAComponentDeclaration getReferencedComponent();

  @JvmStatic
  @Nullable
  MSAComponentInstanceDeclaration getReferencedComponentInstance();

}
