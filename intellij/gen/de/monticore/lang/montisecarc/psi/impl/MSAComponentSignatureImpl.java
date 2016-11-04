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

public class MSAComponentSignatureImpl extends MSACompositeElementImpl implements MSAComponentSignature {

  public MSAComponentSignatureImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MSAVisitor visitor) {
    visitor.visitComponentSignature(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MSAVisitor) accept((MSAVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public MSAComponentInstanceName getComponentInstanceName() {
    return PsiTreeUtil.getChildOfType(this, MSAComponentInstanceName.class);
  }

  @Override
  @NotNull
  public MSAComponentName getComponentName() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, MSAComponentName.class));
  }

  @Override
  @NotNull
  public List<MSAReferenceType> getReferenceTypeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAReferenceType.class);
  }

  @Override
  @Nullable
  public MSATypeParameters getTypeParameters() {
    return PsiTreeUtil.getChildOfType(this, MSATypeParameters.class);
  }

}
