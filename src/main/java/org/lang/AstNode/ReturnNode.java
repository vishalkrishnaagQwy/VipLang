package org.lang.AstNode;

import org.lang.exceptions.ExceptionOnCodeAnalysis;
import org.objectweb.asm.MethodVisitor;

public class ReturnNode extends ASTNode {
    public ASTNode getExpr() {
        return expr;
    }

    public void setExpr(ASTNode expr) {
        this.expr = expr;
    }

    private ASTNode expr;
    @Override
    public void accept(AST visitor) throws ExceptionOnCodeAnalysis {

    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) throws ExceptionOnCodeAnalysis {

    }
}
