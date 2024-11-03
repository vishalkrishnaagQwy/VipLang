package org.lang.vip;

class NumberNode extends ASTNode {
    int value;

    public NumberNode(int value) {
        this.value = value;
    }

    @Override
    public void accept(AST visitor) {

    }
}
