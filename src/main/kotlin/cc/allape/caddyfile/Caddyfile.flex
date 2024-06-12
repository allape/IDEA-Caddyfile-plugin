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
    private void _clearStack() {
        _stateStack.clear();
    }
    private boolean _isStackEmpty() {
        return _stateStack.empty();
    }
%}
%{
    private boolean __IN_MATCH_DECLARE_ONE = false;
    private boolean __IN_GROUP_DIRECTIVE_ENCODE_ARGS = false;
%}
%function advance
%type IElementType
%eof{  return;
%eof}

NEW_LINE=[\r\n]
WHITE_SPACE=[ \t]

EMPTY_LINE=({NEW_LINE}|{WHITE_SPACE})+

ONE_LINE_COMMENT="#"[^\r\n]*

MATCHER=[\@\/\*]
FILEPATH=\.{0,2}([\/\\]?[\w.-]+)+
QUOTED_FILEPATH=\"\.{0,2}([\/\\]?[\w.\- ]+)+\"
COMBINED_FILEPATH=({FILEPATH}|{QUOTED_FILEPATH})

COLON=":"
PROTOCOL=(\w+{COLON}\/\/)
HOSTNAME=([\w\-]+\.)*[\w\-]+
PORT=\d+

%state HOSTNAME_MATCHER
%state PORT
%state MATCH

%state BINDING_HOSTNAME

%state QUOTED_STRING
%state VARIABLE
%state VARIABLE_STRING
%state QUOTED_VARIABLE_STRING

%state MATCHER
%state MATCHER_ONE
%state MATCHER_TWO
%state MATCHER_THR

%state MATCH_DECLARE
%state MATCH_DECLARE_DIRECTIVE
%state MATCH_DECLARE_DIR_HEADER
%state MATCH_DECLARE_DIR_HEADER_VALUE
%state MATCH_DECLARE_DIR_METHOD
%state MATCH_DECLARE_DIR_STATUS

%state GROUP

%state GROUP_DIRECTIVE_ABORT

%state GROUP_DIRECTIVE_ACME_SERVER

%state GROUP_DIRECTIVE_BASIC_AUTH

%state GROUP_DIRECTIVE_BASIC_AUTH_SERECTS

%state GROUP_DIRECTIVE_BASIC_AUTH_PASSWORD

%state GROUP_DIRECTIVE_BIND

%state GROUP_DIRECTIVE_ENCODE
%state GROUP_DIRECTIVE_ENCODE_ARGS
%state GROUP_DIRECTIVE_ENCODE_ARGS_GZIP
%state GROUP_DIRECTIVE_ENCODE_ARGS_MINIMUM_LENGTH

%state GROUP_DIRECTIVE_ERROR
%state GROUP_DIRECTIVE_ERROR_ARGS
%state GROUP_DIRECTIVE_ERROR_ARGS_MESSAGE

%state GROUP_DIRECTIVE_TLS

%state GROUP_DIRECTIVE_REDIR

%state GROUP_DIRECTIVE_RESPOND

%state GROUP_DIRECTIVE_REVERSE_PROXY


%%

