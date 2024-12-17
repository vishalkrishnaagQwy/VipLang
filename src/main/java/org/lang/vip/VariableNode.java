package org.lang.vip;

class VariableNode extends ASTNode {
    String name;

    public VariableNode(String name) {
        this.name = name;
    }

    @Override
    public void accept(AST visitor) {
     visitor.visitVariableNode(this);
    }
}

