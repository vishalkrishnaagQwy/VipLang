package org.lang.AstNode;


import java.util.List;

public class ArithematicExpr extends ASTNode {
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

    private ASTNode left;
    private List<ASTNode> right;
    private String operator;
    public ArithematicExpr(String operator, ASTNode left, List<ASTNode> right){
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
    @Override
    public void accept(AST visitor) {
        visitor.visitArithematicExpr(this);
    }
}
