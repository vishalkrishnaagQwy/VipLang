package org.lang.vip;

public class MethodCallNode extends ASTNode{
    String methodName;
    ASTNode body;
    public MethodCallNode(String methodName,ASTNode expression){
        this.methodName = methodName;
        this.body = expression;
    }

    @Override
    public void accept(AST visitor) {
       visitor.visitMethodCallNode(this);
        body.accept(visitor);
    }
}
