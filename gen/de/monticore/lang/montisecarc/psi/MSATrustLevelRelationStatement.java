// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface MSATrustLevelRelationStatement extends MSACompositeElement {

  @Nullable
  MSAStereotype getStereotype();

  @Nullable
  MSASuppressAnnotation getSuppressAnnotation();

  @NotNull
  List<MSATrustLevelIdentifier> getTrustLevelIdentifierList();

  @Nullable
  MSATrustLevelIdentifier getStartComponent();

  @Nullable
  MSATrustLevelIdentifier getTargetComponent();

}
