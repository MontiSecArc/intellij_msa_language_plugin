package de.monticore.lang.montisecarc;

import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static de.monticore.lang.montisecarc.psi.MSACompositeElementTypes.*;

%%

%{
  public MSALexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class MSALexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s

WHITE_SPACE=[ \t\n\x0B\f\r]+
SINGLE_LINE_COMMENT=("//"|#)[^\r\n]*
MULTI_LINE_COMMENT="/"\*([^*]|[\r\n]|(\*+([^*/]|[\r\n])))*(\*+"/")
NUMBER=[0-9]+(\.[0-9]*)?
ID=[:letter:][a-zA-Z_0-9]*
STRING=('([^'\\]|\\.)*'|\"([^\"\\]|\\.)*\")

%%
<YYINITIAL> {
  {WHITE_SPACE}              { return com.intellij.psi.TokenType.WHITE_SPACE; }

  "{"                        { return LBRACE; }
  "}"                        { return RBRACE; }
  "["                        { return LBRACK; }
  "]"                        { return RBRACK; }
  "("                        { return LPAREN; }
  ")"                        { return RPAREN; }
  ":"                        { return COLON; }
  ";"                        { return SEMICOLON; }
  ","                        { return COMMA; }
  "<NL>"                     { return SEMICOLON_SYNTHETIC; }
  "+"                        { return PLUS; }
  "-"                        { return MINUS; }
  "="                        { return EQUAL; }
  "<"                        { return LESS; }
  ">"                        { return GREATER; }
  "in"                       { return IN; }
  "out"                      { return OUT; }
  "->"                       { return ARROW; }
  "critical"                 { return CRITICAL; }
  "encrypted"                { return ENCRYPTED; }
  "unencrypted"              { return UNENCRYPTED; }
  "autoconnect"              { return AUTOCONNECT; }
  "type"                     { return TYPE; }
  "off"                      { return OFF; }
  "on"                       { return ON; }
  "access"                   { return ACCESS; }
  "accesscontrol"            { return ACCESSCONTROL; }
  "identity"                 { return IDENTITY; }
  "strong"                   { return STRONG; }
  "weak"                     { return WEAK; }
  "cpe"                      { return CPE; }
  "configuration"            { return CONFIGURATION; }
  "."                        { return DOT; }
  "*"                        { return ASTERIX; }
  "package"                  { return PACKAGE; }
  "import"                   { return IMPORT; }
  "component"                { return COMPONENT; }
  "extends"                  { return EXTENDS; }
  "port"                     { return PORT; }
  "WEAK_AUTH"                { return WEAK_AUTH; }
  "STRONG_AUTH"              { return STRONG_AUTH; }
  "connect"                  { return CONNECT; }
  "trustlevel"               { return TRUSTLEVEL; }
  "trustlevelrelation"       { return TRUSTLEVELRELATION; }

  {WHITE_SPACE}              { return WHITE_SPACE; }
  {SINGLE_LINE_COMMENT}      { return SINGLE_LINE_COMMENT; }
  {MULTI_LINE_COMMENT}       { return MULTI_LINE_COMMENT; }
  {NUMBER}                   { return NUMBER; }
  {ID}                       { return ID; }
  {STRING}                   { return STRING; }

}

[^] { return com.intellij.psi.TokenType.BAD_CHARACTER; }
