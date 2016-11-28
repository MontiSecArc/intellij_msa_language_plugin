// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import de.monticore.lang.montisecarc.stubs.elements.MSAComponentInstanceStub;
import kotlin.jvm.JvmStatic;

public interface MSAComponentInstanceDeclaration extends MSACompositeElement, StubBasedPsiElement<MSAComponentInstanceStub> {

  @NotNull
  List<MSAComponentInstanceName> getComponentInstanceNameList();

  @NotNull
  List<MSAComponentNameWithTypeProjection> getComponentNameWithTypeProjectionList();

  @NotNull
  List<MSAConnector> getConnectorList();

  @NotNull
  List<MSAJavaClassReference> getJavaClassReferenceList();

  @NotNull
  List<MSAStereotype> getStereotypeList();

  @NotNull
  List<MSASuppressAnnotation> getSuppressAnnotationList();

  @JvmStatic
  @NotNull
  String getQualifiedName();

  @JvmStatic
  int getTrustLevel();

  @JvmStatic
  int getAbsoluteTrustLevel();

  void addPolicyViolation(String violation);

  @NotNull
  List<String> getPolicyViolations();

}
