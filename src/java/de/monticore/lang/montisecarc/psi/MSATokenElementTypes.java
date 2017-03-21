// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi;

import com.intellij.psi.tree.TokenSet;

public interface MSATokenElementTypes {

  // Keywords
  MSATokenType ACCESS = new MSAKeywordTokenType("access");
  MSATokenType ACCESSCONTROL = new MSAKeywordTokenType("accesscontrol");
  MSATokenType IDENTITY = new MSAKeywordTokenType("identity");
  MSATokenType IMPORT = new MSAKeywordTokenType("import");
  MSATokenType AUTOCONNECT = new MSAKeywordTokenType("autoconnect");
  MSATokenType COMPONENT = new MSAKeywordTokenType("component");
  MSATokenType CONFIGURATION = new MSAKeywordTokenType("configuration");
  MSATokenType CONNECT = new MSAKeywordTokenType("connect");
  MSATokenType TRUSTLEVEL = new MSAKeywordTokenType("trustlevel");
  MSATokenType TRUSTLEVELRELATION = new MSAKeywordTokenType("trustlevelrelation");
  MSATokenType CPE = new MSAKeywordTokenType("cpe");
  MSATokenType PORT = new MSATokenType("port");
  MSATokenType PACKAGE = new MSATokenType("package");
  MSATokenType CLEARANCEFOR = new MSATokenType("clearanceFor");

  // Operators
  MSATokenType ARROW = new MSAOperatorTokenType("->");
  MSATokenType LESS = new MSAOperatorTokenType("<");
  MSATokenType EQUAL = new MSAOperatorTokenType("=");

  MSATokenType ASTERIX = new MSATokenType("*");
  MSATokenType COLON = new MSATokenType(":");
  MSATokenType COMMA = new MSATokenType(",");
  MSATokenType DOT = new MSATokenType(".");
  MSATokenType EXTENDS = new MSATokenType("extends");
  MSATokenType GREATER = new MSATokenType(">");
  MSATokenType ID = new MSATokenType("ID");
  MSATokenType LBRACE = new MSATokenType("{");
  MSATokenType LBRACK = new MSATokenType("[");
  MSATokenType LPAREN = new MSATokenType("(");
  MSATokenType MINUS = new MSATokenType("-");
  MSATokenType NUMBER = new MSATokenType("NUMBER");
  MSATokenType PLUS = new MSATokenType("+");
  MSATokenType RBRACE = new MSATokenType("}");
  MSATokenType RBRACK = new MSATokenType("]");
  MSATokenType RPAREN = new MSATokenType(")");
  MSATokenType SEMICOLON = new MSATokenType(";");
  MSATokenType SEMICOLON_SYNTHETIC = new MSATokenType("<NL>");
  MSATokenType STRING = new MSATokenType("STRING");

  // Attributes
  MSATokenType STRONG = new MSATokenType("strong");
  MSATokenType TYPE = new MSATokenType("type");
  MSATokenType ENCRYPTED = new MSATokenType("encrypted");
  MSATokenType CRITICAL = new MSATokenType("critical");
  MSATokenType UNENCRYPTED = new MSAAttributeTokenType("unencrypted");
  MSATokenType WEAK = new MSAAttributeTokenType("weak");

  MSATokenType IN = new MSATokenType("in");
  MSATokenType OUT = new MSATokenType("out");

  MSATokenType ON = new MSATokenType("on");
  MSATokenType OFF = new MSATokenType("off");

  MSATokenType SUPPRESS_POLICY_TOKEN = new MSAKeywordTokenType("SuppressPolicy");
  MSATokenType AT = new MSAKeywordTokenType("@");

  // Comments
  MSATokenType SINGLE_LINE_COMMENT = new MSACommentTokenType("SINGLE_LINE_COMMENT");
  MSATokenType MULTI_LINE_COMMENT = new MSACommentTokenType("MULTI_LINE_COMMENT");

  TokenSet COMMENTS = TokenSet.create(SINGLE_LINE_COMMENT, MULTI_LINE_COMMENT);
  TokenSet KEYWORDS = TokenSet.create(COMPONENT, PORT, CPE, CONFIGURATION, IDENTITY, CONNECT, TRUSTLEVEL, TRUSTLEVELRELATION, CRITICAL, ACCESS, ACCESSCONTROL, PACKAGE, IMPORT, EXTENDS, CLEARANCEFOR);
  TokenSet TYPES = TokenSet.create(UNENCRYPTED, ENCRYPTED, IN, OUT, ON, OFF, TYPE, STRONG, WEAK);

}
