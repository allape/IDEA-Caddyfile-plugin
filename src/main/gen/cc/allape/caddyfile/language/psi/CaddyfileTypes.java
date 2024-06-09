// This is a generated file. Not intended for manual editing.
package cc.allape.caddyfile.language.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import cc.allape.caddyfile.CaddyfileElementType;
import cc.allape.caddyfile.CaddyfileTokenType;
import cc.allape.caddyfile.language.psi.impl.*;

public interface CaddyfileTypes {

  IElementType ABORT = new CaddyfileElementType("ABORT");
  IElementType ACME_SERVER = new CaddyfileElementType("ACME_SERVER");
  IElementType BASIC_AUTH = new CaddyfileElementType("BASIC_AUTH");
  IElementType BIND = new CaddyfileElementType("BIND");
  IElementType BINDING = new CaddyfileElementType("BINDING");
  IElementType COLON = new CaddyfileElementType("COLON");
  IElementType DIRECTIVE = new CaddyfileElementType("DIRECTIVE");
  IElementType ENCODE = new CaddyfileElementType("ENCODE");
  IElementType ENCODE_ARG = new CaddyfileElementType("ENCODE_ARG");
  IElementType ENCODE_ARG_GZIP = new CaddyfileElementType("ENCODE_ARG_GZIP");
  IElementType ENCODE_ARG_MINIMUM_LENGTH = new CaddyfileElementType("ENCODE_ARG_MINIMUM_LENGTH");
  IElementType ENCODE_ARG_ZSTD = new CaddyfileElementType("ENCODE_ARG_ZSTD");
  IElementType GROUP = new CaddyfileElementType("GROUP");
  IElementType HOST = new CaddyfileElementType("HOST");
  IElementType HOSTNAME_MATCHER = new CaddyfileElementType("HOSTNAME_MATCHER");
  IElementType MATCHER = new CaddyfileElementType("MATCHER");
  IElementType MATCHER_ONE = new CaddyfileElementType("MATCHER_ONE");
  IElementType MATCHER_THR = new CaddyfileElementType("MATCHER_THR");
  IElementType MATCHER_TWO = new CaddyfileElementType("MATCHER_TWO");
  IElementType MATCH_BODY = new CaddyfileElementType("MATCH_BODY");
  IElementType MATCH_DECLARE = new CaddyfileElementType("MATCH_DECLARE");
  IElementType MATCH_DECLARE_DIRECTIVE = new CaddyfileElementType("MATCH_DECLARE_DIRECTIVE");
  IElementType MATCH_DECLARE_DIR_HEADER = new CaddyfileElementType("MATCH_DECLARE_DIR_HEADER");
  IElementType MATCH_DECLARE_DIR_METHOD = new CaddyfileElementType("MATCH_DECLARE_DIR_METHOD");
  IElementType MATCH_DECLARE_DIR_PATH = new CaddyfileElementType("MATCH_DECLARE_DIR_PATH");
  IElementType MATCH_DECLARE_DIR_STATUS = new CaddyfileElementType("MATCH_DECLARE_DIR_STATUS");
  IElementType MATCH_DECLARE_NOT = new CaddyfileElementType("MATCH_DECLARE_NOT");
  IElementType MATCH_DECLARE_ONE = new CaddyfileElementType("MATCH_DECLARE_ONE");
  IElementType MATCH_DECLARE_TWO = new CaddyfileElementType("MATCH_DECLARE_TWO");
  IElementType MATCH_DECLARE_TWO_DIRECTIVE = new CaddyfileElementType("MATCH_DECLARE_TWO_DIRECTIVE");
  IElementType MATCH_DECLARE_TWO_SEP = new CaddyfileElementType("MATCH_DECLARE_TWO_SEP");
  IElementType MATCH_DIRECTIVE = new CaddyfileElementType("MATCH_DIRECTIVE");
  IElementType PORT_WITH_COLON = new CaddyfileElementType("PORT_WITH_COLON");
  IElementType PROPERTY = new CaddyfileElementType("PROPERTY");
  IElementType REDIR = new CaddyfileElementType("REDIR");
  IElementType RESPOND = new CaddyfileElementType("RESPOND");
  IElementType REVERSE_PROXY = new CaddyfileElementType("REVERSE_PROXY");
  IElementType TLS = new CaddyfileElementType("TLS");
  IElementType VARIABLE = new CaddyfileElementType("VARIABLE");