<YYINITIAL> {
    [^\s#]+            { yybegin(BINDING_HOSTNAME); yypushback(yylength()); }
    {EMPTY_LINE}       { return TokenType.WHITE_SPACE; }
}

<HOSTNAME_MATCHER> {
    "*"             { return CaddyfileTypes.STAR; }
    "."             { return CaddyfileTypes.DOT; }
    ":"             { _pushState(PORT); return CaddyfileTypes.COLON; }
    [^\s:*.]+       { return CaddyfileTypes.TEXT; }
    {WHITE_SPACE}+  { _popState(); return TokenType.WHITE_SPACE; }
}
<PORT> {
    \d+             { _popState(); return CaddyfileTypes.PORT; }
}

<QUOTED_STRING> {
    "\""       { if (yycharat(yylength()-2) != '\\') { _popState(); return CaddyfileTypes.QUOTATION; } }
    [^\r\n\"]+ { return CaddyfileTypes.TEXT; }
}

<VARIABLE> {
    "{"        { return CaddyfileTypes.LEFT_CURLY_BRACE; }
    "}"        { _popState(); return CaddyfileTypes.RIGHT_CURLY_BRACE; }
    [^\s\{\}]+ { return CaddyfileTypes.VARIABLE_NAME; }
}

<BINDING_HOSTNAME> {
    "{"             { yybegin(GROUP); return CaddyfileTypes.LEFT_CURLY_BRACE; }
    [^\s\{]+        { _pushState(HOSTNAME_MATCHER); yypushback(yylength()); }
}

<VARIABLE_STRING> {
    "{"            { _pushState(VARIABLE); yypushback(yylength()); }
    [^\s\{]+       { return CaddyfileTypes.TEXT; }
    {EMPTY_LINE}   { _popState(); yypushback(yylength()); }
}

<MATCHER> {
    "*"         { _pushState(MATCHER_ONE); yypushback(yylength()); }
    "/"         { _pushState(MATCHER_TWO); yypushback(yylength()); }
    "@"         { _pushState(MATCHER_THR); return CaddyfileTypes.AT; }
    [^\s\*\/@]+ { _popState(); yypushback(yylength()); }
}
<MATCHER_ONE> {
    "*"         { _popState(); return CaddyfileTypes.STAR; }
}
<MATCHER_TWO> {
    "/"         { return CaddyfileTypes.SLASH; }
    "*"         { return CaddyfileTypes.STAR; }
    [^\s\/\*]+  { return CaddyfileTypes.TEXT; }
    [\s]        { _popState(); yypushback(yylength()); }
}
<MATCHER_THR> {
    [^\s]+      { _popState(); return CaddyfileTypes.MATCHER_NAME; }
}

<MATCH_DECLARE> {
    [^\s]+      { _pushState(MATCH_DECLARE_DIRECTIVE); return CaddyfileTypes.MATCH_NAME; }
    {NEW_LINE}  { _popState(); return TokenType.WHITE_SPACE; }
}

<MATCH_DECLARE_DIRECTIVE> {
    "{"        { __IN_MATCH_DECLARE_ONE = true; return CaddyfileTypes.LEFT_CURLY_BRACE; }
    "}"        { __IN_MATCH_DECLARE_ONE = false; _popState(); return CaddyfileTypes.RIGHT_CURLY_BRACE; }
    header     { _pushState(MATCH_DECLARE_DIR_HEADER); return CaddyfileTypes.MATCH_DECLARE_DIR_HEADER; }
    method     { _pushState(MATCH_DECLARE_DIR_METHOD); return CaddyfileTypes.MATCH_DECLARE_DIR_METHOD; }
    path       { _pushState(MATCHER_TWO); return CaddyfileTypes.MATCH_DECLARE_DIR_PATH; }
    status     { _pushState(MATCH_DECLARE_DIR_STATUS); return CaddyfileTypes.MATCH_DECLARE_DIR_STATUS; }
    "|"        { return CaddyfileTypes.MATCH_DECLARE_TWO_SEP; }
    {NEW_LINE} { if (!__IN_MATCH_DECLARE_ONE) { _popState(); yypushback(yylength()); } else { return TokenType.WHITE_SPACE; } }
}

<MATCH_DECLARE_DIR_HEADER> {
    [^\s\|]+       { _pushState(MATCH_DECLARE_DIR_HEADER_VALUE); return CaddyfileTypes.HEADER; }
    {NEW_LINE}|"|" { _popState(); yypushback(yylength()); }
}
<MATCH_DECLARE_DIR_HEADER_VALUE> {
    [^\s\|]+       { _popState(); return CaddyfileTypes.HEADER_VALUE; }
    {NEW_LINE}|"|" { _popState(); yypushback(yylength()); }
}
<MATCH_DECLARE_DIR_METHOD> {
    [^\s\|]+       { return CaddyfileTypes.METHOD; }
    {NEW_LINE}|"|" { _popState(); yypushback(yylength()); }
}
<MATCH_DECLARE_DIR_STATUS> {
    \d+            { return CaddyfileTypes.STATUS_CODE; }
    {NEW_LINE}|"|" { _popState(); yypushback(yylength()); }
}

<GROUP> {
    @                 { yybegin(MATCH_DECLARE); return CaddyfileTypes.MATCH_DECLARE; }
    abort             { yybegin(GROUP_DIRECTIVE_ABORT); return CaddyfileTypes.ABORT; }
    acme_server       { yybegin(GROUP_DIRECTIVE_ACME_SERVER); return CaddyfileTypes.ACME_SERVER; }
    basic_auth        { yybegin(GROUP_DIRECTIVE_BASIC_AUTH); return CaddyfileTypes.BASIC_AUTH; }
    bind              { yybegin(GROUP_DIRECTIVE_BIND); return CaddyfileTypes.BIND; }
    encode            { yybegin(GROUP_DIRECTIVE_ENCODE); return CaddyfileTypes.ENCODE; }
    error             { yybegin(GROUP_DIRECTIVE_ERROR); return CaddyfileTypes.ERROR; }
    tls               { yybegin(GROUP_DIRECTIVE_TLS); return CaddyfileTypes.TLS; }
    redir             { yybegin(GROUP_DIRECTIVE_REDIR); return CaddyfileTypes.REDIR; }
    respond           { yybegin(GROUP_DIRECTIVE_RESPOND); return CaddyfileTypes.RESPOND; }
    reverse_proxy     { yybegin(GROUP_DIRECTIVE_REVERSE_PROXY); return CaddyfileTypes.REVERSE_PROXY; }

    {EMPTY_LINE}       { return TokenType.WHITE_SPACE; }
    "}"                { yybegin(YYINITIAL); return CaddyfileTypes.RIGHT_CURLY_BRACE; }
}

<GROUP_DIRECTIVE_ABORT> {
    {MATCHER}  { _pushState(MATCHER); yypushback(yylength()); }
    {NEW_LINE} { yybegin(GROUP); return TokenType.WHITE_SPACE; }
}

<GROUP_DIRECTIVE_ACME_SERVER> {
    {MATCHER}  { _pushState(MATCHER); yypushback(yylength()); }
    {NEW_LINE} { yybegin(GROUP); return TokenType.WHITE_SPACE; }
}

<GROUP_DIRECTIVE_BASIC_AUTH> {
    {MATCHER}      { _pushState(MATCHER); yypushback(yylength()); }
    "{"            { _pushState(GROUP_DIRECTIVE_BASIC_AUTH_SERECTS); return CaddyfileTypes.LEFT_CURLY_BRACE; }
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

<GROUP_DIRECTIVE_BIND> {
    (\d{1,3}\.){3}\d{1,3}                         { return CaddyfileTypes.IPV4; }
    \[([0-9a-fA-F]{0,4}:){2,7}[0-9a-fA-F]{0,4}\]  { return CaddyfileTypes.IPV6; }
    unix\/[^\s]+                                  { return CaddyfileTypes.UNIX_SOCKET; }
    {NEW_LINE}                                    { yybegin(GROUP); return TokenType.WHITE_SPACE; }
}

<GROUP_DIRECTIVE_ENCODE> {
    {MATCHER}      { _pushState(MATCHER); yypushback(yylength()); }
    (zstd|gzip)    { return CaddyfileTypes.COMPRESSION_METHOD; }
    "{"            { _pushState(GROUP_DIRECTIVE_ENCODE_ARGS); return CaddyfileTypes.LEFT_CURLY_BRACE; }
    "}"            { yybegin(GROUP); return CaddyfileTypes.RIGHT_CURLY_BRACE;}
    {NEW_LINE}     { if (_isStackEmpty()) yybegin(GROUP); return TokenType.WHITE_SPACE; }
}
<GROUP_DIRECTIVE_ENCODE_ARGS> {
    gzip                { _pushState(GROUP_DIRECTIVE_ENCODE_ARGS_GZIP); return CaddyfileTypes.ENCODE_ARG_GZIP;}
    zstd                { return CaddyfileTypes.ENCODE_ARG_ZSTD;}
    minimum_length      { _pushState(GROUP_DIRECTIVE_ENCODE_ARGS_MINIMUM_LENGTH); return CaddyfileTypes.ENCODE_ARG_MINIMUM_LENGTH;}
    match               { _pushState(MATCH_DECLARE_DIRECTIVE); return CaddyfileTypes.MATCH_DIRECTIVE; }
    "}"                 { _popState(); yypushback(yylength()); }
    {NEW_LINE}          { return TokenType.WHITE_SPACE; }
}
<GROUP_DIRECTIVE_ENCODE_ARGS_GZIP> {
    \d+        { _popState(); return CaddyfileTypes.GZIP_LEVEL; }
    {NEW_LINE} { _popState(); return TokenType.WHITE_SPACE; }
}
<GROUP_DIRECTIVE_ENCODE_ARGS_MINIMUM_LENGTH> {
    \d+        { _popState(); return CaddyfileTypes.MINIMUM_LENGTH; }
    {NEW_LINE} { _popState(); return TokenType.WHITE_SPACE; }
}

<GROUP_DIRECTIVE_ERROR> {
    {MATCHER}      { _pushState(MATCHER); yypushback(yylength()); }
    \"             { _pushState(QUOTED_STRING); return CaddyfileTypes.QUOTATION; }
    \d+            { return CaddyfileTypes.STATUS_CODE; }
    "{"            { _pushState(GROUP_DIRECTIVE_ERROR_ARGS); return CaddyfileTypes.LEFT_CURLY_BRACE; }
    "}"            { yybegin(GROUP); return CaddyfileTypes.RIGHT_CURLY_BRACE;}
    {NEW_LINE}     { if (_isStackEmpty()) yybegin(GROUP); return TokenType.WHITE_SPACE; }
}
<GROUP_DIRECTIVE_ERROR_ARGS> {
    "message"      { _pushState(GROUP_DIRECTIVE_ERROR_ARGS_MESSAGE); return CaddyfileTypes.ERROR_ARG_MESSAGE; }
    "}"            { _popState(); yypushback(yylength()); }
    {NEW_LINE}     { return TokenType.WHITE_SPACE; }
}
<GROUP_DIRECTIVE_ERROR_ARGS_MESSAGE> {
    "\""           { _pushState(QUOTED_STRING); return CaddyfileTypes.QUOTATION; }
    [^\s\"]+       { _popState(); return CaddyfileTypes.TEXT; }
    {NEW_LINE}     { _popState(); yypushback(yylength()); }
}

<GROUP_DIRECTIVE_TLS> {
    {COMBINED_FILEPATH} { return CaddyfileTypes.FILEPATH; }
    {NEW_LINE}          { yybegin(GROUP); return TokenType.WHITE_SPACE; }
}

<GROUP_DIRECTIVE_REDIR> {
    {MATCHER}        { _pushState(MATCHER); yypushback(yylength()); }
    {PROTOCOL}       { return CaddyfileTypes.PROTOCOL; }
    [^\s]            { _pushState(VARIABLE_STRING); yypushback(yylength()); }
    {NEW_LINE}       { yybegin(GROUP); return TokenType.WHITE_SPACE; }
}

<GROUP_DIRECTIVE_RESPOND> {
    {MATCHER}       { _pushState(MATCHER); yypushback(yylength()); }
    \d+             { return CaddyfileTypes.STATUS_CODE; }
    {NEW_LINE}      { yybegin(GROUP); return TokenType.WHITE_SPACE; }
}

<GROUP_DIRECTIVE_REVERSE_PROXY> {
    {MATCHER}        { _pushState(MATCHER); yypushback(yylength()); }
    {PORT}           { return CaddyfileTypes.PORT; }
    {PROTOCOL}       { return CaddyfileTypes.PROTOCOL; }
    {HOSTNAME}       { return CaddyfileTypes.HOSTNAME; }
    {COLON}          { return CaddyfileTypes.COLON; }
    {NEW_LINE}       { yybegin(GROUP); return TokenType.WHITE_SPACE; }
}


{WHITE_SPACE}+     { return TokenType.WHITE_SPACE; }
{ONE_LINE_COMMENT} { return CaddyfileTypes.COMMENT; }
[^]                { return TokenType.BAD_CHARACTER; }
