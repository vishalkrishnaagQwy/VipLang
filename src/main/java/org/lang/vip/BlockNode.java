package org.lang.vip;

import org.objectweb.asm.MethodVisitor;

import java.util.List;

public class BlockNode extends ASTNode{
    List<ASTNode> list;
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
