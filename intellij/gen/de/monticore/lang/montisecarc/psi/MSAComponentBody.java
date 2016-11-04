// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface MSAComponentBody extends MSACompositeElement {

  @NotNull
  List<MSAAccessControlStatement> getAccessControlStatementList();

  @NotNull
  List<MSAAccessStatement> getAccessStatementList();

  @NotNull
  List<MSAAutoConnectStatement> getAutoConnectStatementList();

  @NotNull
  List<MSACPEStatement> getCPEStatementList();

  @NotNull
  List<MSAComponentDeclaration> getComponentDeclarationList();

  @NotNull
  List<MSAComponentInstanceDeclaration> getComponentInstanceDeclarationList();

  @NotNull
  List<MSAConfigurationStatement> getConfigurationStatementList();

  @NotNull
  List<MSAConnectPortStatement> getConnectPortStatementList();

  @NotNull
  List<MSAIdentityStatement> getIdentityStatementList();

  @NotNull
  List<MSAPortDeclaration> getPortDeclarationList();

  @NotNull
  List<MSATrustLevelRelationStatement> getTrustLevelRelationStatementList();

  @NotNull
  List<MSATrustLevelStatement> getTrustLevelStatementList();

}
