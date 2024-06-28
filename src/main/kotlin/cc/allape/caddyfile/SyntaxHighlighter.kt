package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import javax.swing.Icon


class CaddyfileSyntaxHighlighter : SyntaxHighlighterBase() {
    @NotNull
    override fun getHighlightingLexer(): Lexer {
        return CaddyfileLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType): @NotNull Array<TextAttributesKey?> {
        when (tokenType) {
            CaddyfileTypes.VARIABLE_NAME,
            CaddyfileTypes.DIRECTIVE
            -> {
                return KEYWORD_KEYS
            }
            CaddyfileTypes.AT_MATCHER_NAME -> {
                return LABEL_KEYS
            }
            CaddyfileTypes.SLASH -> {
                return SEMICOLON_KEYS
            }
            CaddyfileTypes.STAR -> {
                return NUMBER_KEYS
            }
            CaddyfileTypes.LCB,
            CaddyfileTypes.RCB -> {
                return BRACKETS_KEYS
            }
            CaddyfileTypes.TEXT -> {
                return STRING_KEYS
            }
            CaddyfileTypes.COMMENT -> {
                return COMMENT_KEYS
            }
            TokenType.BAD_CHARACTER -> {
                return BAD_CHAR_KEYS
            }
            else -> return EMPTY_KEYS
        }
    }

//    override fun getTokenHighlights(tokenType: IElementType): @NotNull Array<TextAttributesKey?> {
//        when (tokenType) {
//            // important keywords
//            CaddyfileTypes.VARIABLE_NAME,
//            CaddyfileTypes.STAR,
//            // directives
//            CaddyfileTypes.ABORT,
//            CaddyfileTypes.ACME_SERVER,
//            CaddyfileTypes.BASIC_AUTH,
//            CaddyfileTypes.BIND,
//            CaddyfileTypes.ENCODE,
//            CaddyfileTypes.ERROR,
//            CaddyfileTypes.FILE_SERVER,
//            CaddyfileTypes.FORWARD_AUTH,
//            CaddyfileTypes.TLS,
//            CaddyfileTypes.REDIR,
//            CaddyfileTypes.RESPOND,
//            CaddyfileTypes.REVERSE_PROXY,
//            // sub directive
//            CaddyfileTypes.FORWARD_AUTH_ARG_URI,
//            CaddyfileTypes.FORWARD_AUTH_ARG_COPY_HEADERS,
//            CaddyfileTypes.FORWARD_AUTH_ARG_HEADER_UP,
//            CaddyfileTypes.FILE_SERVER_ARG_FS,
//            CaddyfileTypes.FILE_SERVER_ARG_ROOT,
//            CaddyfileTypes.FILE_SERVER_ARG_HIDE,
//            CaddyfileTypes.FILE_SERVER_ARG_INDEX,
//            CaddyfileTypes.FILE_SERVER_ARG_BROWSE,
//            CaddyfileTypes.FILE_SERVER_ARG_BROWSE_ARG_REVEAL_SYMLINKS,
//            CaddyfileTypes.FILE_SERVER_ARG_PRECOMPRESSED,
//            CaddyfileTypes.FILE_SERVER_ARG_STATUS,
//            CaddyfileTypes.FILE_SERVER_ARG_DISABLE_CANONICAL_URIS,
//            CaddyfileTypes.FILE_SERVER_ARG_PASS_THRU,
//            CaddyfileTypes.ERROR_ARG_MESSAGE,
//            CaddyfileTypes.MATCH_DIRECTIVE,
//            CaddyfileTypes.ENCODE_ARG_GZIP,
//            CaddyfileTypes.ENCODE_ARG_MINIMUM_LENGTH,
//            CaddyfileTypes.ENCODE_ARG_ZSTD
//            -> {
//                return KEYWORD_KEYS
//            }
//            CaddyfileTypes.HOSTNAME -> {
//                return LABEL_KEYS
//            }
//            CaddyfileTypes.COPY_TO,
//            CaddyfileTypes.DOT,
//            CaddyfileTypes.COLON -> {
//                return SEMICOLON_KEYS
//            }
//            CaddyfileTypes.STATUS_CODE,
//            CaddyfileTypes.PORT,
//            CaddyfileTypes.PORT_WITH_COLON -> {
//                return NUMBER_KEYS
//            }
//            CaddyfileTypes.RIGHT_CURLY_BRACE,
//            CaddyfileTypes.LEFT_CURLY_BRACE -> {
//                return BRACKETS_KEYS
//            }
//            CaddyfileTypes.HEADER,
//            CaddyfileTypes.HEADER_VALUE,
//            CaddyfileTypes.USERNAME,
//            CaddyfileTypes.PASSWORD,
//            CaddyfileTypes.MATCH_DECLARE_DIR_HEADER,
//            CaddyfileTypes.MATCH_DECLARE_DIR_METHOD,
//            CaddyfileTypes.MATCH_DECLARE_DIR_PATH,
//            CaddyfileTypes.MATCH_DECLARE_DIR_STATUS,
//            CaddyfileTypes.TEXT,
//            CaddyfileTypes.PROTOCOL -> {
//                return STRING_KEYS
//            }
//            CaddyfileTypes.COMMENT -> {
//                return COMMENT_KEYS
//            }
//            TokenType.BAD_CHARACTER -> {
//                return BAD_CHAR_KEYS
//            }
//            else -> return EMPTY_KEYS
//        }
//    }

