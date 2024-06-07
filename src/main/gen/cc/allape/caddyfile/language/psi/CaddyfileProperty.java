// This is a generated file. Not intended for manual editing.
package cc.allape.caddyfile.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface CaddyfileProperty extends PsiElement {

  @NotNull
  CaddyfileBinding getBinding();

  @NotNull
  CaddyfileGroup getGroup();

  @Nullable
  String getKey();

  @Nullable
  String getValue();

}
