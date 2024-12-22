package org.lang.vip;

import org.objectweb.asm.MethodVisitor;

public class ExprNode extends ASTNode {
    String value;

    public ExprNode(String value) {
        this.value = value;
    }

    @Override
    public void accept(AST visitor) {
       visitor.visitExperNode(this);
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {

    }
}