  IElementType AT = new CaddyfileTokenType("AT");
  IElementType COMMENT = new CaddyfileTokenType("COMMENT");
  IElementType COMPRESSION_METHOD = new CaddyfileTokenType("COMPRESSION_METHOD");
  IElementType CRLF = new CaddyfileTokenType("CRLF");
  IElementType DIRECTIVE_9_0 = new CaddyfileTokenType("directive_9_0");
  IElementType DOT = new CaddyfileTokenType("DOT");
  IElementType ENCODE_ARG_4_0 = new CaddyfileTokenType("encode_arg_4_0");
  IElementType FILEPATH = new CaddyfileTokenType("FILEPATH");
  IElementType GZIP_LEVEL = new CaddyfileTokenType("GZIP_LEVEL");
  IElementType HEADER = new CaddyfileTokenType("HEADER");
  IElementType HEADER_VALUE = new CaddyfileTokenType("HEADER_VALUE");
  IElementType HOSTNAME = new CaddyfileTokenType("HOSTNAME");
  IElementType IPV4 = new CaddyfileTokenType("IPV4");
  IElementType IPV6 = new CaddyfileTokenType("IPV6");
  IElementType LEFT_CURLY_BRACE = new CaddyfileTokenType("LEFT_CURLY_BRACE");
  IElementType MATCHER_NAME = new CaddyfileTokenType("MATCHER_NAME");
  IElementType MATCH_DECLARE_DIRECTIVE_4_0 = new CaddyfileTokenType("match_declare_directive_4_0");
  IElementType MATCH_NAME = new CaddyfileTokenType("MATCH_NAME");
  IElementType METHOD = new CaddyfileTokenType("METHOD");
  IElementType MINIMUM_LENGTH = new CaddyfileTokenType("MINIMUM_LENGTH");
  IElementType PASSWORD = new CaddyfileTokenType("PASSWORD");
  IElementType PORT = new CaddyfileTokenType("PORT");
  IElementType PROTOCOL = new CaddyfileTokenType("PROTOCOL");
  IElementType RIGHT_CURLY_BRACE = new CaddyfileTokenType("RIGHT_CURLY_BRACE");
  IElementType SLASH = new CaddyfileTokenType("SLASH");
  IElementType STAR = new CaddyfileTokenType("STAR");
  IElementType STATUS_CODE = new CaddyfileTokenType("STATUS_CODE");
  IElementType TEXT = new CaddyfileTokenType("TEXT");
  IElementType UNIX_SOCKET = new CaddyfileTokenType("UNIX_SOCKET");
  IElementType USERNAME = new CaddyfileTokenType("USERNAME");
  IElementType VARIABLE_NAME = new CaddyfileTokenType("VARIABLE_NAME");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ABORT) {
        return new CaddyfileAbortImpl(node);
      }
      else if (type == ACME_SERVER) {
        return new CaddyfileAcmeServerImpl(node);
      }
      else if (type == BASIC_AUTH) {
        return new CaddyfileBasicAuthImpl(node);
      }
      else if (type == BIND) {
        return new CaddyfileBindImpl(node);
      }
      else if (type == BINDING) {
        return new CaddyfileBindingImpl(node);
      }
      else if (type == COLON) {
        return new CaddyfileColonImpl(node);
      }
      else if (type == DIRECTIVE) {
        return new CaddyfileDirectiveImpl(node);
      }
      else if (type == ENCODE) {
        return new CaddyfileEncodeImpl(node);
      }
      else if (type == ENCODE_ARG) {
        return new CaddyfileEncodeArgImpl(node);
      }
      else if (type == ENCODE_ARG_GZIP) {
        return new CaddyfileEncodeArgGzipImpl(node);
      }
      else if (type == ENCODE_ARG_MINIMUM_LENGTH) {
        return new CaddyfileEncodeArgMinimumLengthImpl(node);
      }
      else if (type == ENCODE_ARG_ZSTD) {
        return new CaddyfileEncodeArgZstdImpl(node);
      }
      else if (type == GROUP) {
        return new CaddyfileGroupImpl(node);
      }
      else if (type == HOST) {
        return new CaddyfileHostImpl(node);
      }
      else if (type == HOSTNAME_MATCHER) {
        return new CaddyfileHostnameMatcherImpl(node);
      }
      else if (type == MATCHER) {
        return new CaddyfileMatcherImpl(node);
      }
      else if (type == MATCHER_ONE) {
        return new CaddyfileMatcherOneImpl(node);
      }
      else if (type == MATCHER_THR) {
        return new CaddyfileMatcherThrImpl(node);
      }
      else if (type == MATCHER_TWO) {
        return new CaddyfileMatcherTwoImpl(node);
      }
      else if (type == MATCH_BODY) {
        return new CaddyfileMatchBodyImpl(node);
      }
      else if (type == MATCH_DECLARE) {
        return new CaddyfileMatchDeclareImpl(node);
      }
      else if (type == MATCH_DECLARE_DIRECTIVE) {
        return new CaddyfileMatchDeclareDirectiveImpl(node);
      }
      else if (type == MATCH_DECLARE_DIR_HEADER) {
        return new CaddyfileMatchDeclareDirHeaderImpl(node);
      }
      else if (type == MATCH_DECLARE_DIR_METHOD) {
        return new CaddyfileMatchDeclareDirMethodImpl(node);
      }
      else if (type == MATCH_DECLARE_DIR_PATH) {
        return new CaddyfileMatchDeclareDirPathImpl(node);
      }
      else if (type == MATCH_DECLARE_DIR_STATUS) {
        return new CaddyfileMatchDeclareDirStatusImpl(node);
      }
      else if (type == MATCH_DECLARE_NOT) {
        return new CaddyfileMatchDeclareNotImpl(node);
      }
      else if (type == MATCH_DECLARE_ONE) {
        return new CaddyfileMatchDeclareOneImpl(node);
      }
      else if (type == MATCH_DECLARE_TWO) {
        return new CaddyfileMatchDeclareTwoImpl(node);
      }
      else if (type == MATCH_DECLARE_TWO_DIRECTIVE) {
        return new CaddyfileMatchDeclareTwoDirectiveImpl(node);
      }
      else if (type == MATCH_DECLARE_TWO_SEP) {
        return new CaddyfileMatchDeclareTwoSepImpl(node);
      }
      else if (type == MATCH_DIRECTIVE) {
        return new CaddyfileMatchDirectiveImpl(node);
      }
      else if (type == PORT_WITH_COLON) {
        return new CaddyfilePortWithColonImpl(node);
      }
      else if (type == PROPERTY) {
        return new CaddyfilePropertyImpl(node);
      }
      else if (type == REDIR) {
        return new CaddyfileRedirImpl(node);
      }
      else if (type == RESPOND) {
        return new CaddyfileRespondImpl(node);
      }
      else if (type == REVERSE_PROXY) {
        return new CaddyfileReverseProxyImpl(node);
      }
      else if (type == TLS) {
        return new CaddyfileTlsImpl(node);
      }
      else if (type == VARIABLE) {
        return new CaddyfileVariableImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
