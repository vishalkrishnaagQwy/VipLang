package org.lang.AstNode;

import org.lang.exceptions.ExceptionOnCodeAnalysis;

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
}
