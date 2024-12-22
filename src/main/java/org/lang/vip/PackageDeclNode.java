package org.lang.vip;

import org.objectweb.asm.MethodVisitor;

import java.util.List;

public class PackageDeclNode extends ASTNode{
    String packageRoute;
    public PackageDeclNode(String folderList) {
        this.packageRoute = folderList;
    }

    @Override
    public void accept(AST visitor) {
        visitor.visitPackageDeclNode(this);
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {

    }
}
