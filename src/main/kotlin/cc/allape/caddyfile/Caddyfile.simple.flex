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
//%{
//    private boolean _globalVariable = false;
//%}
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
%state GLOBAL_VARIABLE

%state DIRECTIVE
%state MATCHER_DECLARATION
%state ARG

%%

<MATCHER> {
    "*"         { yybegin(MATCHER_ONE); yypushback(yylength()); }
    "/"         { yybegin(MATCHER_TWO); yypushback(yylength()); }
    "@"         { yybegin(MATCHER_THR); yypushback(yylength()); }
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
    [^\s]+      { yybegin(ARG); return CaddyfileTypes.AT_MATCHER_NAME; }
}

<VARIABLE> {
    "{"        { return CaddyfileTypes.LCB; }
    "}"        { yybegin(ARG); return CaddyfileTypes.RCB; }
    [^\s{}]+   { return CaddyfileTypes.VARIABLE_NAME; }
}
<GLOBAL_VARIABLE> {
    "{"        { return CaddyfileTypes.LCB; }
    "}"        { yybegin(YYINITIAL); return CaddyfileTypes.RCB; }
    [^\s{}]+   { return CaddyfileTypes.GLOBAL_VARIABLE_NAME; }
}

<YYINITIAL> {
    [^\s{}#]+   { yybegin(DIRECTIVE); yypushback(yylength()); }
    \{[^\s]     { yybegin(GLOBAL_VARIABLE); yypushback(yylength()); }
    \{\s        { yybegin(YYINITIAL); yypushback(yylength() - 1); return CaddyfileTypes.LCB; }
    "}"         { yybegin(YYINITIAL); return CaddyfileTypes.RCB; }
    {COMMENT}   { return CaddyfileTypes.COMMENT; }
    {CRLF}      { return TokenType.WHITE_SPACE; }
}

<DIRECTIVE> {
    // region these directives have matcher
    "abort"           { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "acme_server"     { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "basic_auth"      { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "encode"          { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "error"           { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "file_server"     { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "forward_auth"    { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "fs"              { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "handle"          { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "handle_path"     { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "header"          { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "invoke"          { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "log_append"      { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "log_skip"        { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "map"             { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "method"          { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "metrics"         { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "php_fastcgi"     { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "push"            { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "redir"           { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "request_body"    { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "request_header"  { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "respond"         { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "reverse_proxy"   { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "rewrite"         { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "root"            { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "route"           { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "templates"       { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    // `uri` can be ambiguous, detail see https://caddyserver.com/docs/caddyfile/directives/uri
    "uri"             { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    "vars"            { yybegin(MATCHER); return CaddyfileTypes.DIRECTIVE; }
    // endregion
    "@"         { yybegin(MATCHER_DECLARATION); yypushback(yylength()); }
    [^\s\@\{]+  { yybegin(ARG); return CaddyfileTypes.DIRECTIVE; }
    {CRLF}      { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
}

<MATCHER_DECLARATION> {
    [^\s]+        { yybegin(ARG); return CaddyfileTypes.AT_MATCHER_NAME; }
}

<ARG> {
    [^\s{}]+    { return CaddyfileTypes.ARG; }
    \{[^\s]     { yybegin(VARIABLE); yypushback(yylength()); }
    \{\s        { yybegin(YYINITIAL); yypushback(yylength()-1); return CaddyfileTypes.LCB; }
    {CRLF}      { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
}

{WHITE_SPACE}+  { return TokenType.WHITE_SPACE; }
[^]             { yybegin(YYINITIAL); return TokenType.BAD_CHARACTER; }
