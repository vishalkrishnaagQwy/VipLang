package org.lang.AstNode;

import org.objectweb.asm.MethodVisitor;

public class ForNode extends ASTNode {
    public ForNode() {
    }

    @Override
    public void accept(AST visitor) {
        visitor.visitForNode(this);
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {

    }
}
