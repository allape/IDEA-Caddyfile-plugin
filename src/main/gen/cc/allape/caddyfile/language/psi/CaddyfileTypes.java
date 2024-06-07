// This is a generated file. Not intended for manual editing.
package cc.allape.caddyfile.language.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import cc.allape.caddyfile.CaddyfileElementType;
import cc.allape.caddyfile.CaddyfileTokenType;
import cc.allape.caddyfile.language.psi.impl.*;

public interface CaddyfileTypes {

  IElementType BINDING = new CaddyfileElementType("BINDING");
  IElementType COLON = new CaddyfileElementType("COLON");
  IElementType DIRECTIVE = new CaddyfileElementType("DIRECTIVE");
  IElementType GROUP = new CaddyfileElementType("GROUP");
  IElementType HOST = new CaddyfileElementType("HOST");
  IElementType PORT_WITH_COLON = new CaddyfileElementType("PORT_WITH_COLON");
  IElementType PROPERTY = new CaddyfileElementType("PROPERTY");
  IElementType REDIR = new CaddyfileElementType("REDIR");
  IElementType RESPOND = new CaddyfileElementType("RESPOND");
  IElementType REVERSE_PROXY = new CaddyfileElementType("REVERSE_PROXY");
  IElementType TLS = new CaddyfileElementType("TLS");
  IElementType VARIABLE = new CaddyfileElementType("VARIABLE");

  IElementType BINDING_HOSTNAME = new CaddyfileTokenType("BINDING_HOSTNAME");
  IElementType COMMENT = new CaddyfileTokenType("COMMENT");
  IElementType CRLF = new CaddyfileTokenType("CRLF");
  IElementType FILEPATH = new CaddyfileTokenType("FILEPATH");
  IElementType HOSTNAME = new CaddyfileTokenType("HOSTNAME");
  IElementType LEFT_CURLY_BRACE = new CaddyfileTokenType("LEFT_CURLY_BRACE");
  IElementType PORT = new CaddyfileTokenType("PORT");
  IElementType PROTOCOL = new CaddyfileTokenType("PROTOCOL");
  IElementType RIGHT_CURLY_BRACE = new CaddyfileTokenType("RIGHT_CURLY_BRACE");
  IElementType STATUS_CODE = new CaddyfileTokenType("STATUS_CODE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == BINDING) {
        return new CaddyfileBindingImpl(node);
      }
      else if (type == COLON) {
        return new CaddyfileColonImpl(node);
      }
      else if (type == DIRECTIVE) {
        return new CaddyfileDirectiveImpl(node);
      }
      else if (type == GROUP) {
        return new CaddyfileGroupImpl(node);
      }
      else if (type == HOST) {
        return new CaddyfileHostImpl(node);
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
