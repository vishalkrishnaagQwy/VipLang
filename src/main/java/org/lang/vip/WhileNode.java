package org.lang.vip;

public class WhileNode extends ASTNode {
    public WhileNode(){

    }

    @Override
    public void accept(AST visitor) {
        visitor.visitWhileNode(this);
    }
}
