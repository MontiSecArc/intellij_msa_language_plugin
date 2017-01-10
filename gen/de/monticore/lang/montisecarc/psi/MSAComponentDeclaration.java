// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import de.monticore.lang.montisecarc.stubs.elements.MSAComponentDeclarationStub;

public interface MSAComponentDeclaration extends MSAHighlightable, StubBasedPsiElement<MSAComponentDeclarationStub> {

  @Nullable
  MSAComponentBody getComponentBody();

  @Nullable
  MSAComponentSignature getComponentSignature();

  @Nullable
  MSAStereotype getStereotype();

  @Nullable
  MSASuppressAnnotation getSuppressAnnotation();

  @NotNull
  String getQualifiedName();

  @NotNull
  String getComponentName();

  @NotNull
  String getInstanceName();

  int getTrustLevel();

  int getAbsoluteTrustLevel();

  void addPolicyViolation(String violation);

  @NotNull
  List<String> getPolicyViolations();

  @NotNull
  MSAComponentDeclaration[] getSuperComponents();

}
