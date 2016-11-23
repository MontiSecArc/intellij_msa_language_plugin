// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static de.monticore.lang.montisecarc.psi.MSACompositeElementTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;
import static de.monticore.lang.montisecarc.psi.MSATokenElementTypes.*;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class MSAParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t == ACCESS_CONTROL_STATEMENT) {
      r = AccessControlStatement(b, 0);
    }
    else if (t == ACCESS_STATEMENT) {
      r = AccessStatement(b, 0);
    }
    else if (t == AUTO_CONNECT_STATEMENT) {
      r = AutoConnectStatement(b, 0);
    }
    else if (t == CPE_STATEMENT) {
      r = CPEStatement(b, 0);
    }
    else if (t == COMPONENT_BODY) {
      r = ComponentBody(b, 0);
    }
    else if (t == COMPONENT_DECLARATION) {
      r = ComponentDeclaration(b, 0);
    }
    else if (t == COMPONENT_EXTENSION) {
      r = ComponentExtension(b, 0);
    }
    else if (t == COMPONENT_INSTANCE_DECLARATION) {
      r = ComponentInstanceDeclaration(b, 0);
    }
    else if (t == COMPONENT_INSTANCE_NAME) {
      r = ComponentInstanceName(b, 0);
    }
    else if (t == COMPONENT_NAME) {
      r = ComponentName(b, 0);
    }
    else if (t == COMPONENT_NAME_WITH_TYPE) {
      r = ComponentNameWithType(b, 0);
    }
    else if (t == COMPONENT_SIGNATURE) {
      r = ComponentSignature(b, 0);
    }
    else if (t == CONFIGURATION_STATEMENT) {
      r = ConfigurationStatement(b, 0);
    }
    else if (t == CONNECT_PORT_STATEMENT) {
      r = ConnectPortStatement(b, 0);
    }
    else if (t == CONNECT_SOURCE) {
      r = ConnectSource(b, 0);
    }
    else if (t == CONNECT_TARGET) {
      r = ConnectTarget(b, 0);
    }
    else if (t == CONNECTOR) {
      r = Connector(b, 0);
    }
    else if (t == IDENTITY_IDENTIFIER) {
      r = IdentityIdentifier(b, 0);
    }
    else if (t == IDENTITY_STATEMENT) {
      r = IdentityStatement(b, 0);
    }
    else if (t == IMPORT_DECLARATION) {
      r = ImportDeclaration(b, 0);
    }
    else if (t == JAVA_CLASS_REFERENCE) {
      r = JavaClassReference(b, 0);
    }
    else if (t == JAVA_REFERENCE) {
      r = JavaReference(b, 0);
    }
    else if (t == LEVEL) {
      r = LEVEL(b, 0);
    }
    else if (t == PACKAGE_CLAUSE) {
      r = PackageClause(b, 0);
    }
    else if (t == POLICY) {
      r = Policy(b, 0);
    }
    else if (t == PORT_ACCESS_ROLE) {
      r = PortAccessRole(b, 0);
    }
    else if (t == PORT_DECLARATION) {
      r = PortDeclaration(b, 0);
    }
    else if (t == PORT_ELEMENT) {
      r = PortElement(b, 0);
    }
    else if (t == PORT_INSTANCE_NAME) {
      r = PortInstanceName(b, 0);
    }
    else if (t == QUALIFIED_IDENTIFIER) {
      r = QualifiedIdentifier(b, 0);
    }
    else if (t == ROLE_NAME) {
      r = RoleName(b, 0);
    }
    else if (t == STEREOTYPE) {
      r = Stereotype(b, 0);
    }
    else if (t == SUPPRESS_ANNOTATION) {
      r = SuppressAnnotation(b, 0);
    }
    else if (t == SUPPRESS_ANNOTATION_KEYWORD) {
      r = SuppressAnnotationKeyword(b, 0);
    }
    else if (t == TRUST_LEVEL_IDENTIFIER) {
      r = TrustLevelIdentifier(b, 0);
    }
    else if (t == TRUST_LEVEL_RELATION_STATEMENT) {
      r = TrustLevelRelationStatement(b, 0);
    }
    else if (t == TRUST_LEVEL_STATEMENT) {
      r = TrustLevelStatement(b, 0);
    }
    else if (t == TYPE_PARAMETERS) {
      r = TypeParameters(b, 0);
    }
    else {
      r = parse_root_(t, b, 0);
    }
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return File(b, l + 1);
  }

  /* ********************************************************** */
  // Prefix? ACCESSCONTROL { ON | OFF } semi
  public static boolean AccessControlStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AccessControlStatement")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ACCESS_CONTROL_STATEMENT, "<access control statement>");
    r = AccessControlStatement_0(b, l + 1);
    r = r && consumeToken(b, ACCESSCONTROL);
    p = r; // pin = 2
    r = r && report_error_(b, AccessControlStatement_2(b, l + 1));
    r = p && semi(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // Prefix?
  private static boolean AccessControlStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AccessControlStatement_0")) return false;
    Prefix(b, l + 1);
    return true;
  }

  // ON | OFF
  private static boolean AccessControlStatement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AccessControlStatement_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ON);
    if (!r) r = consumeToken(b, OFF);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PortAccessRole | RoleName
  static boolean AccessRoles(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AccessRoles")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = PortAccessRole(b, l + 1);
    if (!r) r = RoleName(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Prefix? ACCESS AccessRoles ( COMMA AccessRoles )* semi
  public static boolean AccessStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AccessStatement")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ACCESS_STATEMENT, "<access statement>");
    r = AccessStatement_0(b, l + 1);
    r = r && consumeToken(b, ACCESS);
    p = r; // pin = 2
    r = r && report_error_(b, AccessRoles(b, l + 1));
    r = p && report_error_(b, AccessStatement_3(b, l + 1)) && r;
    r = p && semi(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // Prefix?
  private static boolean AccessStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AccessStatement_0")) return false;
    Prefix(b, l + 1);
    return true;
  }

  // ( COMMA AccessRoles )*
  private static boolean AccessStatement_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AccessStatement_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!AccessStatement_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "AccessStatement_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA AccessRoles
  private static boolean AccessStatement_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AccessStatement_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && AccessRoles(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Prefix? AUTOCONNECT [ENCRYPTED | UNENCRYPTED] { TYPE | port | OFF } semi
  public static boolean AutoConnectStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AutoConnectStatement")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, AUTO_CONNECT_STATEMENT, "<auto connect statement>");
    r = AutoConnectStatement_0(b, l + 1);
    r = r && consumeToken(b, AUTOCONNECT);
    p = r; // pin = 2
    r = r && report_error_(b, AutoConnectStatement_2(b, l + 1));
    r = p && report_error_(b, AutoConnectStatement_3(b, l + 1)) && r;
    r = p && semi(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // Prefix?
  private static boolean AutoConnectStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AutoConnectStatement_0")) return false;
    Prefix(b, l + 1);
    return true;
  }

  // [ENCRYPTED | UNENCRYPTED]
  private static boolean AutoConnectStatement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AutoConnectStatement_2")) return false;
    AutoConnectStatement_2_0(b, l + 1);
    return true;
  }

  // ENCRYPTED | UNENCRYPTED
  private static boolean AutoConnectStatement_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AutoConnectStatement_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ENCRYPTED);
    if (!r) r = consumeToken(b, UNENCRYPTED);
    exit_section_(b, m, null, r);
    return r;
  }

  // TYPE | port | OFF
  private static boolean AutoConnectStatement_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AutoConnectStatement_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TYPE);
    if (!r) r = consumeToken(b, PORT);
    if (!r) r = consumeToken(b, OFF);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Prefix? CPE STRING semi
  public static boolean CPEStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CPEStatement")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CPE_STATEMENT, "<cpe statement>");
    r = CPEStatement_0(b, l + 1);
    r = r && consumeTokens(b, 1, CPE, STRING);
    p = r; // pin = 2
    r = r && semi(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // Prefix?
  private static boolean CPEStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CPEStatement_0")) return false;
    Prefix(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // LBRACE (Statement)* RBRACE
  public static boolean ComponentBody(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentBody")) return false;
    if (!nextTokenIs(b, LBRACE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, COMPONENT_BODY, null);
    r = consumeToken(b, LBRACE);
    p = r; // pin = 1
    r = r && report_error_(b, ComponentBody_1(b, l + 1));
    r = p && consumeToken(b, RBRACE) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (Statement)*
  private static boolean ComponentBody_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentBody_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!ComponentBody_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ComponentBody_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // (Statement)
  private static boolean ComponentBody_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentBody_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Statement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Prefix? component ComponentSignature ComponentBody
  public static boolean ComponentDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentDeclaration")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, COMPONENT_DECLARATION, "<component declaration>");
    r = ComponentDeclaration_0(b, l + 1);
    r = r && consumeToken(b, COMPONENT);
    p = r; // pin = 2
    r = r && report_error_(b, ComponentSignature(b, l + 1));
    r = p && ComponentBody(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // Prefix?
  private static boolean ComponentDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentDeclaration_0")) return false;
    Prefix(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // extends ComponentNameWithType
  public static boolean ComponentExtension(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentExtension")) return false;
    if (!nextTokenIs(b, EXTENDS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EXTENDS);
    r = r && ComponentNameWithType(b, l + 1);
    exit_section_(b, m, COMPONENT_EXTENSION, r);
    return r;
  }

  /* ********************************************************** */
  // ComponentInstanceWithParameters (LBRACK SimpleConnectPortStatement (semi SimpleConnectPortStatement)* RBRACK)?
  static boolean ComponentInstance(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstance")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ComponentInstanceWithParameters(b, l + 1);
    r = r && ComponentInstance_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (LBRACK SimpleConnectPortStatement (semi SimpleConnectPortStatement)* RBRACK)?
  private static boolean ComponentInstance_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstance_1")) return false;
    ComponentInstance_1_0(b, l + 1);
    return true;
  }

  // LBRACK SimpleConnectPortStatement (semi SimpleConnectPortStatement)* RBRACK
  private static boolean ComponentInstance_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstance_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACK);
    r = r && SimpleConnectPortStatement(b, l + 1);
    r = r && ComponentInstance_1_0_2(b, l + 1);
    r = r && consumeToken(b, RBRACK);
    exit_section_(b, m, null, r);
    return r;
  }

  // (semi SimpleConnectPortStatement)*
  private static boolean ComponentInstance_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstance_1_0_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!ComponentInstance_1_0_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ComponentInstance_1_0_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // semi SimpleConnectPortStatement
  private static boolean ComponentInstance_1_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstance_1_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = semi(b, l + 1);
    r = r && SimpleConnectPortStatement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Prefix?  ComponentNamesWithTypes ComponentParameters SuppressAnnotation? ComponentInstance (COMMA SuppressAnnotation? ComponentInstance)* semi
  public static boolean ComponentInstanceDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstanceDeclaration")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, COMPONENT_INSTANCE_DECLARATION, "<component instance declaration>");
    r = ComponentInstanceDeclaration_0(b, l + 1);
    r = r && ComponentNamesWithTypes(b, l + 1);
    r = r && ComponentParameters(b, l + 1);
    p = r; // pin = 3
    r = r && report_error_(b, ComponentInstanceDeclaration_3(b, l + 1));
    r = p && report_error_(b, ComponentInstance(b, l + 1)) && r;
    r = p && report_error_(b, ComponentInstanceDeclaration_5(b, l + 1)) && r;
    r = p && semi(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // Prefix?
  private static boolean ComponentInstanceDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstanceDeclaration_0")) return false;
    Prefix(b, l + 1);
    return true;
  }

  // SuppressAnnotation?
  private static boolean ComponentInstanceDeclaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstanceDeclaration_3")) return false;
    SuppressAnnotation(b, l + 1);
    return true;
  }

  // (COMMA SuppressAnnotation? ComponentInstance)*
  private static boolean ComponentInstanceDeclaration_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstanceDeclaration_5")) return false;
    int c = current_position_(b);
    while (true) {
      if (!ComponentInstanceDeclaration_5_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ComponentInstanceDeclaration_5", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA SuppressAnnotation? ComponentInstance
  private static boolean ComponentInstanceDeclaration_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstanceDeclaration_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && ComponentInstanceDeclaration_5_0_1(b, l + 1);
    r = r && ComponentInstance(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // SuppressAnnotation?
  private static boolean ComponentInstanceDeclaration_5_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstanceDeclaration_5_0_1")) return false;
    SuppressAnnotation(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ID
  public static boolean ComponentInstanceName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstanceName")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    exit_section_(b, m, COMPONENT_INSTANCE_NAME, r);
    return r;
  }

  /* ********************************************************** */
  // ComponentInstanceName ("(" ID ("," ID)* ")")?
  static boolean ComponentInstanceWithParameters(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstanceWithParameters")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ComponentInstanceName(b, l + 1);
    r = r && ComponentInstanceWithParameters_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ("(" ID ("," ID)* ")")?
  private static boolean ComponentInstanceWithParameters_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstanceWithParameters_1")) return false;
    ComponentInstanceWithParameters_1_0(b, l + 1);
    return true;
  }

  // "(" ID ("," ID)* ")"
  private static boolean ComponentInstanceWithParameters_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstanceWithParameters_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && consumeToken(b, ID);
    r = r && ComponentInstanceWithParameters_1_0_2(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // ("," ID)*
  private static boolean ComponentInstanceWithParameters_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstanceWithParameters_1_0_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!ComponentInstanceWithParameters_1_0_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ComponentInstanceWithParameters_1_0_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // "," ID
  private static boolean ComponentInstanceWithParameters_1_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstanceWithParameters_1_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && consumeToken(b, ID);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ID
  public static boolean ComponentName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentName")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    exit_section_(b, m, COMPONENT_NAME, r);
    return r;
  }

  /* ********************************************************** */
  // ComponentName TypeParameters?
  public static boolean ComponentNameWithType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentNameWithType")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, COMPONENT_NAME_WITH_TYPE, null);
    r = ComponentName(b, l + 1);
    p = r; // pin = 1
    r = r && ComponentNameWithType_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // TypeParameters?
  private static boolean ComponentNameWithType_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentNameWithType_1")) return false;
    TypeParameters(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ComponentNameWithType (DOT ComponentNameWithType)*
  static boolean ComponentNamesWithTypes(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentNamesWithTypes")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = ComponentNameWithType(b, l + 1);
    p = r; // pin = 1
    r = r && ComponentNamesWithTypes_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (DOT ComponentNameWithType)*
  private static boolean ComponentNamesWithTypes_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentNamesWithTypes_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!ComponentNamesWithTypes_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ComponentNamesWithTypes_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // DOT ComponentNameWithType
  private static boolean ComponentNamesWithTypes_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentNamesWithTypes_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOT);
    r = r && ComponentNameWithType(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ("(" Parameter ("," Parameter)* ")")?
  static boolean ComponentParameters(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentParameters")) return false;
    ComponentParameters_0(b, l + 1);
    return true;
  }

  // "(" Parameter ("," Parameter)* ")"
  private static boolean ComponentParameters_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentParameters_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && Parameter(b, l + 1);
    r = r && ComponentParameters_0_2(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // ("," Parameter)*
  private static boolean ComponentParameters_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentParameters_0_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!ComponentParameters_0_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ComponentParameters_0_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // "," Parameter
  private static boolean ComponentParameters_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentParameters_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && Parameter(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ComponentNameWithType ComponentParameters ComponentExtension? ComponentInstanceWithParameters?
  public static boolean ComponentSignature(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentSignature")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, COMPONENT_SIGNATURE, "<component signature>");
    r = ComponentNameWithType(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, ComponentParameters(b, l + 1));
    r = p && report_error_(b, ComponentSignature_2(b, l + 1)) && r;
    r = p && ComponentSignature_3(b, l + 1) && r;
    exit_section_(b, l, m, r, p, signature_recover_parser_);
    return r || p;
  }

  // ComponentExtension?
  private static boolean ComponentSignature_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentSignature_2")) return false;
    ComponentExtension(b, l + 1);
    return true;
  }

  // ComponentInstanceWithParameters?
  private static boolean ComponentSignature_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentSignature_3")) return false;
    ComponentInstanceWithParameters(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // Prefix? CONFIGURATION ID semi
  public static boolean ConfigurationStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConfigurationStatement")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CONFIGURATION_STATEMENT, "<configuration statement>");
    r = ConfigurationStatement_0(b, l + 1);
    r = r && consumeTokens(b, 1, CONFIGURATION, ID);
    p = r; // pin = 2
    r = r && semi(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // Prefix?
  private static boolean ConfigurationStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConfigurationStatement_0")) return false;
    Prefix(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // Prefix? connect Connector semi
  public static boolean ConnectPortStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConnectPortStatement")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CONNECT_PORT_STATEMENT, "<connect port statement>");
    r = ConnectPortStatement_0(b, l + 1);
    r = r && consumeToken(b, CONNECT);
    p = r; // pin = 2
    r = r && report_error_(b, Connector(b, l + 1));
    r = p && semi(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // Prefix?
  private static boolean ConnectPortStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConnectPortStatement_0")) return false;
    Prefix(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // QualifiedIdentifier
  public static boolean ConnectSource(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConnectSource")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = QualifiedIdentifier(b, l + 1);
    exit_section_(b, m, CONNECT_SOURCE, r);
    return r;
  }

  /* ********************************************************** */
  // QualifiedIdentifier
  public static boolean ConnectTarget(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConnectTarget")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = QualifiedIdentifier(b, l + 1);
    exit_section_(b, m, CONNECT_TARGET, r);
    return r;
  }

  /* ********************************************************** */
  // [ ENCRYPTED | UNENCRYPTED ] [ WEAK_AUTH | STRONG_AUTH ] ConnectSource ARROW ConnectTarget (COMMA ConnectTarget)*
  public static boolean Connector(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Connector")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CONNECTOR, "<connector>");
    r = Connector_0(b, l + 1);
    r = r && Connector_1(b, l + 1);
    r = r && ConnectSource(b, l + 1);
    p = r; // pin = 3
    r = r && report_error_(b, consumeToken(b, ARROW));
    r = p && report_error_(b, ConnectTarget(b, l + 1)) && r;
    r = p && Connector_5(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [ ENCRYPTED | UNENCRYPTED ]
  private static boolean Connector_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Connector_0")) return false;
    Connector_0_0(b, l + 1);
    return true;
  }

  // ENCRYPTED | UNENCRYPTED
  private static boolean Connector_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Connector_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ENCRYPTED);
    if (!r) r = consumeToken(b, UNENCRYPTED);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ WEAK_AUTH | STRONG_AUTH ]
  private static boolean Connector_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Connector_1")) return false;
    Connector_1_0(b, l + 1);
    return true;
  }

  // WEAK_AUTH | STRONG_AUTH
  private static boolean Connector_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Connector_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, WEAK_AUTH);
    if (!r) r = consumeToken(b, STRONG_AUTH);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA ConnectTarget)*
  private static boolean Connector_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Connector_5")) return false;
    int c = current_position_(b);
    while (true) {
      if (!Connector_5_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Connector_5", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA ConnectTarget
  private static boolean Connector_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Connector_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && ConnectTarget(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // [PackageClause] (ImportDeclaration)* ComponentDeclaration?
  static boolean File(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "File")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = File_0(b, l + 1);
    r = r && File_1(b, l + 1);
    r = r && File_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [PackageClause]
  private static boolean File_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "File_0")) return false;
    PackageClause(b, l + 1);
    return true;
  }

  // (ImportDeclaration)*
  private static boolean File_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "File_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!File_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "File_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // (ImportDeclaration)
  private static boolean File_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "File_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ImportDeclaration(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ComponentDeclaration?
  private static boolean File_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "File_2")) return false;
    ComponentDeclaration(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // (ComponentInstanceName DOT)* ComponentInstanceName  PortInstanceName?
  public static boolean IdentityIdentifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IdentityIdentifier")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = IdentityIdentifier_0(b, l + 1);
    r = r && ComponentInstanceName(b, l + 1);
    r = r && IdentityIdentifier_2(b, l + 1);
    exit_section_(b, m, IDENTITY_IDENTIFIER, r);
    return r;
  }

  // (ComponentInstanceName DOT)*
  private static boolean IdentityIdentifier_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IdentityIdentifier_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!IdentityIdentifier_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "IdentityIdentifier_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ComponentInstanceName DOT
  private static boolean IdentityIdentifier_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IdentityIdentifier_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ComponentInstanceName(b, l + 1);
    r = r && consumeToken(b, DOT);
    exit_section_(b, m, null, r);
    return r;
  }

  // PortInstanceName?
  private static boolean IdentityIdentifier_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IdentityIdentifier_2")) return false;
    PortInstanceName(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // Prefix IDENTITY { STRONG | WEAK } IdentityIdentifier ARROW IdentityIdentifier ( COMMA IdentityIdentifier)* semi
  public static boolean IdentityStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IdentityStatement")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IDENTITY_STATEMENT, "<identity statement>");
    r = Prefix(b, l + 1);
    r = r && consumeToken(b, IDENTITY);
    p = r; // pin = 2
    r = r && report_error_(b, IdentityStatement_2(b, l + 1));
    r = p && report_error_(b, IdentityIdentifier(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, ARROW)) && r;
    r = p && report_error_(b, IdentityIdentifier(b, l + 1)) && r;
    r = p && report_error_(b, IdentityStatement_6(b, l + 1)) && r;
    r = p && semi(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // STRONG | WEAK
  private static boolean IdentityStatement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IdentityStatement_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STRONG);
    if (!r) r = consumeToken(b, WEAK);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( COMMA IdentityIdentifier)*
  private static boolean IdentityStatement_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IdentityStatement_6")) return false;
    int c = current_position_(b);
    while (true) {
      if (!IdentityStatement_6_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "IdentityStatement_6", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA IdentityIdentifier
  private static boolean IdentityStatement_6_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IdentityStatement_6_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && IdentityIdentifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // import ID (DOT (ID DOT)* ASTERIX | (DOT ID)*) semi
  public static boolean ImportDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImportDeclaration")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IMPORT_DECLARATION, "<import declaration>");
    r = consumeTokens(b, 1, IMPORT, ID);
    p = r; // pin = 1
    r = r && report_error_(b, ImportDeclaration_2(b, l + 1));
    r = p && semi(b, l + 1) && r;
    exit_section_(b, l, m, r, p, import_recover_parser_);
    return r || p;
  }

  // DOT (ID DOT)* ASTERIX | (DOT ID)*
  private static boolean ImportDeclaration_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImportDeclaration_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ImportDeclaration_2_0(b, l + 1);
    if (!r) r = ImportDeclaration_2_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // DOT (ID DOT)* ASTERIX
  private static boolean ImportDeclaration_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImportDeclaration_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOT);
    r = r && ImportDeclaration_2_0_1(b, l + 1);
    r = r && consumeToken(b, ASTERIX);
    exit_section_(b, m, null, r);
    return r;
  }

  // (ID DOT)*
  private static boolean ImportDeclaration_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImportDeclaration_2_0_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!ImportDeclaration_2_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ImportDeclaration_2_0_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ID DOT
  private static boolean ImportDeclaration_2_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImportDeclaration_2_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ID, DOT);
    exit_section_(b, m, null, r);
    return r;
  }

  // (DOT ID)*
  private static boolean ImportDeclaration_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImportDeclaration_2_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!ImportDeclaration_2_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ImportDeclaration_2_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // DOT ID
  private static boolean ImportDeclaration_2_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImportDeclaration_2_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, DOT, ID);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (JavaReference DOT)* JavaReference
  public static boolean JavaClassReference(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JavaClassReference")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = JavaClassReference_0(b, l + 1);
    r = r && JavaReference(b, l + 1);
    exit_section_(b, m, JAVA_CLASS_REFERENCE, r);
    return r;
  }

  // (JavaReference DOT)*
  private static boolean JavaClassReference_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JavaClassReference_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!JavaClassReference_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "JavaClassReference_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // JavaReference DOT
  private static boolean JavaClassReference_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JavaClassReference_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = JavaReference(b, l + 1);
    r = r && consumeToken(b, DOT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ID
  public static boolean JavaReference(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "JavaReference")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    exit_section_(b, m, JAVA_REFERENCE, r);
    return r;
  }

  /* ********************************************************** */
  // [ PLUS | MINUS ] NUMBER
  public static boolean LEVEL(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LEVEL")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LEVEL, "<level>");
    r = LEVEL_0(b, l + 1);
    r = r && consumeToken(b, NUMBER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [ PLUS | MINUS ]
  private static boolean LEVEL_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LEVEL_0")) return false;
    LEVEL_0_0(b, l + 1);
    return true;
  }

  // PLUS | MINUS
  private static boolean LEVEL_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LEVEL_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PLUS);
    if (!r) r = consumeToken(b, MINUS);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // package (DOT | ID)+ semi
  public static boolean PackageClause(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PackageClause")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PACKAGE_CLAUSE, "<package clause>");
    r = consumeToken(b, PACKAGE);
    p = r; // pin = 1
    r = r && report_error_(b, PackageClause_1(b, l + 1));
    r = p && semi(b, l + 1) && r;
    exit_section_(b, l, m, r, p, package_recover_parser_);
    return r || p;
  }

  // (DOT | ID)+
  private static boolean PackageClause_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PackageClause_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = PackageClause_1_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!PackageClause_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "PackageClause_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // DOT | ID
  private static boolean PackageClause_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PackageClause_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOT);
    if (!r) r = consumeToken(b, ID);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // JavaClassReference ID ("=" ID)?
  static boolean Parameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Parameter")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = JavaClassReference(b, l + 1);
    r = r && consumeToken(b, ID);
    r = r && Parameter_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ("=" ID)?
  private static boolean Parameter_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Parameter_2")) return false;
    Parameter_2_0(b, l + 1);
    return true;
  }

  // "=" ID
  private static boolean Parameter_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Parameter_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EQUAL);
    r = r && consumeToken(b, ID);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // STRING
  public static boolean Policy(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Policy")) return false;
    if (!nextTokenIs(b, STRING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STRING);
    exit_section_(b, m, POLICY, r);
    return r;
  }

  /* ********************************************************** */
  // QualifiedIdentifier LPAREN RoleName ( COMMA RoleName )* RPAREN
  public static boolean PortAccessRole(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortAccessRole")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = QualifiedIdentifier(b, l + 1);
    r = r && consumeToken(b, LPAREN);
    r = r && RoleName(b, l + 1);
    r = r && PortAccessRole_3(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, PORT_ACCESS_ROLE, r);
    return r;
  }

  // ( COMMA RoleName )*
  private static boolean PortAccessRole_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortAccessRole_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!PortAccessRole_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "PortAccessRole_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA RoleName
  private static boolean PortAccessRole_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortAccessRole_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && RoleName(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Prefix port SuppressAnnotation? PortElement ( COMMA SuppressAnnotation? PortElement )* semi
  public static boolean PortDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortDeclaration")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PORT_DECLARATION, "<port declaration>");
    r = Prefix(b, l + 1);
    r = r && consumeToken(b, PORT);
    r = r && PortDeclaration_2(b, l + 1);
    p = r; // pin = 3
    r = r && report_error_(b, PortElement(b, l + 1));
    r = p && report_error_(b, PortDeclaration_4(b, l + 1)) && r;
    r = p && semi(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // SuppressAnnotation?
  private static boolean PortDeclaration_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortDeclaration_2")) return false;
    SuppressAnnotation(b, l + 1);
    return true;
  }

  // ( COMMA SuppressAnnotation? PortElement )*
  private static boolean PortDeclaration_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortDeclaration_4")) return false;
    int c = current_position_(b);
    while (true) {
      if (!PortDeclaration_4_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "PortDeclaration_4", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA SuppressAnnotation? PortElement
  private static boolean PortDeclaration_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortDeclaration_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && PortDeclaration_4_0_1(b, l + 1);
    r = r && PortElement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // SuppressAnnotation?
  private static boolean PortDeclaration_4_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortDeclaration_4_0_1")) return false;
    SuppressAnnotation(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // 'critical'? { 'in' | 'out' } JavaClassReference [PortInstanceName]
  public static boolean PortElement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortElement")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PORT_ELEMENT, "<port>");
    r = PortElement_0(b, l + 1);
    r = r && PortElement_1(b, l + 1);
    p = r; // pin = 2
    r = r && report_error_(b, JavaClassReference(b, l + 1));
    r = p && PortElement_3(b, l + 1) && r;
    exit_section_(b, l, m, r, p, port_element_recover_parser_);
    return r || p;
  }

  // 'critical'?
  private static boolean PortElement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortElement_0")) return false;
    consumeToken(b, CRITICAL);
    return true;
  }

  // 'in' | 'out'
  private static boolean PortElement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortElement_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IN);
    if (!r) r = consumeToken(b, OUT);
    exit_section_(b, m, null, r);
    return r;
  }

  // [PortInstanceName]
  private static boolean PortElement_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortElement_3")) return false;
    PortInstanceName(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ID
  public static boolean PortInstanceName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortInstanceName")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    exit_section_(b, m, PORT_INSTANCE_NAME, r);
    return r;
  }

  /* ********************************************************** */
  // SuppressAnnotation? Stereotype?
  static boolean Prefix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Prefix")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Prefix_0(b, l + 1);
    r = r && Prefix_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // SuppressAnnotation?
  private static boolean Prefix_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Prefix_0")) return false;
    SuppressAnnotation(b, l + 1);
    return true;
  }

  // Stereotype?
  private static boolean Prefix_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Prefix_1")) return false;
    Stereotype(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // (ComponentInstanceName DOT)* PortInstanceName
  public static boolean QualifiedIdentifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "QualifiedIdentifier")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = QualifiedIdentifier_0(b, l + 1);
    r = r && PortInstanceName(b, l + 1);
    exit_section_(b, m, QUALIFIED_IDENTIFIER, r);
    return r;
  }

  // (ComponentInstanceName DOT)*
  private static boolean QualifiedIdentifier_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "QualifiedIdentifier_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!QualifiedIdentifier_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "QualifiedIdentifier_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ComponentInstanceName DOT
  private static boolean QualifiedIdentifier_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "QualifiedIdentifier_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ComponentInstanceName(b, l + 1);
    r = r && consumeToken(b, DOT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ID
  public static boolean RoleName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RoleName")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    exit_section_(b, m, ROLE_NAME, r);
    return r;
  }

  /* ********************************************************** */
  // Prefix? Connector
  static boolean SimpleConnectPortStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SimpleConnectPortStatement")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = SimpleConnectPortStatement_0(b, l + 1);
    r = r && Connector(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Prefix?
  private static boolean SimpleConnectPortStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SimpleConnectPortStatement_0")) return false;
    Prefix(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ComponentDeclaration |
  //     TrustLevelStatement |
  //     TrustLevelRelationStatement |
  //     CPEStatement |
  //     ConfigurationStatement |
  //     AutoConnectStatement |
  //     AccessControlStatement |
  //     PortDeclaration |
  //     ComponentInstanceDeclaration |
  //     ConnectPortStatement |
  //     AccessStatement |
  //     IdentityStatement
  static boolean Statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = ComponentDeclaration(b, l + 1);
    if (!r) r = TrustLevelStatement(b, l + 1);
    if (!r) r = TrustLevelRelationStatement(b, l + 1);
    if (!r) r = CPEStatement(b, l + 1);
    if (!r) r = ConfigurationStatement(b, l + 1);
    if (!r) r = AutoConnectStatement(b, l + 1);
    if (!r) r = AccessControlStatement(b, l + 1);
    if (!r) r = PortDeclaration(b, l + 1);
    if (!r) r = ComponentInstanceDeclaration(b, l + 1);
    if (!r) r = ConnectPortStatement(b, l + 1);
    if (!r) r = AccessStatement(b, l + 1);
    if (!r) r = IdentityStatement(b, l + 1);
    exit_section_(b, l, m, r, false, statement_recover_parser_);
    return r;
  }

  /* ********************************************************** */
  // ID ("=" STRING)?
  static boolean StereoValue(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StereoValue")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    r = r && StereoValue_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ("=" STRING)?
  private static boolean StereoValue_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StereoValue_1")) return false;
    StereoValue_1_0(b, l + 1);
    return true;
  }

  // "=" STRING
  private static boolean StereoValue_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StereoValue_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EQUAL);
    r = r && consumeToken(b, STRING);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '<<' StereoValue ("," StereoValue)* '>' '>'
  public static boolean Stereotype(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Stereotype")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STEREOTYPE, "<stereotype>");
    r = consumeToken(b, "<<");
    r = r && StereoValue(b, l + 1);
    r = r && Stereotype_2(b, l + 1);
    r = r && consumeToken(b, GREATER);
    r = r && consumeToken(b, GREATER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ("," StereoValue)*
  private static boolean Stereotype_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Stereotype_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!Stereotype_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Stereotype_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // "," StereoValue
  private static boolean Stereotype_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Stereotype_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && StereoValue(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // SuppressAnnotationKeyword '(' Policy (COMMA Policy)* ')'
  public static boolean SuppressAnnotation(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SuppressAnnotation")) return false;
    if (!nextTokenIs(b, AT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SUPPRESS_ANNOTATION, null);
    r = SuppressAnnotationKeyword(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, LPAREN));
    r = p && report_error_(b, Policy(b, l + 1)) && r;
    r = p && report_error_(b, SuppressAnnotation_3(b, l + 1)) && r;
    r = p && consumeToken(b, RPAREN) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (COMMA Policy)*
  private static boolean SuppressAnnotation_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SuppressAnnotation_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!SuppressAnnotation_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "SuppressAnnotation_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA Policy
  private static boolean SuppressAnnotation_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SuppressAnnotation_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && Policy(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // AT SUPPRESS_POLICY_TOKEN
  public static boolean SuppressAnnotationKeyword(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SuppressAnnotationKeyword")) return false;
    if (!nextTokenIs(b, AT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SUPPRESS_ANNOTATION_KEYWORD, null);
    r = consumeTokens(b, 1, AT, SUPPRESS_POLICY_TOKEN);
    p = r; // pin = 1
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // (ComponentInstanceName DOT)* ComponentInstanceName
  public static boolean TrustLevelIdentifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TrustLevelIdentifier")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = TrustLevelIdentifier_0(b, l + 1);
    r = r && ComponentInstanceName(b, l + 1);
    exit_section_(b, m, TRUST_LEVEL_IDENTIFIER, r);
    return r;
  }

  // (ComponentInstanceName DOT)*
  private static boolean TrustLevelIdentifier_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TrustLevelIdentifier_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!TrustLevelIdentifier_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "TrustLevelIdentifier_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ComponentInstanceName DOT
  private static boolean TrustLevelIdentifier_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TrustLevelIdentifier_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ComponentInstanceName(b, l + 1);
    r = r && consumeToken(b, DOT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Prefix? trustlevelrelation TrustLevelIdentifier { EQUAL | LESS | GREATER } TrustLevelIdentifier semi
  public static boolean TrustLevelRelationStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TrustLevelRelationStatement")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TRUST_LEVEL_RELATION_STATEMENT, "<trust level relation statement>");
    r = TrustLevelRelationStatement_0(b, l + 1);
    r = r && consumeToken(b, TRUSTLEVELRELATION);
    p = r; // pin = 2
    r = r && report_error_(b, TrustLevelIdentifier(b, l + 1));
    r = p && report_error_(b, TrustLevelRelationStatement_3(b, l + 1)) && r;
    r = p && report_error_(b, TrustLevelIdentifier(b, l + 1)) && r;
    r = p && semi(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // Prefix?
  private static boolean TrustLevelRelationStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TrustLevelRelationStatement_0")) return false;
    Prefix(b, l + 1);
    return true;
  }

  // EQUAL | LESS | GREATER
  private static boolean TrustLevelRelationStatement_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TrustLevelRelationStatement_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EQUAL);
    if (!r) r = consumeToken(b, LESS);
    if (!r) r = consumeToken(b, GREATER);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Prefix? trustlevel LEVEL [STRING] semi
  public static boolean TrustLevelStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TrustLevelStatement")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TRUST_LEVEL_STATEMENT, "<trust level statement>");
    r = TrustLevelStatement_0(b, l + 1);
    r = r && consumeToken(b, TRUSTLEVEL);
    p = r; // pin = 2
    r = r && report_error_(b, LEVEL(b, l + 1));
    r = p && report_error_(b, TrustLevelStatement_3(b, l + 1)) && r;
    r = p && semi(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // Prefix?
  private static boolean TrustLevelStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TrustLevelStatement_0")) return false;
    Prefix(b, l + 1);
    return true;
  }

  // [STRING]
  private static boolean TrustLevelStatement_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TrustLevelStatement_3")) return false;
    consumeToken(b, STRING);
    return true;
  }

  /* ********************************************************** */
  // LESS TypeVariableDeclaration (COMMA TypeVariableDeclaration)* GREATER
  public static boolean TypeParameters(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeParameters")) return false;
    if (!nextTokenIs(b, LESS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LESS);
    r = r && TypeVariableDeclaration(b, l + 1);
    r = r && TypeParameters_2(b, l + 1);
    r = r && consumeToken(b, GREATER);
    exit_section_(b, m, TYPE_PARAMETERS, r);
    return r;
  }

  // (COMMA TypeVariableDeclaration)*
  private static boolean TypeParameters_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeParameters_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!TypeParameters_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "TypeParameters_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA TypeVariableDeclaration
  private static boolean TypeParameters_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeParameters_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && TypeVariableDeclaration(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ID (extends JavaClassReference)?
  static boolean TypeVariableDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeVariableDeclaration")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    r = r && TypeVariableDeclaration_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (extends JavaClassReference)?
  private static boolean TypeVariableDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeVariableDeclaration_1")) return false;
    TypeVariableDeclaration_1_0(b, l + 1);
    return true;
  }

  // extends JavaClassReference
  private static boolean TypeVariableDeclaration_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeVariableDeclaration_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EXTENDS);
    r = r && JavaClassReference(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !('<<' | component | import | semi | AT)
  static boolean import_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !import_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '<<' | component | import | semi | AT
  private static boolean import_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "<<");
    if (!r) r = consumeToken(b, COMPONENT);
    if (!r) r = consumeToken(b, IMPORT);
    if (!r) r = semi(b, l + 1);
    if (!r) r = consumeToken(b, AT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !(';' | '<<' | <<eof>> | import | component | AT)
  static boolean package_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !package_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ';' | '<<' | <<eof>> | import | component | AT
  private static boolean package_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SEMICOLON);
    if (!r) r = consumeToken(b, "<<");
    if (!r) r = eof(b, l + 1);
    if (!r) r = consumeToken(b, IMPORT);
    if (!r) r = consumeToken(b, COMPONENT);
    if (!r) r = consumeToken(b, AT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !(';' | ',' | CRITICAL)
  static boolean port_element_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "port_element_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !port_element_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ';' | ',' | CRITICAL
  private static boolean port_element_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "port_element_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SEMICOLON);
    if (!r) r = consumeToken(b, COMMA);
    if (!r) r = consumeToken(b, CRITICAL);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // SEMICOLON_SYNTHETIC | SEMICOLON | <<eof>>
  static boolean semi(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "semi")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<semicolon>");
    r = consumeToken(b, SEMICOLON_SYNTHETIC);
    if (!r) r = consumeToken(b, SEMICOLON);
    if (!r) r = eof(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // !(<<eof>> | LBRACE | RBRACE | component)
  static boolean signature_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "signature_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !signature_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // <<eof>> | LBRACE | RBRACE | component
  private static boolean signature_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "signature_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = eof(b, l + 1);
    if (!r) r = consumeToken(b, LBRACE);
    if (!r) r = consumeToken(b, RBRACE);
    if (!r) r = consumeToken(b, COMPONENT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !( '<<' | AT | semi | trustlevel | trustlevelrelation | IDENTITY | ACCESSCONTROL | ACCESS | port | component | connect | CPE | CONFIGURATION | RBRACE | ComponentName)
  static boolean statement_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !statement_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '<<' | AT | semi | trustlevel | trustlevelrelation | IDENTITY | ACCESSCONTROL | ACCESS | port | component | connect | CPE | CONFIGURATION | RBRACE | ComponentName
  private static boolean statement_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "<<");
    if (!r) r = consumeToken(b, AT);
    if (!r) r = semi(b, l + 1);
    if (!r) r = consumeToken(b, TRUSTLEVEL);
    if (!r) r = consumeToken(b, TRUSTLEVELRELATION);
    if (!r) r = consumeToken(b, IDENTITY);
    if (!r) r = consumeToken(b, ACCESSCONTROL);
    if (!r) r = consumeToken(b, ACCESS);
    if (!r) r = consumeToken(b, PORT);
    if (!r) r = consumeToken(b, COMPONENT);
    if (!r) r = consumeToken(b, CONNECT);
    if (!r) r = consumeToken(b, CPE);
    if (!r) r = consumeToken(b, CONFIGURATION);
    if (!r) r = consumeToken(b, RBRACE);
    if (!r) r = ComponentName(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  final static Parser import_recover_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return import_recover(b, l + 1);
    }
  };
  final static Parser package_recover_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return package_recover(b, l + 1);
    }
  };
  final static Parser port_element_recover_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return port_element_recover(b, l + 1);
    }
  };
  final static Parser signature_recover_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return signature_recover(b, l + 1);
    }
  };
  final static Parser statement_recover_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return statement_recover(b, l + 1);
    }
  };
}
