package org.lang.vip;

import org.objectweb.asm.MethodVisitor;

public class InstanceClassNode extends ASTNode{
    String className;
    ASTNode paramsExpr;

    public InstanceClassNode() {
    }
    void setClassName(String className)
    {
        this.className = className;
    }

    void setParams(ASTNode params){
        this.paramsExpr = params;
    }

    @Override
    public void accept(AST visitor) {
        visitor.visitInstanceClassNode(this);
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {

    }
}