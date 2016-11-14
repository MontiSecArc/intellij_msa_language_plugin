// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static de.monticore.lang.montisecarc.psi.MSACompositeElementTypes.*;
import de.monticore.lang.montisecarc.psi.impl.mixin.MSAPortElementImplMixin;
import de.monticore.lang.montisecarc.psi.*;
import kotlin.jvm.JvmStatic;
import de.monticore.lang.montisecarc.stubs.elements.MSAPortElementStub;
import com.intellij.psi.stubs.IStubElementType;
import static de.monticore.lang.montisecarc.psi.MSATokenElementTypes.*;

public class MSAPortElementImpl extends MSAPortElementImplMixin implements MSAPortElement {

  public MSAPortElementImpl(ASTNode node) {
    super(node);
  }

  public MSAPortElementImpl(MSAPortElementStub stub, IStubElementType<?, ?> nodeType) {
    super(stub, nodeType);
  }

  public void accept(@NotNull MSAVisitor visitor) {
    visitor.visitPortElement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MSAVisitor) accept((MSAVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public MSAJavaClassReference getJavaClassReference() {
    return PsiTreeUtil.getChildOfType(this, MSAJavaClassReference.class);
  }

  @Override
  @Nullable
  public MSAPortInstanceName getPortInstanceName() {
    return PsiTreeUtil.getChildOfType(this, MSAPortInstanceName.class);
  }

  @JvmStatic
  public boolean isCritical() {
    return MSAPsiImplUtil.isCritical(this);
  }

  @JvmStatic
  @NotNull
  public String getDirection() {
    return MSAPsiImplUtil.getDirection(this);
  }

}
