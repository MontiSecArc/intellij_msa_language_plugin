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

public class MSAConnectorImpl extends MSACompositeElementImpl implements MSAConnector {

  public MSAConnectorImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MSAVisitor visitor) {
    visitor.visitConnector(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MSAVisitor) accept((MSAVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public MSAConnectSource getConnectSource() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, MSAConnectSource.class));
  }

  @Override
  @NotNull
  public List<MSAConnectTarget> getConnectTargetList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAConnectTarget.class);
  }

  @Override
  @Nullable
  public MSAJavaClassReference getJavaClassReference() {
    return PsiTreeUtil.getChildOfType(this, MSAJavaClassReference.class);
  }

}
