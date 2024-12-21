package org.lang.vip;

public class ForNode {
    public ForNode() {
    }

    public void accept(AST visitor) {
        visitor.visitForNode(this);
    }
}
