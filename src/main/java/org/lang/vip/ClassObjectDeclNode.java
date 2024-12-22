package org.lang.vip;

import org.objectweb.asm.MethodVisitor;

public class ClassObjectDeclNode extends ASTNode{
  private   String className;

    public ASTNode getParams() {
        return params;
    }

    public String getClassName() {
        return className;
    }

    private   ASTNode params;

    public ClassObjectDeclNode(String identifier, ASTNode astNode) {
        this.className = identifier;
        this.params = astNode;
    }

    void setClassName(String className)
    {
        this.className = className;
    }

    void setParams(ASTNode params){
        this.params = params;
    }

    @Override
    public void accept(AST visitor) {
        visitor.visitClassObjectNode(this);
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {
        visitor.visitClassObjectNode(this,methodVisitor);
    }
}