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

public class CaddyfileFileServerArgImpl extends ASTWrapperPsiElement implements CaddyfileFileServerArg {

  public CaddyfileFileServerArgImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CaddyfileVisitor visitor) {
    visitor.visitFileServerArg(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CaddyfileVisitor) accept((CaddyfileVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CaddyfileFileServerArgBrowse getFileServerArgBrowse() {
    return findChildByClass(CaddyfileFileServerArgBrowse.class);
  }

  @Override
  @Nullable
  public CaddyfileFileServerArgDisableCanonicalUris getFileServerArgDisableCanonicalUris() {
    return findChildByClass(CaddyfileFileServerArgDisableCanonicalUris.class);
  }

  @Override
  @Nullable
  public CaddyfileFileServerArgFs getFileServerArgFs() {
    return findChildByClass(CaddyfileFileServerArgFs.class);
  }

  @Override
  @Nullable
  public CaddyfileFileServerArgHide getFileServerArgHide() {
    return findChildByClass(CaddyfileFileServerArgHide.class);
  }

  @Override
  @Nullable
  public CaddyfileFileServerArgIndex getFileServerArgIndex() {
    return findChildByClass(CaddyfileFileServerArgIndex.class);
  }

  @Override
  @Nullable
  public CaddyfileFileServerArgPassThru getFileServerArgPassThru() {
    return findChildByClass(CaddyfileFileServerArgPassThru.class);
  }

  @Override
  @Nullable
  public CaddyfileFileServerArgPrecompressed getFileServerArgPrecompressed() {
    return findChildByClass(CaddyfileFileServerArgPrecompressed.class);
  }

  @Override
  @Nullable
  public CaddyfileFileServerArgRoot getFileServerArgRoot() {
    return findChildByClass(CaddyfileFileServerArgRoot.class);
  }

  @Override
  @Nullable
  public CaddyfileFileServerArgStatus getFileServerArgStatus() {
    return findChildByClass(CaddyfileFileServerArgStatus.class);
  }

}
