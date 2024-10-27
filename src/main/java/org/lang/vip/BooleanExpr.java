package org.lang.vip;

public class BooleanExpr extends ASTNode{
    public BooleanExpr(){

    }
    @Override
    public void accept(AST visitor) {
         visitor.visitBooleanExprNode(this);
    }
}
