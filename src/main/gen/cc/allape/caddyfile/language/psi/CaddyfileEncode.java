// This is a generated file. Not intended for manual editing.
package cc.allape.caddyfile.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface CaddyfileEncode extends PsiElement {

  @NotNull
  List<CaddyfileEncodeArg> getEncodeArgList();

  @Nullable
  CaddyfileMatcher getMatcher();

}
