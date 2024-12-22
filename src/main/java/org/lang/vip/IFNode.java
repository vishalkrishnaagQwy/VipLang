package org.lang.vip;

import org.objectweb.asm.MethodVisitor;

import java.util.List;

public class IFNode extends ASTNode {
    ASTNode condition;
    List<ASTNode> body;

    public IFNode(ASTNode condition, List<ASTNode> body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void accept(AST visitor) {
        visitor.visitIFNode(this);
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {

    }
}
