package org.lang.AstNode;

import org.objectweb.asm.MethodVisitor;

public class VariableNode extends ASTNode {
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

