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

public class CaddyfileDirectiveImpl extends ASTWrapperPsiElement implements CaddyfileDirective {

  public CaddyfileDirectiveImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CaddyfileVisitor visitor) {
    visitor.visitDirective(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CaddyfileVisitor) accept((CaddyfileVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CaddyfileAbort getAbort() {
    return findChildByClass(CaddyfileAbort.class);
  }

  @Override
  @Nullable
  public CaddyfileAcmeServer getAcmeServer() {
    return findChildByClass(CaddyfileAcmeServer.class);
  }

  @Override
  @Nullable
  public CaddyfileBasicAuth getBasicAuth() {
    return findChildByClass(CaddyfileBasicAuth.class);
  }

  @Override
  @Nullable
  public CaddyfileBind getBind() {
    return findChildByClass(CaddyfileBind.class);
  }

  @Override
  @Nullable
  public CaddyfileEncode getEncode() {
    return findChildByClass(CaddyfileEncode.class);
  }

  @Override
  @Nullable
  public CaddyfileError getError() {
    return findChildByClass(CaddyfileError.class);
  }

  @Override
  @Nullable
  public CaddyfileFileServer getFileServer() {
    return findChildByClass(CaddyfileFileServer.class);
  }

  @Override
  @Nullable
  public CaddyfileRedir getRedir() {
    return findChildByClass(CaddyfileRedir.class);
  }

  @Override
  @Nullable
  public CaddyfileRespond getRespond() {
    return findChildByClass(CaddyfileRespond.class);
  }

  @Override
  @Nullable
  public CaddyfileReverseProxy getReverseProxy() {
    return findChildByClass(CaddyfileReverseProxy.class);
  }

  @Override
  @Nullable
  public CaddyfileTls getTls() {
    return findChildByClass(CaddyfileTls.class);
  }

}
