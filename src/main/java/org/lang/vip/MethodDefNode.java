package org.lang.vip;

import org.objectweb.asm.MethodVisitor;

import java.util.List;

public class MethodDefNode extends ASTNode {
    ASTNode params =null;
    String functionName;
    List<ASTNode> body;
    ASTNode hints;

    public MethodDefNode(String functionName,ASTNode paramList,List<ASTNode> body,ASTNode ReturnType) {
        this.functionName = functionName;
        this.params = paramList;
        this.body = body;
        this.hints = ReturnType;
    }

    @Override
    public void accept(AST visitor) {
        visitor.visitMethodDefNode(this);
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {
        visitor.visitMethodDefNode(this,methodVisitor);
    }
}