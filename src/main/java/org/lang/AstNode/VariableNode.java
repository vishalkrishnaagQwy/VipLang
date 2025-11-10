package org.lang.AstNode;


public class VariableNode extends ASTNode {
    private String name;

    public VariableNode(String name) {
        this.name = name;
    }

    @Override
    public void accept(AST visitor) {
     visitor.visitVariableNode(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

