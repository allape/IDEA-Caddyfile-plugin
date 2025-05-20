package cc.allape.caddyfile.language;

// DO NOT OPTIMIZE IMPORT
import cc.allape.caddyfile.language.psi.CaddyfileTypes;
import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import java.util.ArrayList;
// DO NOT OPTIMIZE IMPORT

%%

%public %class CaddyfileLexer
%implements FlexLexer
%unicode
%{
    private ArrayList<Integer> _prevStates = new ArrayList<Integer>();

    private void beginWithMemo(int state) {
        _prevStates.add(yystate());
        yybegin(state);
    }

    private void recoverMemoState(int orState) {
        if (_prevStates.isEmpty()) {
            yybegin(orState);
            return;
        }
        yybegin(_prevStates.remove(_prevStates.size() - 1));
    }

    private void recoverMemoState() {
        recoverMemoState(YYINITIAL);
    }
%}
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

%state SNIPPET

%state VARIABLE
%state GLOBAL_VARIABLE

%state TEMPLATE_TEXT
%state QUOTED_TEMPLATE_TEXT

%state DIRECTIVE
%state MATCHER_DECLARATION
%state SNIPPET_DECLARATION
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
    \s+          { yybegin(ARG); yypushback(yylength()); }
}
<MATCHER_THR> {
    [^\s]+      { yybegin(ARG); return CaddyfileTypes.AT_MATCHER_NAME; }
}

<SNIPPET> {
    [^\s]+  { yybegin(ARG); return CaddyfileTypes.SNIPPET_NAME_TEXT; }
}

<VARIABLE> {
    "{"        { return CaddyfileTypes.LCB; }
    "}"        { recoverMemoState(ARG); return CaddyfileTypes.RCB; }
    [^\s{}]+   { return CaddyfileTypes.VARIABLE_NAME; }
}
<GLOBAL_VARIABLE> {
    "{"        { return CaddyfileTypes.LCB; }
    "}"        { yybegin(YYINITIAL); return CaddyfileTypes.RCB; }
    [^\s{}]+   { return CaddyfileTypes.GLOBAL_VARIABLE_NAME; }
}

<TEMPLATE_TEXT> {
    [^\s\"\{]+ { return CaddyfileTypes.TEXT; }
    "{"        { beginWithMemo(VARIABLE); yypushback(yylength()); }
    "\""       { beginWithMemo(QUOTED_TEMPLATE_TEXT); return CaddyfileTypes.QUOTE; }
    \s         { recoverMemoState(); yypushback(yylength()); }
}

<QUOTED_TEMPLATE_TEXT> {
    (\\\"|\\\\|[^\n\"\{]+)+ { return CaddyfileTypes.TEXT; }
    "\""                    { recoverMemoState(); return CaddyfileTypes.QUOTE; }
    "{"                     { beginWithMemo(VARIABLE); yypushback(yylength()); }
    "\n"                    { yybegin(YYINITIAL); return CaddyfileTypes.TEXT; }
}

<YYINITIAL> {
    \{[^\s}]+}\s*\n         { yybegin(GLOBAL_VARIABLE); yypushback(yylength()); }
    \{\s                    { yybegin(YYINITIAL); yypushback(yylength() - 1); return CaddyfileTypes.LCB; }
    ([^\s}#]+|\"[^\s\"]+\") { yybegin(DIRECTIVE); yypushback(yylength()); }
    "}"                     { yybegin(YYINITIAL); return CaddyfileTypes.RCB; }
    {COMMENT}               { return CaddyfileTypes.COMMENT; }
    {CRLF}                  { return TokenType.WHITE_SPACE; }
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
    "import"          { yybegin(SNIPPET); return CaddyfileTypes.DIRECTIVE; }
    "@"               { yybegin(MATCHER_DECLARATION); yypushback(yylength()); }
    "("               { yybegin(SNIPPET_DECLARATION); yypushback(yylength()); }
    "\""              { yybegin(ARG); beginWithMemo(QUOTED_TEMPLATE_TEXT); return CaddyfileTypes.QUOTE; }
    "{"               { yybegin(ARG); beginWithMemo(TEMPLATE_TEXT); yypushback(yylength()); }
    [^\s\@\{\(]+      { yybegin(ARG); return CaddyfileTypes.DIRECTIVE; }
    {CRLF}            { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
}

<MATCHER_DECLARATION> {
    [^\s]+        { yybegin(ARG); return CaddyfileTypes.AT_MATCHER_NAME; }
}

<SNIPPET_DECLARATION> {
    "("           { return CaddyfileTypes.LB; }
    [^\s\(\)]+    { return CaddyfileTypes.SNIPPET_NAME_TEXT; }
    ")"           { yybegin(YYINITIAL); return CaddyfileTypes.RB; }
}

<ARG> {
    "\""       { beginWithMemo(QUOTED_TEMPLATE_TEXT); return CaddyfileTypes.QUOTE; }
    [^\"\s{]   { beginWithMemo(TEMPLATE_TEXT); yypushback(yylength()); }
    \{[^\s]    { beginWithMemo(TEMPLATE_TEXT); yypushback(yylength()); }
    \{\s       { yybegin(YYINITIAL); yypushback(yylength()-1); return CaddyfileTypes.LCB; }
    #[^\n]*\n  { yybegin(YYINITIAL); return CaddyfileTypes.COMMENT; }
    {CRLF}     { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
}

{WHITE_SPACE}+ { return TokenType.WHITE_SPACE; }
[^]            { yybegin(YYINITIAL); return TokenType.BAD_CHARACTER; }
