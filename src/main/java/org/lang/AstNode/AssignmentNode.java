package org.lang.AstNode;

import org.lang.exceptions.ExceptionOnCodeAnalysis;

public class AssignmentNode extends ASTNode {
    private ASTNode variable;
    private ASTNode expr;

    public AssignmentNode(ASTNode variable, ASTNode expr) {
        this.variable = variable;
        this.expr = expr;
    }


    @Override
    public void accept(AST visitor) throws ExceptionOnCodeAnalysis {
       visitor.visitAssignmentNode(this);
    }

    public ASTNode getVariable() {
        return variable;
    }

    public void setVariable(ASTNode variable) {
        this.variable = variable;
    }

    public ASTNode getExpr() {
        return expr;
    }

    public void setExpr(ASTNode expr) {
        this.expr = expr;
    }
}
