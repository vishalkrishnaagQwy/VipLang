package org.lang.vip;

import org.objectweb.asm.MethodVisitor;

import java.util.List;

public class VarDeclNode extends ASTNode{
   private String VariableName;
   private ASTNode expr;
   ASTNode hints;

    public ASTNode getExpr() {
        return expr;
    }

    public void setExpr(ASTNode expr) {
        this.expr = expr;
    }

    public ASTNode getHints() {
        return hints;
    }

    public void setHints(ASTNode hints) {
        this.hints = hints;
    }

    public String getVariableName() {
        return VariableName;
    }

    public void setVariableName(String variableName) {
        VariableName = variableName;
    }

    @Override
    public void accept(AST visitor) {
        visitor.visitVarDeclNode(this);
    }

    public VarDeclNode() {
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {
        visitor.visitVarDeclNode(this,methodVisitor);
    }
}
