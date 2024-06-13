package cc.allape.caddyfile.language;

// DO NOT OPTIMIZE IMPORT
import cc.allape.caddyfile.language.psi.CaddyfileTypes;
import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
// DO NOT OPTIMIZE IMPORT

%%

%public %class CaddyfileLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

CRLF=[\r\n]
WHITE_SPACE=[ \t]
COMMENT="#"[^\r\n]*

%state MATCHER
%state MATCHER_ONE
%state MATCHER_TWO
%state MATCHER_THR

%state VARIABLE

%state DIRECTIVE
%state ARG

%%

<MATCHER> {
    "*"         { yybegin(MATCHER_ONE); yypushback(yylength()); }
    "/"         { yybegin(MATCHER_TWO); yypushback(yylength()); }
    "@"         { yybegin(MATCHER_THR); return CaddyfileTypes.AT; }
    {CRLF}      { yybegin(ARG); yypushback(yylength()); }
    [^\s\*\/\@] { yybegin(ARG); yypushback(yylength()); }
}
<MATCHER_ONE> {
    "*"         { yybegin(ARG); return CaddyfileTypes.STAR; }
}
<MATCHER_TWO> {
    "/"           { return CaddyfileTypes.SLASH; }
    "*"           { return CaddyfileTypes.STAR; }
    \?[^\s]+      { return CaddyfileTypes.TEXT; }
    [^\s\/\*\?]+  { return CaddyfileTypes.TEXT; }
    [\s]          { yybegin(ARG); yypushback(yylength()); }
}
<MATCHER_THR> {
    [^\s]+      { yybegin(ARG); return CaddyfileTypes.MATCHER_NAME; }
}

<VARIABLE> {
    "{"        { return CaddyfileTypes.LCB; }
    "}"        { yybegin(ARG); return CaddyfileTypes.RCB; }
    [^\s\{\}]+ { return CaddyfileTypes.VARIABLE_NAME; }
}

<YYINITIAL> {
    [^\s#}]+    { yybegin(DIRECTIVE); yypushback(yylength()); }
    "}"         { yybegin(YYINITIAL); return CaddyfileTypes.RCB; }
    {COMMENT}   { return CaddyfileTypes.COMMENT; }
    {CRLF}      { return TokenType.WHITE_SPACE; }
}

<DIRECTIVE> {
    [^\s]+      { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    {CRLF}      { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
}

<ARG> {
    [^\s\{\}]+  { return CaddyfileTypes.ARG; }
    \{[^\s]+    { yybegin(VARIABLE); yypushback(yylength()); }
    \{[\s]+     { yybegin(YYINITIAL); yypushback(yylength()-1); return CaddyfileTypes.LCB; }
    {CRLF}      { yybegin(YYINITIAL); yypushback(yylength()); }
}

{WHITE_SPACE}+  { return TokenType.WHITE_SPACE; }
[^]             { yybegin(YYINITIAL); return TokenType.BAD_CHARACTER; }
