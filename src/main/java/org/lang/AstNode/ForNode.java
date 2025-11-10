package org.lang.AstNode;


public class ForNode extends ASTNode {
    public ForNode() {
    }

    @Override
    public void accept(AST visitor) {
        visitor.visitForNode(this);
    }
}
