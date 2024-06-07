package cc.allape.caddyfile

import cc.allape.caddyfile.language.CaddyfileLexer
import com.intellij.lexer.FlexAdapter


class CaddyfileLexerAdapter : FlexAdapter(CaddyfileLexer(null))