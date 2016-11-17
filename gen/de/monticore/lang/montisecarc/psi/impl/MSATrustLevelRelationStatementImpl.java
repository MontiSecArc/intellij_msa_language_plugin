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
import static de.monticore.lang.montisecarc.psi.MSATokenElementTypes.*;

public class MSATrustLevelRelationStatementImpl extends MSACompositeElementImpl implements MSATrustLevelRelationStatement {

  public MSATrustLevelRelationStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MSAVisitor visitor) {
    visitor.visitTrustLevelRelationStatement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MSAVisitor) accept((MSAVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public MSAStereotype getStereotype() {
    return PsiTreeUtil.getChildOfType(this, MSAStereotype.class);
  }

  @Override
  @Nullable
  public MSASuppressAnnotation getSuppressAnnotation() {
    return PsiTreeUtil.getChildOfType(this, MSASuppressAnnotation.class);
  }

  @Override
  @NotNull
  public List<MSATrustLevelIdentifier> getTrustLevelIdentifierList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSATrustLevelIdentifier.class);
  }

  @Override
  @Nullable
  public MSATrustLevelIdentifier getStartComponent() {
    List<MSATrustLevelIdentifier> p1 = getTrustLevelIdentifierList();
    return p1.size() < 1 ? null : p1.get(0);
  }

  @Override
  @Nullable
  public MSATrustLevelIdentifier getTargetComponent() {
    List<MSATrustLevelIdentifier> p1 = getTrustLevelIdentifierList();
    return p1.size() < 2 ? null : p1.get(1);
  }

}
