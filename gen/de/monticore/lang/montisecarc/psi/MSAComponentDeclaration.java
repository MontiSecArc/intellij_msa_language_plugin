// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import de.monticore.lang.montisecarc.stubs.elements.MSAComponentDeclarationStub;
import kotlin.jvm.JvmStatic;

public interface MSAComponentDeclaration extends MSANamedElement, StubBasedPsiElement<MSAComponentDeclarationStub> {

  @Nullable
  MSAComponentBody getComponentBody();

  @Nullable
  MSAComponentSignature getComponentSignature();

  @Nullable
  MSAStereotype getStereotype();

  @Nullable
  MSASuppressAnnotation getSuppressAnnotation();

  @JvmStatic
  @NotNull
  String getName();

  @JvmStatic
  @NotNull
  String getQualifiedName();

  @JvmStatic
  @NotNull
  String getComponentName();

  @JvmStatic
  @NotNull
  String getInstanceName();

  @JvmStatic
  int getTrustLevel();

  @JvmStatic
  int getAbsoluteTrustLevel();

  void addPolicyViolation(String violation);

  @NotNull
  List<String> getPolicyViolations();

  @NotNull
  MSAComponentDeclaration[] getSuperComponents();

}
