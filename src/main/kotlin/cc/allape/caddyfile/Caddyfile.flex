package cc.allape.caddyfile.language;

// DO NOT OPTIMIZE IMPORT
import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import cc.allape.caddyfile.language.psi.CaddyfileTypes;
import com.intellij.psi.TokenType;
import java.util.ArrayList;
import java.util.Stack;
// DO NOT OPTIMIZE IMPORT

%%

%public %class CaddyfileLexer
%implements FlexLexer
%unicode
%{
    private Stack<Integer> _stateStack = new Stack<Integer>();
    private void _pushState(int state) {
        _stateStack.push(zzLexicalState);
        yybegin(state);
    }
    private void _popState() {
        yybegin(_stateStack.pop());
    }
%}
%function advance
%type IElementType
%eof{  return;
%eof}

NEW_LINE=[\r\n]
WHITE_SPACE=[ \t]

EMPTY_LINE=({NEW_LINE}|{WHITE_SPACE})+

ONE_LINE_COMMENT="#"[^\r\n]*

FILEPATH=\.{0,2}([\/\\]?[\w.-]+)+
QUOTED_FILEPATH=\"\.{0,2}([\/\\]?[\w.\- ]+)+\"
COMBINED_FILEPATH=({FILEPATH}|{QUOTED_FILEPATH})

COLON=":"
PROTOCOL=(\w+{COLON}\/\/)
HOSTNAME=([\w\-]+\.)*[\w\-]+
PORT=\d+

%state STARRED_HOSTNAME
%state PORT
%state STARRED_PATH

%state BINDING

%state VARIABLE
%state VARIABLE_STRING
%state QUOTED_VARIABLE_STRING

%state GROUP
%state GROUP_DIRECTIVE_ABORT
%state GROUP_DIRECTIVE_ACME_SERVER
%state GROUP_DIRECTIVE_BASIC_AUTH
%state GROUP_DIRECTIVE_BASIC_AUTH_SERECTS
%state GROUP_DIRECTIVE_BASIC_AUTH_PASSWORD
%state GROUP_DIRECTIVE_TLS
%state GROUP_DIRECTIVE_REDIR
%state GROUP_DIRECTIVE_RESPOND
%state GROUP_DIRECTIVE_REVERSE_PROXY

%%

