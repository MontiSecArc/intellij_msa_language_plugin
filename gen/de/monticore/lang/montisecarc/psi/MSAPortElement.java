// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import de.monticore.lang.montisecarc.stubs.elements.MSAPortElementStub;

public interface MSAPortElement extends MSAHighlightable, StubBasedPsiElement<MSAPortElementStub> {

  @Nullable
  MSAJavaClassReference getJavaClassReference();

  @Nullable
  MSAPortInstanceName getPortInstanceName();

  boolean isCritical();

  @NotNull
  String getDirection();

  @Nullable
  MSAComponentDeclaration getEnclosingComponent();

  @NotNull
  String getPortName();

}
