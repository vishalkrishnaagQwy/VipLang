package org.lang.vip;

import org.objectweb.asm.MethodVisitor;

public class MethodCallNode extends ASTNode{
    String methodName;
    ASTNode expr;
    public MethodCallNode(String methodName,ASTNode expression){
        this.methodName = methodName;
        this.expr = expression;
    }

    @Override
    public void accept(AST visitor) {
       visitor.visitMethodCallNode(this);
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {
      visitor.visitMethodCallNode(this,methodVisitor);
    }
}
