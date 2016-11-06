// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import de.monticore.lang.montisecarc.stubs.elements.MSAPortElementStub;
import kotlin.jvm.JvmStatic;

public interface MSAPortElement extends MSANamedElement, StubBasedPsiElement<MSAPortElementStub> {

  @Nullable
  MSAJavaClassReference getJavaClassReference();

  @Nullable
  MSAPortInstanceName getPortInstanceName();

  @Nullable
  PsiElement getId();

  @Nullable
  String getName();

  @JvmStatic
  boolean isCritical();

  @JvmStatic
  @NotNull
  String getDirection();

  @Nullable
  MSAComponentDeclaration getEnclosingComponent();

}