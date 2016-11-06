// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import de.monticore.lang.montisecarc.stubs.ElementFactoryKt;
import de.monticore.lang.montisecarc.psi.impl.*;

public interface MSACompositeElementTypes {

  IElementType ACCESS_CONTROL_STATEMENT = new MSACompositeElementType("ACCESS_CONTROL_STATEMENT");
  IElementType ACCESS_STATEMENT = new MSACompositeElementType("ACCESS_STATEMENT");
  IElementType AUTO_CONNECT_STATEMENT = new MSACompositeElementType("AUTO_CONNECT_STATEMENT");
  IElementType COMPONENT_BODY = new MSACompositeElementType("COMPONENT_BODY");
  IElementType COMPONENT_DECLARATION = ElementFactoryKt.factory("COMPONENT_DECLARATION");
  IElementType COMPONENT_INSTANCE_DECLARATION = ElementFactoryKt.factory("COMPONENT_INSTANCE_DECLARATION");
  IElementType COMPONENT_INSTANCE_NAME = new MSACompositeElementType("COMPONENT_INSTANCE_NAME");
  IElementType COMPONENT_NAME = new MSACompositeElementType("COMPONENT_NAME");
  IElementType COMPONENT_NAME_WITH_TYPE = new MSACompositeElementType("COMPONENT_NAME_WITH_TYPE");
  IElementType COMPONENT_SIGNATURE = new MSACompositeElementType("COMPONENT_SIGNATURE");
  IElementType CONFIGURATION_STATEMENT = new MSACompositeElementType("CONFIGURATION_STATEMENT");
  IElementType CONNECTOR = new MSACompositeElementType("CONNECTOR");
  IElementType CONNECT_PORT_STATEMENT = new MSACompositeElementType("CONNECT_PORT_STATEMENT");
  IElementType CONNECT_SOURCE = new MSACompositeElementType("CONNECT_SOURCE");
  IElementType CONNECT_TARGET = new MSACompositeElementType("CONNECT_TARGET");
  IElementType CPE_STATEMENT = new MSACompositeElementType("CPE_STATEMENT");
  IElementType IDENTITY_IDENTIFIER = new MSACompositeElementType("IDENTITY_IDENTIFIER");
  IElementType IDENTITY_STATEMENT = new MSACompositeElementType("IDENTITY_STATEMENT");
  IElementType IMPORT_DECLARATION = new MSACompositeElementType("IMPORT_DECLARATION");
  IElementType JAVA_CLASS_REFERENCE = new MSACompositeElementType("JAVA_CLASS_REFERENCE");
  IElementType LEVEL = new MSACompositeElementType("LEVEL");
  IElementType PACKAGE_CLAUSE = new MSACompositeElementType("PACKAGE_CLAUSE");
  IElementType PORT_ACCESS_ROLE = new MSACompositeElementType("PORT_ACCESS_ROLE");
  IElementType PORT_DECLARATION = new MSACompositeElementType("PORT_DECLARATION");
  IElementType PORT_ELEMENT = ElementFactoryKt.factory("PORT_ELEMENT");
  IElementType PORT_INSTANCE_NAME = new MSACompositeElementType("PORT_INSTANCE_NAME");
  IElementType QUALIFIED_IDENTIFIER = new MSACompositeElementType("QUALIFIED_IDENTIFIER");
  IElementType ROLE_NAME = new MSACompositeElementType("ROLE_NAME");
  IElementType STEREOTYPE = new MSACompositeElementType("STEREOTYPE");
  IElementType TRUST_LEVEL_IDENTIFIER = new MSACompositeElementType("TRUST_LEVEL_IDENTIFIER");
  IElementType TRUST_LEVEL_RELATION_STATEMENT = new MSACompositeElementType("TRUST_LEVEL_RELATION_STATEMENT");
  IElementType TRUST_LEVEL_STATEMENT = new MSACompositeElementType("TRUST_LEVEL_STATEMENT");
  IElementType TYPE_PARAMETERS = new MSACompositeElementType("TYPE_PARAMETERS");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == ACCESS_CONTROL_STATEMENT) {
        return new MSAAccessControlStatementImpl(node);
      }
      else if (type == ACCESS_STATEMENT) {
        return new MSAAccessStatementImpl(node);
      }
      else if (type == AUTO_CONNECT_STATEMENT) {
        return new MSAAutoConnectStatementImpl(node);
      }
      else if (type == COMPONENT_BODY) {
        return new MSAComponentBodyImpl(node);
      }
      else if (type == COMPONENT_DECLARATION) {
        return new MSAComponentDeclarationImpl(node);
      }
      else if (type == COMPONENT_INSTANCE_DECLARATION) {
        return new MSAComponentInstanceDeclarationImpl(node);
      }
      else if (type == COMPONENT_INSTANCE_NAME) {
        return new MSAComponentInstanceNameImpl(node);
      }
      else if (type == COMPONENT_NAME) {
        return new MSAComponentNameImpl(node);
      }
      else if (type == COMPONENT_NAME_WITH_TYPE) {
        return new MSAComponentNameWithTypeImpl(node);
      }
      else if (type == COMPONENT_SIGNATURE) {
        return new MSAComponentSignatureImpl(node);
      }
      else if (type == CONFIGURATION_STATEMENT) {
        return new MSAConfigurationStatementImpl(node);
      }
      else if (type == CONNECTOR) {
        return new MSAConnectorImpl(node);
      }
      else if (type == CONNECT_PORT_STATEMENT) {
        return new MSAConnectPortStatementImpl(node);
      }
      else if (type == CONNECT_SOURCE) {
        return new MSAConnectSourceImpl(node);
      }
      else if (type == CONNECT_TARGET) {
        return new MSAConnectTargetImpl(node);
      }
      else if (type == CPE_STATEMENT) {
        return new MSACPEStatementImpl(node);
      }
      else if (type == IDENTITY_IDENTIFIER) {
        return new MSAIdentityIdentifierImpl(node);
      }
      else if (type == IDENTITY_STATEMENT) {
        return new MSAIdentityStatementImpl(node);
      }
      else if (type == IMPORT_DECLARATION) {
        return new MSAImportDeclarationImpl(node);
      }
      else if (type == JAVA_CLASS_REFERENCE) {
        return new MSAJavaClassReferenceImpl(node);
      }
      else if (type == LEVEL) {
        return new MSALevelImpl(node);
      }
      else if (type == PACKAGE_CLAUSE) {
        return new MSAPackageClauseImpl(node);
      }
      else if (type == PORT_ACCESS_ROLE) {
        return new MSAPortAccessRoleImpl(node);
      }
      else if (type == PORT_DECLARATION) {
        return new MSAPortDeclarationImpl(node);
      }
      else if (type == PORT_ELEMENT) {
        return new MSAPortElementImpl(node);
      }
      else if (type == PORT_INSTANCE_NAME) {
        return new MSAPortInstanceNameImpl(node);
      }
      else if (type == QUALIFIED_IDENTIFIER) {
        return new MSAQualifiedIdentifierImpl(node);
      }
      else if (type == ROLE_NAME) {
        return new MSARoleNameImpl(node);
      }
      else if (type == STEREOTYPE) {
        return new MSAStereotypeImpl(node);
      }
      else if (type == TRUST_LEVEL_IDENTIFIER) {
        return new MSATrustLevelIdentifierImpl(node);
      }
      else if (type == TRUST_LEVEL_RELATION_STATEMENT) {
        return new MSATrustLevelRelationStatementImpl(node);
      }
      else if (type == TRUST_LEVEL_STATEMENT) {
        return new MSATrustLevelStatementImpl(node);
      }
      else if (type == TYPE_PARAMETERS) {
        return new MSATypeParametersImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
