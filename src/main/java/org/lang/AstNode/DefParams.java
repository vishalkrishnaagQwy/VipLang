package org.lang.AstNode;

import org.objectweb.asm.MethodVisitor;

public class DefParams extends ASTNode {

    public ASTNode getValue() {
        return value;
    }

    public void setValue(ASTNode value) {
        this.value = value;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public ASTNode getAssignExpr() {
        return assignExpr;
    }

    public void setAssignExpr(ASTNode assignExpr) {
        this.assignExpr = assignExpr;
    }


    private ASTNode value;
    private String param;
    private ASTNode assignExpr;



    public DefParams() {
    }

    @Override
    public void accept(AST visitor) {

    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {

    }
}
