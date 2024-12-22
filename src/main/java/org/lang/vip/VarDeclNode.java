package org.lang.vip;

import org.objectweb.asm.MethodVisitor;

public class VarDeclNode extends ASTNode{
    String VariableName;
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
