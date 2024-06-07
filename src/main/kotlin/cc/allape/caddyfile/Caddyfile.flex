package cc.allape.caddyfile.language;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import cc.allape.caddyfile.language.psi.CaddyfileTypes;
import com.intellij.psi.TokenType;

%%

%public %class CaddyfileLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

NEW_LINE=[\r\n]
WHITE_SPACE=[ \t]
LEFT_CURLY_BRACE="{"
RIGHT_CURLY_BRACE="}"

EMPTY_LINE=({NEW_LINE}|{WHITE_SPACE})+

ONE_LINE_COMMENT="#"[^\r\n]*

FILEPATH=\.{0,2}([\/\\]?[\w.-]+)+
QUOTED_FILEPATH=\"\.{0,2}([\/\\]?[\w.\- ]+)+\"
COMBINED_FILEPATH=({FILEPATH}|{QUOTED_FILEPATH})

COLON=":"
PROTOCOL=(\w+{COLON}\/\/)
HOSTNAME=([\w\-]+\.)*[\w\-]+
PORT=\d+

%state BINDING
%state GROUP
%state GROUP_DIRECTIVE_TLS
%state GROUP_DIRECTIVE_REDIR
%state GROUP_DIRECTIVE_RESPOND
%state GROUP_DIRECTIVE_REVERSE_PROXY

%%

<YYINITIAL> {
    {ONE_LINE_COMMENT} { yybegin(YYINITIAL); return CaddyfileTypes.COMMENT; }
    [^\s]+ { yybegin(BINDING); return CaddyfileTypes.BINDING_HOSTNAME; }
}

<BINDING> {
    {COLON} { yybegin(BINDING); return CaddyfileTypes.COLON; }
    {PORT} { yybegin(BINDING); return CaddyfileTypes.PORT; }
    {WHITE_SPACE}+ { yybegin(BINDING); return TokenType.WHITE_SPACE; }
    {LEFT_CURLY_BRACE} { yybegin(GROUP); return CaddyfileTypes.LEFT_CURLY_BRACE; }
}

<GROUP> {
    tls { yybegin(GROUP_DIRECTIVE_TLS); return CaddyfileTypes.TLS; }
    redir { yybegin(GROUP_DIRECTIVE_REDIR); return CaddyfileTypes.REDIR; }
    respond { yybegin(GROUP_DIRECTIVE_RESPOND); return CaddyfileTypes.RESPOND; }
    reverse_proxy { yybegin(GROUP_DIRECTIVE_REVERSE_PROXY); return CaddyfileTypes.REVERSE_PROXY; }

    {ONE_LINE_COMMENT} { yybegin(GROUP); return CaddyfileTypes.COMMENT; }
    {EMPTY_LINE} { yybegin(GROUP); return TokenType.WHITE_SPACE; }
    {RIGHT_CURLY_BRACE} { yybegin(YYINITIAL); return CaddyfileTypes.RIGHT_CURLY_BRACE; }
}
<GROUP_DIRECTIVE_TLS> {
    {COMBINED_FILEPATH} { yybegin(GROUP_DIRECTIVE_TLS); return CaddyfileTypes.FILEPATH; }
    {WHITE_SPACE}+ { yybegin(GROUP_DIRECTIVE_TLS); return TokenType.WHITE_SPACE; }
    {NEW_LINE} { yybegin(GROUP); return TokenType.WHITE_SPACE; }
}
<GROUP_DIRECTIVE_REDIR> {
    \{(host|port)\} { yybegin(GROUP_DIRECTIVE_REDIR); return CaddyfileTypes.VARIABLE; }
    {PORT} { yybegin(GROUP_DIRECTIVE_REDIR); return CaddyfileTypes.PORT; }
    {PROTOCOL} { yybegin(GROUP_DIRECTIVE_REDIR); return CaddyfileTypes.PROTOCOL; }
    {HOSTNAME} { yybegin(GROUP_DIRECTIVE_REDIR); return CaddyfileTypes.HOSTNAME; }
    {COLON} { yybegin(GROUP_DIRECTIVE_REDIR); return CaddyfileTypes.COLON; }
    {WHITE_SPACE}+ { yybegin(GROUP_DIRECTIVE_REDIR); return TokenType.WHITE_SPACE; }
    {NEW_LINE} { yybegin(GROUP); return TokenType.WHITE_SPACE; }
}
<GROUP_DIRECTIVE_RESPOND> {
    \d+ { yybegin(GROUP_DIRECTIVE_RESPOND); return CaddyfileTypes.STATUS_CODE; }
    {WHITE_SPACE}+ { yybegin(GROUP_DIRECTIVE_RESPOND); return TokenType.WHITE_SPACE; }
    {NEW_LINE} { yybegin(GROUP); return TokenType.WHITE_SPACE; }
}
<GROUP_DIRECTIVE_REVERSE_PROXY> {
    {PORT} { yybegin(GROUP_DIRECTIVE_REVERSE_PROXY); return CaddyfileTypes.PORT; }
    {PROTOCOL} { yybegin(GROUP_DIRECTIVE_REVERSE_PROXY); return CaddyfileTypes.PROTOCOL; }
    {HOSTNAME} { yybegin(GROUP_DIRECTIVE_REVERSE_PROXY); return CaddyfileTypes.HOSTNAME; }
    {COLON} { yybegin(GROUP_DIRECTIVE_REVERSE_PROXY); return CaddyfileTypes.COLON; }
    {WHITE_SPACE}+ { yybegin(GROUP_DIRECTIVE_REVERSE_PROXY); return TokenType.WHITE_SPACE; }
    {NEW_LINE} { yybegin(GROUP); return TokenType.WHITE_SPACE; }
}

<YYINITIAL> {EMPTY_LINE} { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
[^] { return TokenType.BAD_CHARACTER; }