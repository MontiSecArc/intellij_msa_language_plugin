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

public class MSAIdentityStatementImpl extends MSACompositeElementImpl implements MSAIdentityStatement {

  public MSAIdentityStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MSAVisitor visitor) {
    visitor.visitIdentityStatement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MSAVisitor) accept((MSAVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<MSAIdentityIdentifier> getIdentityIdentifierList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAIdentityIdentifier.class);
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

}
