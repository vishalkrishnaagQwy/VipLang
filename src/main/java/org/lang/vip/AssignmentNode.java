package org.lang.vip;

public class AssignmentNode extends ASTNode {
    ASTNode variable;

    public AssignmentNode(ASTNode variable, ASTNode expr) {
        this.variable = variable;
        this.expr = expr;
    }

    ASTNode expr;
    @Override
    public void accept(AST visitor) {
       visitor.visitAssignmentNode(this);
    }
}
