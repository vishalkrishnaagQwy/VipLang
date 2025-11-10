package org.lang.AstNode;


public class WhileNode extends ASTNode {
    public WhileNode(){

    }

    @Override
    public void accept(AST visitor) {
        visitor.visitWhileNode(this);
    }
}
