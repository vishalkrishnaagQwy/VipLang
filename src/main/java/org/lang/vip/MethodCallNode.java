package org.lang.vip;

public class MethodCallNode extends ASTNode{
    public MethodCallNode(){

    }

    @Override
    public void accept(AST visitor) {
       visitor.visitMethodCallNode(this);
    }
}
