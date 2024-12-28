package org.lang.vip;

import org.objectweb.asm.MethodVisitor;

import java.util.List;

public class BlockNode extends ASTNode{
    private ASTNode AssignExpr;
    private List<ASTNode> list;
    public List<ASTNode> getList() {
        return list;
    }

    public ASTNode getAssignExpr() {
        return AssignExpr;
    }

    public void setAssignExpr(ASTNode assignExpr) {
        AssignExpr = assignExpr;
    }



    public void setList(List<ASTNode> list) {
        this.list = list;
    }


    public BlockNode(List<ASTNode> expr){

        this.list = expr;
    }

    @Override
    public void accept(AST visitor) {
        visitor.visitBlockNode(this);
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {
        visitor.visitBlockNode(this,methodVisitor);
    }
}
