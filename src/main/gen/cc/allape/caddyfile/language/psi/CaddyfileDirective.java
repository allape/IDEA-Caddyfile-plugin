// This is a generated file. Not intended for manual editing.
package cc.allape.caddyfile.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface CaddyfileDirective extends PsiElement {

  @Nullable
  CaddyfileAbort getAbort();

  @Nullable
  CaddyfileAcmeServer getAcmeServer();

  @Nullable
  CaddyfileBasicAuth getBasicAuth();

  @Nullable
  CaddyfileBind getBind();

  @Nullable
  CaddyfileEncode getEncode();

  @Nullable
  CaddyfileError getError();

  @Nullable
  CaddyfileFileServer getFileServer();

  @Nullable
  CaddyfileForwardAuth getForwardAuth();

  @Nullable
  CaddyfileRedir getRedir();

  @Nullable
  CaddyfileRespond getRespond();

  @Nullable
  CaddyfileReverseProxy getReverseProxy();

  @Nullable
  CaddyfileTls getTls();

}