    companion object {
        val SEMICOLON: TextAttributesKey =
            createTextAttributesKey("CADDYFILE_SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON)
        val LABEL: TextAttributesKey =
            createTextAttributesKey("CADDYFILE_LABEL", DefaultLanguageHighlighterColors.LABEL)
        val KEYWORD: TextAttributesKey =
            createTextAttributesKey("CADDYFILE_HOSTNAME", DefaultLanguageHighlighterColors.KEYWORD)
        val BRACKETS: TextAttributesKey =
            createTextAttributesKey("CADDYFILE_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS)
        val NUMBER: TextAttributesKey =
            createTextAttributesKey("CADDYFILE_PORT", DefaultLanguageHighlighterColors.NUMBER)
        val STRING: TextAttributesKey =
            createTextAttributesKey("CADDYFILE_PROTOCOL", DefaultLanguageHighlighterColors.STRING)
        val LINE_COMMENT: TextAttributesKey =
            createTextAttributesKey("CADDYFILE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val BAD_CHARACTER: TextAttributesKey =
            createTextAttributesKey("CADDYFILE_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)


        private val BAD_CHAR_KEYS = arrayOf<TextAttributesKey?>(BAD_CHARACTER)
        private val SEMICOLON_KEYS = arrayOf<TextAttributesKey?>(SEMICOLON)
        private val BRACKETS_KEYS = arrayOf<TextAttributesKey?>(BRACKETS)
        private val LABEL_KEYS = arrayOf<TextAttributesKey?>(LABEL)
        private val KEYWORD_KEYS = arrayOf<TextAttributesKey?>(KEYWORD)
        private val NUMBER_KEYS = arrayOf<TextAttributesKey?>(NUMBER)
        private val STRING_KEYS = arrayOf<TextAttributesKey?>(STRING)
        private val COMMENT_KEYS = arrayOf<TextAttributesKey?>(LINE_COMMENT)
        private val EMPTY_KEYS = arrayOfNulls<TextAttributesKey>(0)
    }
}

internal class CaddyfileSyntaxHighlighterFactory : SyntaxHighlighterFactory() {
    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): SyntaxHighlighter {
        return CaddyfileSyntaxHighlighter()
    }
}

@Suppress("CompanionObjectInExtension")
internal class CaddyfileColorSettingsPage : ColorSettingsPage {
    override fun getIcon(): Icon {
        return CaddyfileIcons.FILE
    }

    override fun getHighlighter(): SyntaxHighlighter {
        return CaddyfileSyntaxHighlighter()
    }

    override fun getDemoText(): String {
        return """
        :80 {
            redir https://{host}{uri}
        }
        
        # https
        localhost:443 {
            tls /etc/ssl/certs/localhost.crt /etc/ssl/private/localhost.key
            reverse_proxy http://localhost:80
        }

        :443 {
            respond 404
        }
        """.trimIndent()
    }

    @Nullable
    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>? {
        return null
    }

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> {
        return DESCRIPTORS
    }

    override fun getColorDescriptors(): Array<ColorDescriptor> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    override fun getDisplayName(): String {
        return "Caddyfile"
    }

    companion object {
        private val DESCRIPTORS = arrayOf(
            AttributesDescriptor("Brackets", CaddyfileSyntaxHighlighter.BRACKETS),
            AttributesDescriptor("Comment", CaddyfileSyntaxHighlighter.LINE_COMMENT),
            AttributesDescriptor("String", CaddyfileSyntaxHighlighter.STRING),
            AttributesDescriptor("Keyword", CaddyfileSyntaxHighlighter.KEYWORD),
            AttributesDescriptor("Number", CaddyfileSyntaxHighlighter.NUMBER),
            AttributesDescriptor("Label", CaddyfileSyntaxHighlighter.LABEL),
            AttributesDescriptor("Separator", CaddyfileSyntaxHighlighter.SEMICOLON),
            AttributesDescriptor("Bad value", CaddyfileSyntaxHighlighter.BAD_CHARACTER)
        )
    }
}