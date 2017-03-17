// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static de.monticore.lang.montisecarc.psi.MSACompositeElementTypes.*;
import de.monticore.lang.montisecarc.psi.*;

public class MSAComponentBodyImpl extends MSACompositeElementImpl implements MSAComponentBody {

  public MSAComponentBodyImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MSAVisitor visitor) {
    visitor.visitComponentBody(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MSAVisitor) accept((MSAVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<MSAAccessControlStatement> getAccessControlStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAAccessControlStatement.class);
  }

  @Override
  @NotNull
  public List<MSAAccessStatement> getAccessStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAAccessStatement.class);
  }

  @Override
  @NotNull
  public List<MSACPEStatement> getCPEStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSACPEStatement.class);
  }

  @Override
  @NotNull
  public List<MSAClearanceForStatement> getClearanceForStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAClearanceForStatement.class);
  }

  @Override
  @NotNull
  public List<MSAComponentDeclaration> getComponentDeclarationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAComponentDeclaration.class);
  }

  @Override
  @NotNull
  public List<MSAComponentInstanceDeclaration> getComponentInstanceDeclarationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAComponentInstanceDeclaration.class);
  }

  @Override
  @NotNull
  public List<MSAConfigurationStatement> getConfigurationStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAConfigurationStatement.class);
  }

  @Override
  @NotNull
  public List<MSAConnectPortStatement> getConnectPortStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAConnectPortStatement.class);
  }

  @Override
  @NotNull
  public List<MSAIdentityStatement> getIdentityStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAIdentityStatement.class);
  }

  @Override
  @NotNull
  public List<MSAPortDeclaration> getPortDeclarationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAPortDeclaration.class);
  }

  @Override
  @NotNull
  public List<MSATrustLevelRelationStatement> getTrustLevelRelationStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSATrustLevelRelationStatement.class);
  }

  @Override
  @NotNull
  public List<MSATrustLevelStatement> getTrustLevelStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSATrustLevelStatement.class);
  }

}
