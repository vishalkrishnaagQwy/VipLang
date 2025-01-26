package org.lang.AstNode;

import org.objectweb.asm.MethodVisitor;

import java.util.List;

public class BlockNode extends ASTNode {
    private List<ASTNode> list;
    public List<ASTNode> getList() {
        return list;
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
