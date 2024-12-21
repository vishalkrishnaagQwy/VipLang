package org.lang.vip;

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
}
