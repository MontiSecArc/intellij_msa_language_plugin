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

public class MSAComponentNameWithTypeProjectionImpl extends MSACompositeElementImpl implements MSAComponentNameWithTypeProjection {

  public MSAComponentNameWithTypeProjectionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MSAVisitor visitor) {
    visitor.visitComponentNameWithTypeProjection(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MSAVisitor) accept((MSAVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public MSAComponentName getComponentName() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, MSAComponentName.class));
  }

  @Override
  @Nullable
  public MSATypeProjections getTypeProjections() {
    return PsiTreeUtil.getChildOfType(this, MSATypeProjections.class);
  }

}
