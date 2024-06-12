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
  // "abort" matcher?
  public static boolean abort(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "abort")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ABORT, "<abort>");
    r = consumeToken(b, "abort");
    r = r && abort_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // matcher?
  private static boolean abort_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "abort_1")) return false;
    matcher(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // "acme_server" matcher?
  public static boolean acme_server(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "acme_server")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ACME_SERVER, "<acme server>");
    r = consumeToken(b, "acme_server");
    r = r && acme_server_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // matcher?
  private static boolean acme_server_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "acme_server_1")) return false;
    matcher(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // "basic_auth" matcher? LEFT_CURLY_BRACE (USERNAME PASSWORD)* RIGHT_CURLY_BRACE
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

  // matcher?
  private static boolean basic_auth_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "basic_auth_1")) return false;
    matcher(b, l + 1);
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
  // "bind" (IPV4|IPV6|UNIX_SOCKET)+
  public static boolean bind(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bind")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, BIND, "<bind>");
    r = consumeToken(b, "bind");
    r = r && bind_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (IPV4|IPV6|UNIX_SOCKET)+
  private static boolean bind_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bind_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = bind_1_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!bind_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "bind_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // IPV4|IPV6|UNIX_SOCKET
  private static boolean bind_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bind_1_0")) return false;
    boolean r;
    r = consumeToken(b, IPV4);
    if (!r) r = consumeToken(b, IPV6);
    if (!r) r = consumeToken(b, UNIX_SOCKET);
    return r;
  }

  /* ********************************************************** */
  // (hostname_matcher port_with_colon?) | port_with_colon
  public static boolean binding(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "binding")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, BINDING, "<binding>");
    r = binding_0(b, l + 1);
    if (!r) r = port_with_colon(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // hostname_matcher port_with_colon?
  private static boolean binding_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "binding_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = hostname_matcher(b, l + 1);
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
  // "gzip"|"zstd"|"br"
  public static boolean compression_method(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compression_method")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, COMPRESSION_METHOD, "<compression method>");
    r = consumeToken(b, "gzip");
    if (!r) r = consumeToken(b, "zstd");
    if (!r) r = consumeToken(b, "br");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // abort|
  //     acme_server|
  //     basic_auth|
  //     bind|
  //     encode|
  //     error|
  //     file_server|
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
    if (!r) r = bind(b, l + 1);
    if (!r) r = encode(b, l + 1);
    if (!r) r = error(b, l + 1);
    if (!r) r = file_server(b, l + 1);
    if (!r) r = tls(b, l + 1);
    if (!r) r = redir(b, l + 1);
    if (!r) r = reverse_proxy(b, l + 1);
    if (!r) r = respond(b, l + 1);
    if (!r) r = consumeToken(b, DIRECTIVE_11_0);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "encode" matcher? compression_method+ (LEFT_CURLY_BRACE encode_arg* RIGHT_CURLY_BRACE)?
  public static boolean encode(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "encode")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ENCODE, "<encode>");
    r = consumeToken(b, "encode");
    r = r && encode_1(b, l + 1);
    r = r && encode_2(b, l + 1);
    r = r && encode_3(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // matcher?
  private static boolean encode_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "encode_1")) return false;
    matcher(b, l + 1);
    return true;
  }

  // compression_method+
  private static boolean encode_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "encode_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = compression_method(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!compression_method(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "encode_2", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // (LEFT_CURLY_BRACE encode_arg* RIGHT_CURLY_BRACE)?
  private static boolean encode_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "encode_3")) return false;
    encode_3_0(b, l + 1);
    return true;
  }

  // LEFT_CURLY_BRACE encode_arg* RIGHT_CURLY_BRACE
  private static boolean encode_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "encode_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LEFT_CURLY_BRACE);
    r = r && encode_3_0_1(b, l + 1);
    r = r && consumeToken(b, RIGHT_CURLY_BRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // encode_arg*
  private static boolean encode_3_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "encode_3_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!encode_arg(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "encode_3_0_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // encode_arg_gzip|
  //     encode_arg_zstd|
  //     encode_arg_minimum_length|
  //     match_directive|
  public static boolean encode_arg(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "encode_arg")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ENCODE_ARG, "<encode arg>");
    r = encode_arg_gzip(b, l + 1);
    if (!r) r = encode_arg_zstd(b, l + 1);
    if (!r) r = encode_arg_minimum_length(b, l + 1);
    if (!r) r = match_directive(b, l + 1);
    if (!r) r = consumeToken(b, ENCODE_ARG_4_0);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "gzip" GZIP_LEVEL?
  public static boolean encode_arg_gzip(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "encode_arg_gzip")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ENCODE_ARG_GZIP, "<encode arg gzip>");
    r = consumeToken(b, "gzip");
    r = r && encode_arg_gzip_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // GZIP_LEVEL?
  private static boolean encode_arg_gzip_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "encode_arg_gzip_1")) return false;
    consumeToken(b, GZIP_LEVEL);
    return true;
  }

  /* ********************************************************** */
  // "minimum_length" MINIMUM_LENGTH
  public static boolean encode_arg_minimum_length(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "encode_arg_minimum_length")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ENCODE_ARG_MINIMUM_LENGTH, "<encode arg minimum length>");
    r = consumeToken(b, "minimum_length");
    r = r && consumeToken(b, MINIMUM_LENGTH);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "zstd"
  public static boolean encode_arg_zstd(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "encode_arg_zstd")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ENCODE_ARG_ZSTD, "<encode arg zstd>");
    r = consumeToken(b, "zstd");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "error" matcher? (STATUS_CODE|quoted_text STATUS_CODE) (LEFT_CURLY_BRACE error_arg? RIGHT_CURLY_BRACE)?
  public static boolean error(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ERROR, "<error>");
    r = consumeToken(b, "error");
    r = r && error_1(b, l + 1);
    r = r && error_2(b, l + 1);
    r = r && error_3(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // matcher?
  private static boolean error_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_1")) return false;
    matcher(b, l + 1);
    return true;
  }

  // STATUS_CODE|quoted_text STATUS_CODE
  private static boolean error_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STATUS_CODE);
    if (!r) r = error_2_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // quoted_text STATUS_CODE
  private static boolean error_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_2_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = quoted_text(b, l + 1);
    r = r && consumeToken(b, STATUS_CODE);
    exit_section_(b, m, null, r);
    return r;
  }

  // (LEFT_CURLY_BRACE error_arg? RIGHT_CURLY_BRACE)?
  private static boolean error_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_3")) return false;
    error_3_0(b, l + 1);
    return true;
  }

  // LEFT_CURLY_BRACE error_arg? RIGHT_CURLY_BRACE
  private static boolean error_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LEFT_CURLY_BRACE);
    r = r && error_3_0_1(b, l + 1);
    r = r && consumeToken(b, RIGHT_CURLY_BRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // error_arg?
  private static boolean error_3_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_3_0_1")) return false;
    error_arg(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // error_arg_message
  public static boolean error_arg(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_arg")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ERROR_ARG, "<error arg>");
    r = error_arg_message(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "message" (TEXT|quoted_text)
  public static boolean error_arg_message(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_arg_message")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ERROR_ARG_MESSAGE, "<error arg message>");
    r = consumeToken(b, "message");
    r = r && error_arg_message_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // TEXT|quoted_text
  private static boolean error_arg_message_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_arg_message_1")) return false;
    boolean r;
    r = consumeToken(b, TEXT);
    if (!r) r = quoted_text(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // "file_server" matcher? FILE_SERVER_BROWSE? (LEFT_CURLY_BRACE file_server_arg* RIGHT_CURLY_BRACE)?
  public static boolean file_server(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FILE_SERVER, "<file server>");
    r = consumeToken(b, "file_server");
    r = r && file_server_1(b, l + 1);
    r = r && file_server_2(b, l + 1);
    r = r && file_server_3(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // matcher?
  private static boolean file_server_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_1")) return false;
    matcher(b, l + 1);
    return true;
  }

  // FILE_SERVER_BROWSE?
  private static boolean file_server_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_2")) return false;
    consumeToken(b, FILE_SERVER_BROWSE);
    return true;
  }

  // (LEFT_CURLY_BRACE file_server_arg* RIGHT_CURLY_BRACE)?
  private static boolean file_server_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_3")) return false;
    file_server_3_0(b, l + 1);
    return true;
  }

  // LEFT_CURLY_BRACE file_server_arg* RIGHT_CURLY_BRACE
  private static boolean file_server_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LEFT_CURLY_BRACE);
    r = r && file_server_3_0_1(b, l + 1);
    r = r && consumeToken(b, RIGHT_CURLY_BRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // file_server_arg*
  private static boolean file_server_3_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_3_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!file_server_arg(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "file_server_3_0_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // file_server_arg_fs|
  //     file_server_arg_root|
  //     file_server_arg_hide|
  //     file_server_arg_index|
  //     file_server_arg_browse|
  //     file_server_arg_precompressed|
  //     file_server_arg_status|
  //     file_server_arg_disable_canonical_uris|
  //     file_server_arg_pass_thru|
  public static boolean file_server_arg(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_arg")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FILE_SERVER_ARG, "<file server arg>");
    r = file_server_arg_fs(b, l + 1);
    if (!r) r = file_server_arg_root(b, l + 1);
    if (!r) r = file_server_arg_hide(b, l + 1);
    if (!r) r = file_server_arg_index(b, l + 1);
    if (!r) r = file_server_arg_browse(b, l + 1);
    if (!r) r = file_server_arg_precompressed(b, l + 1);
    if (!r) r = file_server_arg_status(b, l + 1);
    if (!r) r = file_server_arg_disable_canonical_uris(b, l + 1);
    if (!r) r = file_server_arg_pass_thru(b, l + 1);
    if (!r) r = consumeToken(b, FILE_SERVER_ARG_9_0);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "browse" FILEPATH? (LEFT_CURLY_BRACE file_server_arg_browse_arg* RIGHT_CURLY_BRACE)?
  public static boolean file_server_arg_browse(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_arg_browse")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FILE_SERVER_ARG_BROWSE, "<file server arg browse>");
    r = consumeToken(b, "browse");
    r = r && file_server_arg_browse_1(b, l + 1);
    r = r && file_server_arg_browse_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // FILEPATH?
  private static boolean file_server_arg_browse_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_arg_browse_1")) return false;
    consumeToken(b, FILEPATH);
    return true;
  }

  // (LEFT_CURLY_BRACE file_server_arg_browse_arg* RIGHT_CURLY_BRACE)?
  private static boolean file_server_arg_browse_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_arg_browse_2")) return false;
    file_server_arg_browse_2_0(b, l + 1);
    return true;
  }

  // LEFT_CURLY_BRACE file_server_arg_browse_arg* RIGHT_CURLY_BRACE
  private static boolean file_server_arg_browse_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_arg_browse_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LEFT_CURLY_BRACE);
    r = r && file_server_arg_browse_2_0_1(b, l + 1);
    r = r && consumeToken(b, RIGHT_CURLY_BRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // file_server_arg_browse_arg*
  private static boolean file_server_arg_browse_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_arg_browse_2_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!file_server_arg_browse_arg(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "file_server_arg_browse_2_0_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // file_server_arg_browse_arg_reveal_symlinks
  public static boolean file_server_arg_browse_arg(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_arg_browse_arg")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FILE_SERVER_ARG_BROWSE_ARG, "<file server arg browse arg>");
    r = file_server_arg_browse_arg_reveal_symlinks(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "reveal_symlinks"
  public static boolean file_server_arg_browse_arg_reveal_symlinks(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_arg_browse_arg_reveal_symlinks")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FILE_SERVER_ARG_BROWSE_ARG_REVEAL_SYMLINKS, "<file server arg browse arg reveal symlinks>");
    r = consumeToken(b, "reveal_symlinks");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "disable_canonical_uris"
  public static boolean file_server_arg_disable_canonical_uris(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_arg_disable_canonical_uris")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FILE_SERVER_ARG_DISABLE_CANONICAL_URIS, "<file server arg disable canonical uris>");
    r = consumeToken(b, "disable_canonical_uris");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "fs" BACKEND+
  public static boolean file_server_arg_fs(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_arg_fs")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FILE_SERVER_ARG_FS, "<file server arg fs>");
    r = consumeToken(b, "fs");
    r = r && file_server_arg_fs_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // BACKEND+
  private static boolean file_server_arg_fs_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_arg_fs_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, BACKEND);
    while (r) {
      int c = current_position_(b);
      if (!consumeToken(b, BACKEND)) break;
      if (!empty_element_parsed_guard_(b, "file_server_arg_fs_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "hide" FILEPATH+
  public static boolean file_server_arg_hide(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_arg_hide")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FILE_SERVER_ARG_HIDE, "<file server arg hide>");
    r = consumeToken(b, "hide");
    r = r && file_server_arg_hide_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // FILEPATH+
  private static boolean file_server_arg_hide_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_arg_hide_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, FILEPATH);
    while (r) {
      int c = current_position_(b);
      if (!consumeToken(b, FILEPATH)) break;
      if (!empty_element_parsed_guard_(b, "file_server_arg_hide_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "index" TEXT+
  public static boolean file_server_arg_index(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_arg_index")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FILE_SERVER_ARG_INDEX, "<file server arg index>");
    r = consumeToken(b, "index");
    r = r && file_server_arg_index_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // TEXT+
  private static boolean file_server_arg_index_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_arg_index_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TEXT);
    while (r) {
      int c = current_position_(b);
      if (!consumeToken(b, TEXT)) break;
      if (!empty_element_parsed_guard_(b, "file_server_arg_index_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "pass_thru"
  public static boolean file_server_arg_pass_thru(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_arg_pass_thru")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FILE_SERVER_ARG_PASS_THRU, "<file server arg pass thru>");
    r = consumeToken(b, "pass_thru");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "precompressed" compression_method+
  public static boolean file_server_arg_precompressed(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_arg_precompressed")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FILE_SERVER_ARG_PRECOMPRESSED, "<file server arg precompressed>");
    r = consumeToken(b, "precompressed");
    r = r && file_server_arg_precompressed_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // compression_method+
  private static boolean file_server_arg_precompressed_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_arg_precompressed_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = compression_method(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!compression_method(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "file_server_arg_precompressed_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "root" FILEPATH
  public static boolean file_server_arg_root(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_arg_root")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FILE_SERVER_ARG_ROOT, "<file server arg root>");
    r = consumeToken(b, "root");
    r = r && consumeToken(b, FILEPATH);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "status" STATUS_CODE
  public static boolean file_server_arg_status(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_server_arg_status")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FILE_SERVER_ARG_STATUS, "<file server arg status>");
    r = consumeToken(b, "status");
    r = r && consumeToken(b, STATUS_CODE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // LEFT_CURLY_BRACE (match_declare|directive)* RIGHT_CURLY_BRACE
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

  // (match_declare|directive)*
  private static boolean group_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "group_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!group_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "group_1", c)) break;
    }
    return true;
  }

  // match_declare|directive
  private static boolean group_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "group_1_0")) return false;
    boolean r;
    r = match_declare(b, l + 1);
    if (!r) r = directive(b, l + 1);
    return r;
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
  // (TEXT|STAR)(DOT (TEXT|STAR))*
  public static boolean hostname_matcher(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "hostname_matcher")) return false;
    if (!nextTokenIs(b, "<hostname matcher>", STAR, TEXT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, HOSTNAME_MATCHER, "<hostname matcher>");
    r = hostname_matcher_0(b, l + 1);
    r = r && hostname_matcher_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // TEXT|STAR
  private static boolean hostname_matcher_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "hostname_matcher_0")) return false;
    boolean r;
    r = consumeToken(b, TEXT);
    if (!r) r = consumeToken(b, STAR);
    return r;
  }

  // (DOT (TEXT|STAR))*
  private static boolean hostname_matcher_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "hostname_matcher_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!hostname_matcher_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "hostname_matcher_1", c)) break;
    }
    return true;
  }

  // DOT (TEXT|STAR)
  private static boolean hostname_matcher_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "hostname_matcher_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOT);
    r = r && hostname_matcher_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // TEXT|STAR
  private static boolean hostname_matcher_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "hostname_matcher_1_0_1")) return false;
    boolean r;
    r = consumeToken(b, TEXT);
    if (!r) r = consumeToken(b, STAR);
    return r;
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
  // match_declare_one|match_declare_two*
  public static boolean match_body(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_body")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATCH_BODY, "<match body>");
    r = match_declare_one(b, l + 1);
    if (!r) r = match_body_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // match_declare_two*
  private static boolean match_body_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_body_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!match_declare_two(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "match_body_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // "@" MATCH_NAME match_body
  public static boolean match_declare(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATCH_DECLARE, "<match declare>");
    r = consumeToken(b, "@");
    r = r && consumeToken(b, MATCH_NAME);
    r = r && match_body(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // match_declare_not? "header" HEADER HEADER_VALUE?
  public static boolean match_declare_dir_header(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare_dir_header")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATCH_DECLARE_DIR_HEADER, "<match declare dir header>");
    r = match_declare_dir_header_0(b, l + 1);
    r = r && consumeToken(b, "header");
    r = r && consumeToken(b, HEADER);
    r = r && match_declare_dir_header_3(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // match_declare_not?
  private static boolean match_declare_dir_header_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare_dir_header_0")) return false;
    match_declare_not(b, l + 1);
    return true;
  }

  // HEADER_VALUE?
  private static boolean match_declare_dir_header_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare_dir_header_3")) return false;
    consumeToken(b, HEADER_VALUE);
    return true;
  }

  /* ********************************************************** */
  // match_declare_not? "method" METHOD+
  public static boolean match_declare_dir_method(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare_dir_method")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATCH_DECLARE_DIR_METHOD, "<match declare dir method>");
    r = match_declare_dir_method_0(b, l + 1);
    r = r && consumeToken(b, "method");
    r = r && match_declare_dir_method_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // match_declare_not?
  private static boolean match_declare_dir_method_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare_dir_method_0")) return false;
    match_declare_not(b, l + 1);
    return true;
  }

  // METHOD+
  private static boolean match_declare_dir_method_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare_dir_method_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, METHOD);
    while (r) {
      int c = current_position_(b);
      if (!consumeToken(b, METHOD)) break;
      if (!empty_element_parsed_guard_(b, "match_declare_dir_method_2", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // match_declare_not? "path" matcher_two+
  public static boolean match_declare_dir_path(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare_dir_path")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATCH_DECLARE_DIR_PATH, "<match declare dir path>");
    r = match_declare_dir_path_0(b, l + 1);
    r = r && consumeToken(b, "path");
    r = r && match_declare_dir_path_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // match_declare_not?
  private static boolean match_declare_dir_path_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare_dir_path_0")) return false;
    match_declare_not(b, l + 1);
    return true;
  }

  // matcher_two+
  private static boolean match_declare_dir_path_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare_dir_path_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = matcher_two(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!matcher_two(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "match_declare_dir_path_2", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // match_declare_not? "status" STATUS_CODE+
  public static boolean match_declare_dir_status(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare_dir_status")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATCH_DECLARE_DIR_STATUS, "<match declare dir status>");
    r = match_declare_dir_status_0(b, l + 1);
    r = r && consumeToken(b, "status");
    r = r && match_declare_dir_status_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // match_declare_not?
  private static boolean match_declare_dir_status_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare_dir_status_0")) return false;
    match_declare_not(b, l + 1);
    return true;
  }

  // STATUS_CODE+
  private static boolean match_declare_dir_status_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare_dir_status_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STATUS_CODE);
    while (r) {
      int c = current_position_(b);
      if (!consumeToken(b, STATUS_CODE)) break;
      if (!empty_element_parsed_guard_(b, "match_declare_dir_status_2", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // match_declare_dir_header|
  //     match_declare_dir_method|
  //     match_declare_dir_path|
  //     match_declare_dir_status|
  public static boolean match_declare_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare_directive")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATCH_DECLARE_DIRECTIVE, "<match declare directive>");
    r = match_declare_dir_header(b, l + 1);
    if (!r) r = match_declare_dir_method(b, l + 1);
    if (!r) r = match_declare_dir_path(b, l + 1);
    if (!r) r = match_declare_dir_status(b, l + 1);
    if (!r) r = consumeToken(b, MATCH_DECLARE_DIRECTIVE_4_0);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "not"
  public static boolean match_declare_not(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare_not")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATCH_DECLARE_NOT, "<match declare not>");
    r = consumeToken(b, "not");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // LEFT_CURLY_BRACE match_declare_directive* RIGHT_CURLY_BRACE
  public static boolean match_declare_one(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare_one")) return false;
    if (!nextTokenIs(b, LEFT_CURLY_BRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LEFT_CURLY_BRACE);
    r = r && match_declare_one_1(b, l + 1);
    r = r && consumeToken(b, RIGHT_CURLY_BRACE);
    exit_section_(b, m, MATCH_DECLARE_ONE, r);
    return r;
  }

  // match_declare_directive*
  private static boolean match_declare_one_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare_one_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!match_declare_directive(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "match_declare_one_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // (match_declare_two_directive match_declare_two_sep)* match_declare_two_directive
  public static boolean match_declare_two(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare_two")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATCH_DECLARE_TWO, "<match declare two>");
    r = match_declare_two_0(b, l + 1);
    r = r && match_declare_two_directive(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (match_declare_two_directive match_declare_two_sep)*
  private static boolean match_declare_two_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare_two_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!match_declare_two_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "match_declare_two_0", c)) break;
    }
    return true;
  }

  // match_declare_two_directive match_declare_two_sep
  private static boolean match_declare_two_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare_two_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = match_declare_two_directive(b, l + 1);
    r = r && match_declare_two_sep(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // match_declare_directive
  public static boolean match_declare_two_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare_two_directive")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATCH_DECLARE_TWO_DIRECTIVE, "<match declare two directive>");
    r = match_declare_directive(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "|"
  public static boolean match_declare_two_sep(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_declare_two_sep")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATCH_DECLARE_TWO_SEP, "<match declare two sep>");
    r = consumeToken(b, "|");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "match" match_body
  public static boolean match_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "match_directive")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATCH_DIRECTIVE, "<match directive>");
    r = consumeToken(b, "match");
    r = r && match_body(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // matcher_one|matcher_two|matcher_thr
  public static boolean matcher(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matcher")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATCHER, "<matcher>");
    r = matcher_one(b, l + 1);
    if (!r) r = matcher_two(b, l + 1);
    if (!r) r = matcher_thr(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // STAR
  public static boolean matcher_one(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matcher_one")) return false;
    if (!nextTokenIs(b, STAR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STAR);
    exit_section_(b, m, MATCHER_ONE, r);
    return r;
  }

  /* ********************************************************** */
  // AT MATCHER_NAME
  public static boolean matcher_thr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matcher_thr")) return false;
    if (!nextTokenIs(b, AT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, AT, MATCHER_NAME);
    exit_section_(b, m, MATCHER_THR, r);
    return r;
  }

  /* ********************************************************** */
  // SLASH ((TEXT|STAR) SLASH?)*
  public static boolean matcher_two(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matcher_two")) return false;
    if (!nextTokenIs(b, SLASH)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SLASH);
    r = r && matcher_two_1(b, l + 1);
    exit_section_(b, m, MATCHER_TWO, r);
    return r;
  }

  // ((TEXT|STAR) SLASH?)*
  private static boolean matcher_two_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matcher_two_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!matcher_two_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "matcher_two_1", c)) break;
    }
    return true;
  }

  // (TEXT|STAR) SLASH?
  private static boolean matcher_two_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matcher_two_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = matcher_two_1_0_0(b, l + 1);
    r = r && matcher_two_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // TEXT|STAR
  private static boolean matcher_two_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matcher_two_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, TEXT);
    if (!r) r = consumeToken(b, STAR);
    return r;
  }

  // SLASH?
  private static boolean matcher_two_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matcher_two_1_0_1")) return false;
    consumeToken(b, SLASH);
    return true;
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
  // QUOTATION TEXT QUOTATION
  public static boolean quoted_text(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "quoted_text")) return false;
    if (!nextTokenIs(b, QUOTATION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, QUOTATION, TEXT, QUOTATION);
    exit_section_(b, m, QUOTED_TEXT, r);
    return r;
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
