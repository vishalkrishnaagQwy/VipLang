package org.lang.vip;

import org.objectweb.asm.MethodVisitor;

public class VariableNode extends ASTNode {
    String name;

    public VariableNode(String name) {
        this.name = name;
    }

    @Override
    public void accept(AST visitor) {
     visitor.visitVariableNode(this);
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {

    }
}

