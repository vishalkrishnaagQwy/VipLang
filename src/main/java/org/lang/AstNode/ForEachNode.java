package org.lang.AstNode;

import org.objectweb.asm.MethodVisitor;

public class ForEachNode  extends ASTNode {
    public ForEachNode() {
    }
    @Override
    public void accept(AST visitor) {
        visitor.visitForEachNode(this);
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {

    }
}
