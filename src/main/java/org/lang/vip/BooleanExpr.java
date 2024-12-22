package org.lang.vip;

import org.objectweb.asm.MethodVisitor;

import java.util.List;

public class BooleanExpr extends ASTNode{
   private ASTNode left;

    public ASTNode getLeft() {
        return left;
    }

    public void setLeft(ASTNode left) {
        this.left = left;
    }

    public List<ASTNode> getRight() {
        return right;
    }

    public void setRight(List<ASTNode> right) {
        this.right = right;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    private List<ASTNode> right;
   private String operator;
    public BooleanExpr(String operator, ASTNode left, List<ASTNode> right){
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
    @Override
    public void accept(AST visitor) {
         visitor.visitBooleanExprNode(this);
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {
        visitor.visitBooleanExprNode(this,methodVisitor);
    }
}
