package org.lang.vip;

public class BlockNode extends ASTNode{
    public BlockNode(){
        System.out.println("blockNode ...");
    }

    @Override
    public void accept(AST visitor) {
        visitor.visitBlockNode(this);
    }
}
