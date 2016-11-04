// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import de.monticore.lang.montisecarc.stubs.elements.MSAComponentInstanceStub;
import kotlin.jvm.JvmStatic;

public interface MSAComponentInstanceDeclaration extends MSANamedElement, StubBasedPsiElement<MSAComponentInstanceStub> {

  @NotNull
  List<MSAComponentInstanceName> getComponentInstanceNameList();

  @NotNull
  List<MSAConnector> getConnectorList();

  @NotNull
  List<MSAPortInstanceName> getPortInstanceNameList();

  @NotNull
  MSAReferenceType getReferenceType();

  @NotNull
  List<MSAStereotype> getStereotypeList();

  @Nullable
  PsiElement getId();

  @JvmStatic
  @NotNull
  String getName();

}
