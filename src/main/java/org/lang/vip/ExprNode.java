package org.lang.vip;

class ExprNode extends ASTNode {
    String value;

    public ExprNode(String value) {
        this.value = value;
    }

    @Override
    public void accept(AST visitor) {
       visitor.visitExperNode(this);
    }
}
