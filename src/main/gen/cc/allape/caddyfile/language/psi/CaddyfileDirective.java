// This is a generated file. Not intended for manual editing.
package cc.allape.caddyfile.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface CaddyfileDirective extends PsiElement {

  @Nullable
  CaddyfileRedir getRedir();

  @Nullable
  CaddyfileRespond getRespond();

  @Nullable
  CaddyfileReverseProxy getReverseProxy();

  @Nullable
  CaddyfileTls getTls();

}
