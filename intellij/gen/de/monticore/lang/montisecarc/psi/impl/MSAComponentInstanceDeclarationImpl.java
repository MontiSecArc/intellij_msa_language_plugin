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

public class MSAComponentInstanceDeclarationImpl extends MSACompositeElementImpl implements MSAComponentInstanceDeclaration {

  public MSAComponentInstanceDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MSAVisitor visitor) {
    visitor.visitComponentInstanceDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MSAVisitor) accept((MSAVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<MSAComponentInstanceName> getComponentInstanceNameList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAComponentInstanceName.class);
  }

  @Override
  @NotNull
  public List<MSAConnector> getConnectorList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAConnector.class);
  }

  @Override
  @NotNull
  public List<MSAPortInstanceName> getPortInstanceNameList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAPortInstanceName.class);
  }

  @Override
  @NotNull
  public MSAReferenceType getReferenceType() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, MSAReferenceType.class));
  }

  @Override
  @NotNull
  public List<MSAStereotype> getStereotypeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAStereotype.class);
  }

  @Override
  @Nullable
  public PsiElement getId() {
    return findChildByType(ID);
  }

}
