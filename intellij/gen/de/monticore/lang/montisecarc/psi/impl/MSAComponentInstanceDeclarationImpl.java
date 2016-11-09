// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static de.monticore.lang.montisecarc.psi.MSACompositeElementTypes.*;
import de.monticore.lang.montisecarc.psi.impl.mixin.MSAComponentInstanceImplMixin;
import de.monticore.lang.montisecarc.psi.*;
import kotlin.jvm.JvmStatic;
import de.monticore.lang.montisecarc.stubs.elements.MSAComponentInstanceStub;
import com.intellij.psi.stubs.IStubElementType;
import static de.monticore.lang.montisecarc.psi.MSATokenElementTypes.*;

public class MSAComponentInstanceDeclarationImpl extends MSAComponentInstanceImplMixin implements MSAComponentInstanceDeclaration {

  public MSAComponentInstanceDeclarationImpl(ASTNode node) {
    super(node);
  }

  public MSAComponentInstanceDeclarationImpl(MSAComponentInstanceStub stub, IStubElementType<?, ?> nodeType) {
    super(stub, nodeType);
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
  public List<MSAComponentNameWithType> getComponentNameWithTypeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAComponentNameWithType.class);
  }

  @Override
  @NotNull
  public List<MSAConnector> getConnectorList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAConnector.class);
  }

  @Override
  @NotNull
  public List<MSAJavaClassReference> getJavaClassReferenceList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAJavaClassReference.class);
  }

  @Override
  @NotNull
  public List<MSAStereotype> getStereotypeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAStereotype.class);
  }

  @JvmStatic
  @NotNull
  public String getName() {
    return MSAPsiImplUtil.getName(this);
  }

  @JvmStatic
  @NotNull
  public String getQualifiedName() {
    return MSAPsiImplUtil.getQualifiedName(this);
  }

  @JvmStatic
  public int getTrustLevel() {
    return MSAPsiImplUtil.getTrustLevel(this);
  }

  @JvmStatic
  public int getAbsoluteTrustLevel() {
    return MSAPsiImplUtil.getAbsoluteTrustLevel(this);
  }

}
