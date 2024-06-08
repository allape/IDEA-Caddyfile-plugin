// This is a generated file. Not intended for manual editing.
package cc.allape.caddyfile.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static cc.allape.caddyfile.language.psi.CaddyfileTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import cc.allape.caddyfile.language.psi.*;
import cc.allape.caddyfile.CaddyfilePsiImplUtil;

public class CaddyfileEncodeArgImpl extends ASTWrapperPsiElement implements CaddyfileEncodeArg {

  public CaddyfileEncodeArgImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CaddyfileVisitor visitor) {
    visitor.visitEncodeArg(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CaddyfileVisitor) accept((CaddyfileVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CaddyfileEncodeArgGzip getEncodeArgGzip() {
    return findChildByClass(CaddyfileEncodeArgGzip.class);
  }

  @Override
  @Nullable
  public CaddyfileEncodeArgMatch getEncodeArgMatch() {
    return findChildByClass(CaddyfileEncodeArgMatch.class);
  }

  @Override
  @Nullable
  public CaddyfileEncodeArgMinimumLength getEncodeArgMinimumLength() {
    return findChildByClass(CaddyfileEncodeArgMinimumLength.class);
  }

  @Override
  @Nullable
  public CaddyfileEncodeArgZstd getEncodeArgZstd() {
    return findChildByClass(CaddyfileEncodeArgZstd.class);
  }

}
