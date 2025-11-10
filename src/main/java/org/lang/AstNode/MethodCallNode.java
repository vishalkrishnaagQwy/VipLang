package org.lang.AstNode;

import org.lang.exceptions.ExceptionOnCodeAnalysis;

public class MethodCallNode extends ASTNode {
    public String methodName;
    public ASTNode expr;
    public MethodCallNode(String methodName,ASTNode expression){
        this.methodName = methodName;
        this.expr = expression;
    }

    @Override
    public void accept(AST visitor) {
       visitor.visitMethodCallNode(this);
    }

}
