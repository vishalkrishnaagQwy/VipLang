package org.lang.AstNode;

import org.objectweb.asm.MethodVisitor;

public class WhileNode extends ASTNode {
    public WhileNode(){

    }

    @Override
    public void accept(AST visitor) {
        visitor.visitWhileNode(this);
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {

    }
}
