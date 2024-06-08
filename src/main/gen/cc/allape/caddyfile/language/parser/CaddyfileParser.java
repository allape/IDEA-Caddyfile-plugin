// This is a generated file. Not intended for manual editing.
package cc.allape.caddyfile.language.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static cc.allape.caddyfile.language.psi.CaddyfileTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class CaddyfileParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return caddyfileFile(b, l + 1);
  }

  /* ********************************************************** */
  // "abort"
  public static boolean abort(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "abort")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ABORT, "<abort>");
    r = consumeToken(b, "abort");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "acme_server"
  public static boolean acme_server(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "acme_server")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ACME_SERVER, "<acme server>");
    r = consumeToken(b, "acme_server");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "basic_auth" starred_path? LEFT_CURLY_BRACE (USERNAME PASSWORD)* RIGHT_CURLY_BRACE
  public static boolean basic_auth(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "basic_auth")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, BASIC_AUTH, "<basic auth>");
    r = consumeToken(b, "basic_auth");
    r = r && basic_auth_1(b, l + 1);
    r = r && consumeToken(b, LEFT_CURLY_BRACE);
    r = r && basic_auth_3(b, l + 1);
    r = r && consumeToken(b, RIGHT_CURLY_BRACE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // starred_path?
  private static boolean basic_auth_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "basic_auth_1")) return false;
    starred_path(b, l + 1);
    return true;
  }

  // (USERNAME PASSWORD)*
  private static boolean basic_auth_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "basic_auth_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!basic_auth_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "basic_auth_3", c)) break;
    }
    return true;
  }

  // USERNAME PASSWORD
  private static boolean basic_auth_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "basic_auth_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, USERNAME, PASSWORD);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (starred_hostname port_with_colon?) | port_with_colon
  public static boolean binding(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "binding")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, BINDING, "<binding>");
    r = binding_0(b, l + 1);
    if (!r) r = port_with_colon(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // starred_hostname port_with_colon?
  private static boolean binding_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "binding_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = starred_hostname(b, l + 1);
    r = r && binding_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // port_with_colon?
  private static boolean binding_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "binding_0_1")) return false;
    port_with_colon(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // item_*
  static boolean caddyfileFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "caddyfileFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "caddyfileFile", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // ":"
  public static boolean colon(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "colon")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, COLON, "<colon>");
    r = consumeToken(b, ":");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // abort|
  //     acme_server|
  //     basic_auth|
  //     tls|
  //     redir|
  //     reverse_proxy|
  //     respond|
  public static boolean directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "directive")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DIRECTIVE, "<directive>");
    r = abort(b, l + 1);
    if (!r) r = acme_server(b, l + 1);
    if (!r) r = basic_auth(b, l + 1);
    if (!r) r = tls(b, l + 1);
    if (!r) r = redir(b, l + 1);
    if (!r) r = reverse_proxy(b, l + 1);
    if (!r) r = respond(b, l + 1);
    if (!r) r = consumeToken(b, DIRECTIVE_7_0);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // LEFT_CURLY_BRACE directive* RIGHT_CURLY_BRACE
  public static boolean group(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "group")) return false;
    if (!nextTokenIs(b, LEFT_CURLY_BRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LEFT_CURLY_BRACE);
    r = r && group_1(b, l + 1);
    r = r && consumeToken(b, RIGHT_CURLY_BRACE);
    exit_section_(b, m, GROUP, r);
    return r;
  }

  // directive*
  private static boolean group_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "group_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!directive(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "group_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // HOSTNAME port_with_colon?
  public static boolean host(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "host")) return false;
    if (!nextTokenIs(b, HOSTNAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, HOSTNAME);
    r = r && host_1(b, l + 1);
    exit_section_(b, m, HOST, r);
    return r;
  }

  // port_with_colon?
  private static boolean host_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "host_1")) return false;
    port_with_colon(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // property|COMMENT|CRLF
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    r = property(b, l + 1);
    if (!r) r = consumeToken(b, COMMENT);
    if (!r) r = consumeToken(b, CRLF);
    return r;
  }

  /* ********************************************************** */
  // colon PORT
  public static boolean port_with_colon(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "port_with_colon")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PORT_WITH_COLON, "<port with colon>");
    r = colon(b, l + 1);
    r = r && consumeToken(b, PORT);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // binding? group
  public static boolean property(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PROPERTY, "<property>");
    r = property_0(b, l + 1);
    r = r && group(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // binding?
  private static boolean property_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property_0")) return false;
    binding(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // "redir" PROTOCOL (TEXT|variable)+
  public static boolean redir(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "redir")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, REDIR, "<redir>");
    r = consumeToken(b, "redir");
    r = r && consumeToken(b, PROTOCOL);
    r = r && redir_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (TEXT|variable)+
  private static boolean redir_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "redir_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = redir_2_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!redir_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "redir_2", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // TEXT|variable
  private static boolean redir_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "redir_2_0")) return false;
    boolean r;
    r = consumeToken(b, TEXT);
    if (!r) r = variable(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // "respond" STATUS_CODE
  public static boolean respond(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "respond")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, RESPOND, "<respond>");
    r = consumeToken(b, "respond");
    r = r && consumeToken(b, STATUS_CODE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "reverse_proxy" PROTOCOL host
  public static boolean reverse_proxy(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "reverse_proxy")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, REVERSE_PROXY, "<reverse proxy>");
    r = consumeToken(b, "reverse_proxy");
    r = r && consumeToken(b, PROTOCOL);
    r = r && host(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (TEXT|STAR)(DOT (TEXT|STAR))*
  public static boolean starred_hostname(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "starred_hostname")) return false;
    if (!nextTokenIs(b, "<starred hostname>", STAR, TEXT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STARRED_HOSTNAME, "<starred hostname>");
    r = starred_hostname_0(b, l + 1);
    r = r && starred_hostname_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // TEXT|STAR
  private static boolean starred_hostname_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "starred_hostname_0")) return false;
    boolean r;
    r = consumeToken(b, TEXT);
    if (!r) r = consumeToken(b, STAR);
    return r;
  }

  // (DOT (TEXT|STAR))*
  private static boolean starred_hostname_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "starred_hostname_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!starred_hostname_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "starred_hostname_1", c)) break;
    }
    return true;
  }

  // DOT (TEXT|STAR)
  private static boolean starred_hostname_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "starred_hostname_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOT);
    r = r && starred_hostname_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // TEXT|STAR
  private static boolean starred_hostname_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "starred_hostname_1_0_1")) return false;
    boolean r;
    r = consumeToken(b, TEXT);
    if (!r) r = consumeToken(b, STAR);
    return r;
  }

  /* ********************************************************** */
  // (SLASH? (TEXT|STAR))* SLASH?
  public static boolean starred_path(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "starred_path")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STARRED_PATH, "<starred path>");
    r = starred_path_0(b, l + 1);
    r = r && starred_path_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (SLASH? (TEXT|STAR))*
  private static boolean starred_path_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "starred_path_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!starred_path_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "starred_path_0", c)) break;
    }
    return true;
  }

  // SLASH? (TEXT|STAR)
  private static boolean starred_path_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "starred_path_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = starred_path_0_0_0(b, l + 1);
    r = r && starred_path_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // SLASH?
  private static boolean starred_path_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "starred_path_0_0_0")) return false;
    consumeToken(b, SLASH);
    return true;
  }

  // TEXT|STAR
  private static boolean starred_path_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "starred_path_0_0_1")) return false;
    boolean r;
    r = consumeToken(b, TEXT);
    if (!r) r = consumeToken(b, STAR);
    return r;
  }

  // SLASH?
  private static boolean starred_path_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "starred_path_1")) return false;
    consumeToken(b, SLASH);
    return true;
  }

  /* ********************************************************** */
  // "tls" FILEPATH FILEPATH
  public static boolean tls(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tls")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TLS, "<tls>");
    r = consumeToken(b, "tls");
    r = r && consumeTokens(b, 0, FILEPATH, FILEPATH);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // LEFT_CURLY_BRACE VARIABLE_NAME RIGHT_CURLY_BRACE
  public static boolean variable(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable")) return false;
    if (!nextTokenIs(b, LEFT_CURLY_BRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, LEFT_CURLY_BRACE, VARIABLE_NAME, RIGHT_CURLY_BRACE);
    exit_section_(b, m, VARIABLE, r);
    return r;
  }

}
