package org.lang.AstNode;

import org.objectweb.asm.MethodVisitor;

public class VersionNode extends ASTNode {
    String version;

    public VersionNode(String version) {
        this.version = version;
    }

    @Override
    public void accept(AST visitor) {
        visitor.visitVersionNode(this);
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {

    }
}
