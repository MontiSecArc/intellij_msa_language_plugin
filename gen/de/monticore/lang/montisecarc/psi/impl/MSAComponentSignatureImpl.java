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
  public MSAComponentExtension getComponentExtension() {
    return PsiTreeUtil.getChildOfType(this, MSAComponentExtension.class);
  }

  @Override
  @Nullable
  public MSAComponentInstanceName getComponentInstanceName() {
    return PsiTreeUtil.getChildOfType(this, MSAComponentInstanceName.class);
  }

  @Override
  @NotNull
  public MSAComponentNameWithType getComponentNameWithType() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, MSAComponentNameWithType.class));
  }

  @Override
  @NotNull
  public List<MSAJavaClassReference> getJavaClassReferenceList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAJavaClassReference.class);
  }

  @Override
  @NotNull
  public MSAComponentNameWithType getComponentName() {
    return getComponentNameWithType();
  }

  @Override
  @Nullable
  public MSAComponentNameWithType getComponentExtensionName() {
    MSAComponentExtension p1 = getComponentExtension();
    if (p1 == null) return null;
    return p1.getComponentNameWithType();
  }

}
