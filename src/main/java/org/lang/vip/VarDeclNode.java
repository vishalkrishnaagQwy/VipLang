package org.lang.vip;

import org.objectweb.asm.MethodVisitor;

import java.util.List;

public class VarDeclNode extends ASTNode{
   private String VariableName;
   private ASTNode expr;

    public ASTNode getExpr() {
        return expr;
    }

    public void setExpr(ASTNode expr) {
        this.expr = expr;
    }

    public List<HintType> getHints() {
        return hints;
    }

    public void setHints(List<HintType> hints) {
        this.hints = hints;
    }

    public String getVariableName() {
        return VariableName;
    }

    public void setVariableName(String variableName) {
        VariableName = variableName;
    }

    List<HintType> hints;
    @Override
    public void accept(AST visitor) {
        visitor.visitVarDeclNode(this);
    }

    public VarDeclNode(String currentId) {
        this.VariableName = currentId;
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {
        visitor.visitVarDeclNode(this,methodVisitor);
    }
}
