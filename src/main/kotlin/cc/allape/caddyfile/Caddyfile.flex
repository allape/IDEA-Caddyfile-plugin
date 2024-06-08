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
%}
%{
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
%state MATCH

%state BINDING_HOSTNAME

%state VARIABLE
%state VARIABLE_STRING
%state QUOTED_VARIABLE_STRING

%state GROUP
%state GROUP_DIRECTIVE_ABORT
%state GROUP_DIRECTIVE_ACME_SERVER
%state GROUP_DIRECTIVE_BASIC_AUTH
%state GROUP_DIRECTIVE_BASIC_AUTH_SERECTS
%state GROUP_DIRECTIVE_BASIC_AUTH_PASSWORD
%state GROUP_DIRECTIVE_BIND
%state GROUP_DIRECTIVE_ENCODE
%state GROUP_DIRECTIVE_TLS
%state GROUP_DIRECTIVE_REDIR
%state GROUP_DIRECTIVE_RESPOND
%state GROUP_DIRECTIVE_REVERSE_PROXY

%state GROUP_DIRECTIVE_ENCODE_ARGS
%state GROUP_DIRECTIVE_ENCODE_ARGS_GZIP
%state GROUP_DIRECTIVE_ENCODE_ARGS_MINIMUM_LENGTH
%state GROUP_DIRECTIVE_ENCODE_ARGS_MATCH
%state GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ONE_LINE
%state GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ONE_LINE_HEADER
%state GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ONE_LINE_HEADER_VALUE
%state GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ONE_LINE_STATUS
%state GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ARGS
%state GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ARGS_STATUS
%state GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ARGS_HEADER
%state GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ARGS_HEADER_VALUE

%%

<YYINITIAL> {
    [^\s#]+            { yybegin(BINDING_HOSTNAME); yypushback(yylength()); }
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

<BINDING_HOSTNAME> {
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
    bind              { yybegin(GROUP_DIRECTIVE_BIND); return CaddyfileTypes.BIND; }
    encode            { yybegin(GROUP_DIRECTIVE_ENCODE); return CaddyfileTypes.ENCODE; }
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
<GROUP_DIRECTIVE_BIND> {
    (\d{1,3}\.){3}\d{1,3}                         { return CaddyfileTypes.IPV4; }
    \[([0-9a-fA-F]{0,4}:){2,7}[0-9a-fA-F]{0,4}\]  { return CaddyfileTypes.IPV6; }
    unix\/[^\s]+                                  { return CaddyfileTypes.UNIX_SOCKET; }
    {NEW_LINE}                                    { yybegin(GROUP); return TokenType.WHITE_SPACE; }
}
<GROUP_DIRECTIVE_ENCODE> {
    (zstd|gzip)                                   { return CaddyfileTypes.COMPRESSION_METHOD; }
    "{"                                           { __IN_GROUP_DIRECTIVE_ENCODE_ARGS = true; _pushState(GROUP_DIRECTIVE_ENCODE_ARGS); return CaddyfileTypes.LEFT_CURLY_BRACE; }
    "}"                                           { __IN_GROUP_DIRECTIVE_ENCODE_ARGS = false; yybegin(GROUP); return CaddyfileTypes.RIGHT_CURLY_BRACE;}
    {NEW_LINE}                                    { if (!__IN_GROUP_DIRECTIVE_ENCODE_ARGS) yybegin(GROUP); return TokenType.WHITE_SPACE; }
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


<GROUP_DIRECTIVE_ENCODE_ARGS> {
    gzip                { _pushState(GROUP_DIRECTIVE_ENCODE_ARGS_GZIP); return CaddyfileTypes.ENCODE_ARG_GZIP;}
    zstd                { return CaddyfileTypes.ENCODE_ARG_ZSTD;}
    minimum_length      { _pushState(GROUP_DIRECTIVE_ENCODE_ARGS_MINIMUM_LENGTH); return CaddyfileTypes.ENCODE_ARG_MINIMUM_LENGTH;}
    match               { _pushState(GROUP_DIRECTIVE_ENCODE_ARGS_MATCH); return CaddyfileTypes.ENCODE_ARG_MATCH;}
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
<GROUP_DIRECTIVE_ENCODE_ARGS_MATCH> {
    "{"        { _pushState(GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ARGS); return CaddyfileTypes.LEFT_CURLY_BRACE; }
    [^\s\{]+   { _pushState(GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ONE_LINE); yypushback(yylength()); }
    {NEW_LINE} { _popState(); yypushback(yylength()); }
}
<GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ONE_LINE> {
    "|"        { return CaddyfileTypes.TEXT; }
    "header"   { _pushState(GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ONE_LINE_HEADER); return CaddyfileTypes.TEXT; }
    "status"   { _pushState(GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ONE_LINE_STATUS); return CaddyfileTypes.TEXT; }
    {NEW_LINE} { _popState(); yypushback(yylength()); }
}
<GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ONE_LINE_HEADER> {
    [^\s\|]+   { _pushState(GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ONE_LINE_HEADER_VALUE); return CaddyfileTypes.HEADER; }
    "|"|{NEW_LINE} { _popState(); yypushback(yylength()); }
}
<GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ONE_LINE_STATUS> {
    \d+        { return CaddyfileTypes.STATUS_CODE; }
    {NEW_LINE} { _popState(); yypushback(yylength()); }
}
<GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ONE_LINE_HEADER_VALUE> {
    [^\s]+     { _popState(); return CaddyfileTypes.HEADER_VALUE; }
    "|"|{NEW_LINE} { _popState(); yypushback(yylength()); }
}
<GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ARGS> {
    status     { _pushState(GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ARGS_STATUS); return CaddyfileTypes.ENCODE_ARG_MATCH_ARG_STATUS; }
    header     { _pushState(GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ARGS_HEADER); return CaddyfileTypes.ENCODE_ARG_MATCH_ARG_HEADER; }
    "}"        { _popState(); return CaddyfileTypes.RIGHT_CURLY_BRACE; }
    {NEW_LINE} { return TokenType.WHITE_SPACE; }
}
<GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ARGS_STATUS> {
    \d+        { return CaddyfileTypes.STATUS_CODE; }
    {NEW_LINE} { _popState(); yypushback(yylength()); }
}
<GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ARGS_HEADER> {
    [^\s]+     { _pushState(GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ARGS_HEADER_VALUE); return CaddyfileTypes.HEADER; }
    {NEW_LINE} { _popState(); yypushback(yylength()); }
}
<GROUP_DIRECTIVE_ENCODE_ARGS_MATCH_ARGS_HEADER_VALUE> {
    [^\s]+     { _popState(); return CaddyfileTypes.HEADER_VALUE; }
    {NEW_LINE} { _popState(); yypushback(yylength()); }
}

{WHITE_SPACE}+     { return TokenType.WHITE_SPACE; }
{ONE_LINE_COMMENT} { return CaddyfileTypes.COMMENT; }
[^]                { return TokenType.BAD_CHARACTER; }