<YYINITIAL> {
    [^\s#]+             { yybegin(BINDING); yypushback(yylength()); }
    {EMPTY_LINE}       { return TokenType.WHITE_SPACE; }
}

<STARRED_HOSTNAME> {
    "*"             { return CaddyfileTypes.STAR; }
    "."             { return CaddyfileTypes.DOT; }
    ":"             { _pushState(PORT); return CaddyfileTypes.COLON; }
    [^\s:*.]+       { return CaddyfileTypes.TEXT; }
    {WHITE_SPACE}+  { _popState(); return TokenType.WHITE_SPACE; }
}
<PORT> {
    \d+             { _popState(); return CaddyfileTypes.PORT; }
}

<STARRED_PATH> {
    "*"             { return CaddyfileTypes.STAR; }
    "/"             { return CaddyfileTypes.SLASH; }
    [^\s\*\/]+      { return CaddyfileTypes.TEXT; }
    {WHITE_SPACE}+  { _popState(); return TokenType.WHITE_SPACE; }
}

<VARIABLE> {
    "{"        { return CaddyfileTypes.LEFT_CURLY_BRACE; }
    "}"        { _popState(); return CaddyfileTypes.RIGHT_CURLY_BRACE; }
    [^\s\{\}]+ { return CaddyfileTypes.VARIABLE_NAME; }
}

<BINDING> {
    "{"             { yybegin(GROUP); return CaddyfileTypes.LEFT_CURLY_BRACE; }
    [^\s\{]+        { _pushState(STARRED_HOSTNAME); yypushback(yylength()); }
}

<VARIABLE_STRING> {
    "{"            { _pushState(VARIABLE); yypushback(yylength()); }
    [^\s\{]+       { return CaddyfileTypes.TEXT; }
    {EMPTY_LINE}   { _popState(); yypushback(yylength()); }
}

<GROUP> {
    abort             { yybegin(GROUP_DIRECTIVE_ABORT); return CaddyfileTypes.ABORT; }
    acme_server       { yybegin(GROUP_DIRECTIVE_ACME_SERVER); return CaddyfileTypes.ACME_SERVER; }
    basic_auth        { yybegin(GROUP_DIRECTIVE_BASIC_AUTH); return CaddyfileTypes.BASIC_AUTH; }
    tls               { yybegin(GROUP_DIRECTIVE_TLS); return CaddyfileTypes.TLS; }
    redir             { yybegin(GROUP_DIRECTIVE_REDIR); return CaddyfileTypes.REDIR; }
    respond           { yybegin(GROUP_DIRECTIVE_RESPOND); return CaddyfileTypes.RESPOND; }
    reverse_proxy     { yybegin(GROUP_DIRECTIVE_REVERSE_PROXY); return CaddyfileTypes.REVERSE_PROXY; }

    {EMPTY_LINE}       { return TokenType.WHITE_SPACE; }
    "}"                { yybegin(YYINITIAL); return CaddyfileTypes.RIGHT_CURLY_BRACE; }
}
<GROUP_DIRECTIVE_ABORT> {
    {NEW_LINE} { yybegin(GROUP); return TokenType.WHITE_SPACE; }
}
<GROUP_DIRECTIVE_ACME_SERVER> {
    {NEW_LINE} { yybegin(GROUP); return TokenType.WHITE_SPACE; }
}
<GROUP_DIRECTIVE_BASIC_AUTH> {
    "{"            { _pushState(GROUP_DIRECTIVE_BASIC_AUTH_SERECTS); return CaddyfileTypes.LEFT_CURLY_BRACE; }
    [^\s\{]+       { _pushState(STARRED_PATH); yypushback(yylength()); }
    {NEW_LINE}     { yybegin(GROUP); return TokenType.WHITE_SPACE; }
}
<GROUP_DIRECTIVE_BASIC_AUTH_SERECTS> {
    "}"              { _popState(); return CaddyfileTypes.RIGHT_CURLY_BRACE; }
    [\w\-\}]+        { _pushState(GROUP_DIRECTIVE_BASIC_AUTH_PASSWORD); return CaddyfileTypes.USERNAME; }
    {NEW_LINE}       { return TokenType.WHITE_SPACE; }
}
<GROUP_DIRECTIVE_BASIC_AUTH_PASSWORD> {
    [^\s\}]+           { _popState(); return CaddyfileTypes.PASSWORD; }
}
<GROUP_DIRECTIVE_TLS> {
    {COMBINED_FILEPATH} { return CaddyfileTypes.FILEPATH; }
    {NEW_LINE}          { yybegin(GROUP); return TokenType.WHITE_SPACE; }
}
<GROUP_DIRECTIVE_REDIR> {
    {PROTOCOL}       { return CaddyfileTypes.PROTOCOL; }
    [^\s]            { _pushState(VARIABLE_STRING); yypushback(yylength()); }
    {NEW_LINE}       { yybegin(GROUP); return TokenType.WHITE_SPACE; }
}
<GROUP_DIRECTIVE_RESPOND> {
    \d+             { return CaddyfileTypes.STATUS_CODE; }
    {NEW_LINE}      { yybegin(GROUP); return TokenType.WHITE_SPACE; }
}
<GROUP_DIRECTIVE_REVERSE_PROXY> {
    {PORT}           { return CaddyfileTypes.PORT; }
    {PROTOCOL}       { return CaddyfileTypes.PROTOCOL; }
    {HOSTNAME}       { return CaddyfileTypes.HOSTNAME; }
    {COLON}          { return CaddyfileTypes.COLON; }
    {NEW_LINE}       { yybegin(GROUP); return TokenType.WHITE_SPACE; }
}

{WHITE_SPACE}+     { return TokenType.WHITE_SPACE; }
{ONE_LINE_COMMENT} { return CaddyfileTypes.COMMENT; }
[^]                { return TokenType.BAD_CHARACTER; }
