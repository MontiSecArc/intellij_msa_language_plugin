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
    else if (t == COMPONENT_INSTANCE_DECLARATION) {
      r = ComponentInstanceDeclaration(b, 0);
    }
    else if (t == COMPONENT_INSTANCE_NAME) {
      r = ComponentInstanceName(b, 0);
    }
    else if (t == COMPONENT_NAME) {
      r = ComponentName(b, 0);
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
    else if (t == IDENTITY_STATEMENT) {
      r = IdentityStatement(b, 0);
    }
    else if (t == IMPORT_DECLARATION) {
      r = ImportDeclaration(b, 0);
    }
    else if (t == LEVEL) {
      r = LEVEL(b, 0);
    }
    else if (t == PACKAGE_CLAUSE) {
      r = PackageClause(b, 0);
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
    else if (t == REFERENCE_TYPE) {
      r = ReferenceType(b, 0);
    }
    else if (t == ROLE_NAME) {
      r = RoleName(b, 0);
    }
    else if (t == STEREOTYPE) {
      r = Stereotype(b, 0);
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
  // Stereotype? ACCESSCONTROL { ON | OFF } semi
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

  // Stereotype?
  private static boolean AccessControlStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AccessControlStatement_0")) return false;
    Stereotype(b, l + 1);
    return true;
  }

  // ON | OFF
  private static boolean AccessControlStatement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AccessControlStatement_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, ON);
    if (!r) r = consumeTokenFast(b, OFF);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PortAccessRole | RoleName
  static boolean AccessRoles(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AccessRoles")) return false;
    if (!nextTokenIsFast(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = PortAccessRole(b, l + 1);
    if (!r) r = RoleName(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Stereotype? ACCESS AccessRoles ( COMMA AccessRoles )* semi
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

  // Stereotype?
  private static boolean AccessStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AccessStatement_0")) return false;
    Stereotype(b, l + 1);
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
    r = consumeTokenFast(b, COMMA);
    r = r && AccessRoles(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Stereotype? AUTOCONNECT [ENCRYPTED | UNENCRYPTED] { TYPE | port | OFF } semi
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

  // Stereotype?
  private static boolean AutoConnectStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AutoConnectStatement_0")) return false;
    Stereotype(b, l + 1);
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
    r = consumeTokenFast(b, ENCRYPTED);
    if (!r) r = consumeTokenFast(b, UNENCRYPTED);
    exit_section_(b, m, null, r);
    return r;
  }

  // TYPE | port | OFF
  private static boolean AutoConnectStatement_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AutoConnectStatement_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, TYPE);
    if (!r) r = consumeTokenFast(b, PORT);
    if (!r) r = consumeTokenFast(b, OFF);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Stereotype? CPE STRING semi
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

  // Stereotype?
  private static boolean CPEStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CPEStatement_0")) return false;
    Stereotype(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // SimpleReferenceType  (DOT SimpleReferenceType)*
  static boolean ComplexReferenceType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComplexReferenceType")) return false;
    if (!nextTokenIsFast(b, ID)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = SimpleReferenceType(b, l + 1);
    p = r; // pin = 1
    r = r && ComplexReferenceType_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (DOT SimpleReferenceType)*
  private static boolean ComplexReferenceType_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComplexReferenceType_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!ComplexReferenceType_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ComplexReferenceType_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // DOT SimpleReferenceType
  private static boolean ComplexReferenceType_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComplexReferenceType_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, DOT);
    r = r && SimpleReferenceType(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // LBRACE (Statement | ComponentDeclaration)* RBRACE
  public static boolean ComponentBody(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentBody")) return false;
    if (!nextTokenIsFast(b, LBRACE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, COMPONENT_BODY, null);
    r = consumeTokenFast(b, LBRACE);
    p = r; // pin = 1
    r = r && report_error_(b, ComponentBody_1(b, l + 1));
    r = p && consumeToken(b, RBRACE) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (Statement | ComponentDeclaration)*
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

  // Statement | ComponentDeclaration
  private static boolean ComponentBody_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentBody_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Statement(b, l + 1);
    if (!r) r = ComponentDeclaration(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Stereotype? component ComponentSignature ComponentBody
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

  // Stereotype?
  private static boolean ComponentDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentDeclaration_0")) return false;
    Stereotype(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ComponentInstanceName (LBRACK SimpleConnectPortStatement (semi SimpleConnectPortStatement)* RBRACK)?
  static boolean ComponentInstance(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstance")) return false;
    if (!nextTokenIsFast(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ComponentInstanceName(b, l + 1);
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
    r = consumeTokenFast(b, LBRACK);
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
  // Stereotype? InputFilter? ReferenceType ("(" QualifiedIdentifier ("," QualifiedIdentifier)* ")" )? ComponentInstance (COMMA ComponentInstance)* semi
  public static boolean ComponentInstanceDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstanceDeclaration")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, COMPONENT_INSTANCE_DECLARATION, "<component instance declaration>");
    r = ComponentInstanceDeclaration_0(b, l + 1);
    r = r && ComponentInstanceDeclaration_1(b, l + 1);
    r = r && ReferenceType(b, l + 1);
    p = r; // pin = 3
    r = r && report_error_(b, ComponentInstanceDeclaration_3(b, l + 1));
    r = p && report_error_(b, ComponentInstance(b, l + 1)) && r;
    r = p && report_error_(b, ComponentInstanceDeclaration_5(b, l + 1)) && r;
    r = p && semi(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // Stereotype?
  private static boolean ComponentInstanceDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstanceDeclaration_0")) return false;
    Stereotype(b, l + 1);
    return true;
  }

  // InputFilter?
  private static boolean ComponentInstanceDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstanceDeclaration_1")) return false;
    InputFilter(b, l + 1);
    return true;
  }

  // ("(" QualifiedIdentifier ("," QualifiedIdentifier)* ")" )?
  private static boolean ComponentInstanceDeclaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstanceDeclaration_3")) return false;
    ComponentInstanceDeclaration_3_0(b, l + 1);
    return true;
  }

  // "(" QualifiedIdentifier ("," QualifiedIdentifier)* ")"
  private static boolean ComponentInstanceDeclaration_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstanceDeclaration_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, LPAREN);
    r = r && QualifiedIdentifier(b, l + 1);
    r = r && ComponentInstanceDeclaration_3_0_2(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // ("," QualifiedIdentifier)*
  private static boolean ComponentInstanceDeclaration_3_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstanceDeclaration_3_0_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!ComponentInstanceDeclaration_3_0_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ComponentInstanceDeclaration_3_0_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // "," QualifiedIdentifier
  private static boolean ComponentInstanceDeclaration_3_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstanceDeclaration_3_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, COMMA);
    r = r && QualifiedIdentifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA ComponentInstance)*
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

  // COMMA ComponentInstance
  private static boolean ComponentInstanceDeclaration_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstanceDeclaration_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, COMMA);
    r = r && ComponentInstance(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ID
  public static boolean ComponentInstanceName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentInstanceName")) return false;
    if (!nextTokenIsFast(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, ID);
    exit_section_(b, m, COMPONENT_INSTANCE_NAME, r);
    return r;
  }

  /* ********************************************************** */
  // ID
  public static boolean ComponentName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentName")) return false;
    if (!nextTokenIsFast(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, ID);
    exit_section_(b, m, COMPONENT_NAME, r);
    return r;
  }

  /* ********************************************************** */
  // InputFilter? ComponentName TypeParameters? ("(" (Parameter | ",")+ ")")? (extends ReferenceType)? ComponentInstanceName?
  public static boolean ComponentSignature(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentSignature")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, COMPONENT_SIGNATURE, "<component signature>");
    r = ComponentSignature_0(b, l + 1);
    r = r && ComponentName(b, l + 1);
    p = r; // pin = 2
    r = r && report_error_(b, ComponentSignature_2(b, l + 1));
    r = p && report_error_(b, ComponentSignature_3(b, l + 1)) && r;
    r = p && report_error_(b, ComponentSignature_4(b, l + 1)) && r;
    r = p && ComponentSignature_5(b, l + 1) && r;
    exit_section_(b, l, m, r, p, signature_recover_parser_);
    return r || p;
  }

  // InputFilter?
  private static boolean ComponentSignature_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentSignature_0")) return false;
    InputFilter(b, l + 1);
    return true;
  }

  // TypeParameters?
  private static boolean ComponentSignature_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentSignature_2")) return false;
    TypeParameters(b, l + 1);
    return true;
  }

  // ("(" (Parameter | ",")+ ")")?
  private static boolean ComponentSignature_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentSignature_3")) return false;
    ComponentSignature_3_0(b, l + 1);
    return true;
  }

  // "(" (Parameter | ",")+ ")"
  private static boolean ComponentSignature_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentSignature_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, LPAREN);
    r = r && ComponentSignature_3_0_1(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // (Parameter | ",")+
  private static boolean ComponentSignature_3_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentSignature_3_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ComponentSignature_3_0_1_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!ComponentSignature_3_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ComponentSignature_3_0_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // Parameter | ","
  private static boolean ComponentSignature_3_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentSignature_3_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Parameter(b, l + 1);
    if (!r) r = consumeTokenFast(b, COMMA);
    exit_section_(b, m, null, r);
    return r;
  }

  // (extends ReferenceType)?
  private static boolean ComponentSignature_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentSignature_4")) return false;
    ComponentSignature_4_0(b, l + 1);
    return true;
  }

  // extends ReferenceType
  private static boolean ComponentSignature_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentSignature_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, EXTENDS);
    r = r && ReferenceType(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ComponentInstanceName?
  private static boolean ComponentSignature_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ComponentSignature_5")) return false;
    ComponentInstanceName(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // Stereotype? CONFIGURATION ID semi
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

  // Stereotype?
  private static boolean ConfigurationStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConfigurationStatement_0")) return false;
    Stereotype(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // Stereotype? connect Connector semi
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

  // Stereotype?
  private static boolean ConnectPortStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConnectPortStatement_0")) return false;
    Stereotype(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // QualifiedIdentifier
  public static boolean ConnectSource(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConnectSource")) return false;
    if (!nextTokenIsFast(b, ID)) return false;
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
    if (!nextTokenIsFast(b, ID)) return false;
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
    r = consumeTokenFast(b, ENCRYPTED);
    if (!r) r = consumeTokenFast(b, UNENCRYPTED);
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
    r = consumeTokenFast(b, WEAK_AUTH);
    if (!r) r = consumeTokenFast(b, STRONG_AUTH);
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
    r = consumeTokenFast(b, COMMA);
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
  static boolean IdentityIdentifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IdentityIdentifier")) return false;
    if (!nextTokenIsFast(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = IdentityIdentifier_0(b, l + 1);
    r = r && ComponentInstanceName(b, l + 1);
    r = r && IdentityIdentifier_2(b, l + 1);
    exit_section_(b, m, null, r);
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
  // Stereotype? IDENTITY { STRONG | WEAK } IdentityIdentifier ARROW IdentityIdentifier ( COMMA IdentityIdentifier)* semi
  public static boolean IdentityStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IdentityStatement")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IDENTITY_STATEMENT, "<identity statement>");
    r = IdentityStatement_0(b, l + 1);
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

  // Stereotype?
  private static boolean IdentityStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IdentityStatement_0")) return false;
    Stereotype(b, l + 1);
    return true;
  }

  // STRONG | WEAK
  private static boolean IdentityStatement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IdentityStatement_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, STRONG);
    if (!r) r = consumeTokenFast(b, WEAK);
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
    r = consumeTokenFast(b, COMMA);
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
    r = consumeTokenFast(b, DOT);
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
  // LPAREN filter ID RPAREN
  static boolean InputFilter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InputFilter")) return false;
    if (!nextTokenIsFast(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, LPAREN, FILTER, ID, RPAREN);
    exit_section_(b, m, null, r);
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
    r = consumeTokenFast(b, PLUS);
    if (!r) r = consumeTokenFast(b, MINUS);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // package (DOT | ID)+ semi
  public static boolean PackageClause(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PackageClause")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PACKAGE_CLAUSE, "<package clause>");
    r = consumeTokenFast(b, PACKAGE);
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
    r = consumeTokenFast(b, DOT);
    if (!r) r = consumeTokenFast(b, ID);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ReferenceType ID ("=" ID)?
  static boolean Parameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Parameter")) return false;
    if (!nextTokenIsFast(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ReferenceType(b, l + 1);
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
    r = consumeTokenFast(b, EQUAL);
    r = r && consumeToken(b, ID);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // QualifiedIdentifier LPAREN RoleName ( COMMA RoleName )* RPAREN
  public static boolean PortAccessRole(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortAccessRole")) return false;
    if (!nextTokenIsFast(b, ID)) return false;
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
    r = consumeTokenFast(b, COMMA);
    r = r && RoleName(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Stereotype? port PortElement ( COMMA PortElement )* semi
  public static boolean PortDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortDeclaration")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PORT_DECLARATION, "<port declaration>");
    r = PortDeclaration_0(b, l + 1);
    r = r && consumeToken(b, PORT);
    p = r; // pin = 2
    r = r && report_error_(b, PortElement(b, l + 1));
    r = p && report_error_(b, PortDeclaration_3(b, l + 1)) && r;
    r = p && semi(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // Stereotype?
  private static boolean PortDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortDeclaration_0")) return false;
    Stereotype(b, l + 1);
    return true;
  }

  // ( COMMA PortElement )*
  private static boolean PortDeclaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortDeclaration_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!PortDeclaration_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "PortDeclaration_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA PortElement
  private static boolean PortDeclaration_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortDeclaration_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, COMMA);
    r = r && PortElement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ['critical' | InputFilter] { 'in' | 'out' } ReferenceType [PortInstanceName]
  public static boolean PortElement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortElement")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PORT_ELEMENT, "<port>");
    r = PortElement_0(b, l + 1);
    r = r && PortElement_1(b, l + 1);
    p = r; // pin = 2
    r = r && report_error_(b, ReferenceType(b, l + 1));
    r = p && PortElement_3(b, l + 1) && r;
    exit_section_(b, l, m, r, p, port_element_recover_parser_);
    return r || p;
  }

  // ['critical' | InputFilter]
  private static boolean PortElement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortElement_0")) return false;
    PortElement_0_0(b, l + 1);
    return true;
  }

  // 'critical' | InputFilter
  private static boolean PortElement_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortElement_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, CRITICAL);
    if (!r) r = InputFilter(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'in' | 'out'
  private static boolean PortElement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PortElement_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, IN);
    if (!r) r = consumeTokenFast(b, OUT);
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
    if (!nextTokenIsFast(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, ID);
    exit_section_(b, m, PORT_INSTANCE_NAME, r);
    return r;
  }

  /* ********************************************************** */
  // (ComponentInstanceName DOT)* PortInstanceName
  static boolean QualifiedIdentifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "QualifiedIdentifier")) return false;
    if (!nextTokenIsFast(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = QualifiedIdentifier_0(b, l + 1);
    r = r && PortInstanceName(b, l + 1);
    exit_section_(b, m, null, r);
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
  // SimpleReferenceType | ComplexReferenceType
  public static boolean ReferenceType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ReferenceType")) return false;
    if (!nextTokenIsFast(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = SimpleReferenceType(b, l + 1);
    if (!r) r = ComplexReferenceType(b, l + 1);
    exit_section_(b, m, REFERENCE_TYPE, r);
    return r;
  }

  /* ********************************************************** */
  // ID
  public static boolean RoleName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RoleName")) return false;
    if (!nextTokenIsFast(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, ID);
    exit_section_(b, m, ROLE_NAME, r);
    return r;
  }

  /* ********************************************************** */
  // Stereotype? Connector
  static boolean SimpleConnectPortStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SimpleConnectPortStatement")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = SimpleConnectPortStatement_0(b, l + 1);
    r = r && Connector(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Stereotype?
  private static boolean SimpleConnectPortStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SimpleConnectPortStatement_0")) return false;
    Stereotype(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // (ID ".")* ComponentName (TypeArguments)?
  static boolean SimpleReferenceType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SimpleReferenceType")) return false;
    if (!nextTokenIsFast(b, ID)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = SimpleReferenceType_0(b, l + 1);
    r = r && ComponentName(b, l + 1);
    p = r; // pin = ComponentName
    r = r && SimpleReferenceType_2(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (ID ".")*
  private static boolean SimpleReferenceType_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SimpleReferenceType_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!SimpleReferenceType_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "SimpleReferenceType_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ID "."
  private static boolean SimpleReferenceType_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SimpleReferenceType_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, ID);
    r = r && consumeToken(b, DOT);
    exit_section_(b, m, null, r);
    return r;
  }

  // (TypeArguments)?
  private static boolean SimpleReferenceType_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SimpleReferenceType_2")) return false;
    SimpleReferenceType_2_0(b, l + 1);
    return true;
  }

  // (TypeArguments)
  private static boolean SimpleReferenceType_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SimpleReferenceType_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = TypeArguments(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TrustLevelStatement |
  //                                 TrustLevelRelationStatement |
  //                                 CPEStatement |
  //                                 ConfigurationStatement |
  //                                 AutoConnectStatement |
  //                                 AccessControlStatement |
  //     PortDeclaration |
  //     ComponentInstanceDeclaration
  //     | ConnectPortStatement
  //     | AccessStatement
  //     | IdentityStatement
  static boolean Statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = TrustLevelStatement(b, l + 1);
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
    if (!nextTokenIsFast(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, ID);
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
    r = consumeTokenFast(b, EQUAL);
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
    r = consumeTokenFast(b, "<<");
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
    r = consumeTokenFast(b, COMMA);
    r = r && StereoValue(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (ComponentInstanceName DOT)* ComponentInstanceName
  static boolean TrustLevelIdentifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TrustLevelIdentifier")) return false;
    if (!nextTokenIsFast(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = TrustLevelIdentifier_0(b, l + 1);
    r = r && ComponentInstanceName(b, l + 1);
    exit_section_(b, m, null, r);
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
  // Stereotype? trustlevelrelation TrustLevelIdentifier { EQUAL | LESS | GREATER } TrustLevelIdentifier semi
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

  // Stereotype?
  private static boolean TrustLevelRelationStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TrustLevelRelationStatement_0")) return false;
    Stereotype(b, l + 1);
    return true;
  }

  // EQUAL | LESS | GREATER
  private static boolean TrustLevelRelationStatement_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TrustLevelRelationStatement_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, EQUAL);
    if (!r) r = consumeTokenFast(b, LESS);
    if (!r) r = consumeTokenFast(b, GREATER);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Stereotype? trustlevel LEVEL [STRING] semi
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

  // Stereotype?
  private static boolean TrustLevelStatement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TrustLevelStatement_0")) return false;
    Stereotype(b, l + 1);
    return true;
  }

  // [STRING]
  private static boolean TrustLevelStatement_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TrustLevelStatement_3")) return false;
    consumeTokenFast(b, STRING);
    return true;
  }

  /* ********************************************************** */
  // "<" (WildcardType | ",")* ">"
  static boolean TypeArguments(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeArguments")) return false;
    if (!nextTokenIsFast(b, LESS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, LESS);
    r = r && TypeArguments_1(b, l + 1);
    r = r && consumeToken(b, GREATER);
    exit_section_(b, m, null, r);
    return r;
  }

  // (WildcardType | ",")*
  private static boolean TypeArguments_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeArguments_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!TypeArguments_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "TypeArguments_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // WildcardType | ","
  private static boolean TypeArguments_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeArguments_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = WildcardType(b, l + 1);
    if (!r) r = consumeTokenFast(b, COMMA);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // LESS TypeVariableDeclaration (COMMA TypeVariableDeclaration)* GREATER
  public static boolean TypeParameters(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeParameters")) return false;
    if (!nextTokenIsFast(b, LESS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, LESS);
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
    r = consumeTokenFast(b, COMMA);
    r = r && TypeVariableDeclaration(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ID (extends (ComplexReferenceType | "&")+)?
  static boolean TypeVariableDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeVariableDeclaration")) return false;
    if (!nextTokenIsFast(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, ID);
    r = r && TypeVariableDeclaration_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (extends (ComplexReferenceType | "&")+)?
  private static boolean TypeVariableDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeVariableDeclaration_1")) return false;
    TypeVariableDeclaration_1_0(b, l + 1);
    return true;
  }

  // extends (ComplexReferenceType | "&")+
  private static boolean TypeVariableDeclaration_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeVariableDeclaration_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, EXTENDS);
    r = r && TypeVariableDeclaration_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (ComplexReferenceType | "&")+
  private static boolean TypeVariableDeclaration_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeVariableDeclaration_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = TypeVariableDeclaration_1_0_1_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!TypeVariableDeclaration_1_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "TypeVariableDeclaration_1_0_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // ComplexReferenceType | "&"
  private static boolean TypeVariableDeclaration_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeVariableDeclaration_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ComplexReferenceType(b, l + 1);
    if (!r) r = consumeTokenFast(b, "&");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '?' ((extends ReferenceType) | (super ReferenceType))?
  static boolean WildcardType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "WildcardType")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, "?");
    r = r && WildcardType_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ((extends ReferenceType) | (super ReferenceType))?
  private static boolean WildcardType_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "WildcardType_1")) return false;
    WildcardType_1_0(b, l + 1);
    return true;
  }

  // (extends ReferenceType) | (super ReferenceType)
  private static boolean WildcardType_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "WildcardType_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = WildcardType_1_0_0(b, l + 1);
    if (!r) r = WildcardType_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // extends ReferenceType
  private static boolean WildcardType_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "WildcardType_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, EXTENDS);
    r = r && ReferenceType(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // super ReferenceType
  private static boolean WildcardType_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "WildcardType_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, SUPER);
    r = r && ReferenceType(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !('<<' | component | import | semi)
  static boolean import_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !import_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '<<' | component | import | semi
  private static boolean import_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, "<<");
    if (!r) r = consumeTokenFast(b, COMPONENT);
    if (!r) r = consumeTokenFast(b, IMPORT);
    if (!r) r = semi(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !(';' | '<<' | <<eof>> | import | component)
  static boolean package_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !package_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ';' | '<<' | <<eof>> | import | component
  private static boolean package_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "package_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, SEMICOLON);
    if (!r) r = consumeTokenFast(b, "<<");
    if (!r) r = eof(b, l + 1);
    if (!r) r = consumeTokenFast(b, IMPORT);
    if (!r) r = consumeTokenFast(b, COMPONENT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !(';' | ',' | InputFilter | CRITICAL)
  static boolean port_element_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "port_element_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !port_element_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ';' | ',' | InputFilter | CRITICAL
  private static boolean port_element_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "port_element_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, SEMICOLON);
    if (!r) r = consumeTokenFast(b, COMMA);
    if (!r) r = InputFilter(b, l + 1);
    if (!r) r = consumeTokenFast(b, CRITICAL);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // SEMICOLON_SYNTHETIC | SEMICOLON | <<eof>>
  static boolean semi(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "semi")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<semicolon>");
    r = consumeTokenFast(b, SEMICOLON_SYNTHETIC);
    if (!r) r = consumeTokenFast(b, SEMICOLON);
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
    if (!r) r = consumeTokenFast(b, LBRACE);
    if (!r) r = consumeTokenFast(b, RBRACE);
    if (!r) r = consumeTokenFast(b, COMPONENT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !('<<' | semi | trustlevel | trustlevelrelation | IDENTITY | ACCESSCONTROL | ACCESS | port | component | connect | CPE | CONFIGURATION | RBRACE | ReferenceType)
  static boolean statement_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !statement_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '<<' | semi | trustlevel | trustlevelrelation | IDENTITY | ACCESSCONTROL | ACCESS | port | component | connect | CPE | CONFIGURATION | RBRACE | ReferenceType
  private static boolean statement_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, "<<");
    if (!r) r = semi(b, l + 1);
    if (!r) r = consumeTokenFast(b, TRUSTLEVEL);
    if (!r) r = consumeTokenFast(b, TRUSTLEVELRELATION);
    if (!r) r = consumeTokenFast(b, IDENTITY);
    if (!r) r = consumeTokenFast(b, ACCESSCONTROL);
    if (!r) r = consumeTokenFast(b, ACCESS);
    if (!r) r = consumeTokenFast(b, PORT);
    if (!r) r = consumeTokenFast(b, COMPONENT);
    if (!r) r = consumeTokenFast(b, CONNECT);
    if (!r) r = consumeTokenFast(b, CPE);
    if (!r) r = consumeTokenFast(b, CONFIGURATION);
    if (!r) r = consumeTokenFast(b, RBRACE);
    if (!r) r = ReferenceType(b, l + 1);
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
