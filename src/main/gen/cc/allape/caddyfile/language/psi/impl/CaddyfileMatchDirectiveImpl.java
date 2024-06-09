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

public class CaddyfileMatchDirectiveImpl extends ASTWrapperPsiElement implements CaddyfileMatchDirective {

  public CaddyfileMatchDirectiveImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CaddyfileVisitor visitor) {
    visitor.visitMatchDirective(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CaddyfileVisitor) accept((CaddyfileVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public CaddyfileMatchBody getMatchBody() {
    return findNotNullChildByClass(CaddyfileMatchBody.class);
  }

}
