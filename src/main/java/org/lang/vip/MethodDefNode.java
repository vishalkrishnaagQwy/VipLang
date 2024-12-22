package org.lang.vip;

import org.objectweb.asm.MethodVisitor;

import java.util.List;

public class MethodDefNode extends ASTNode {
    String functionName;
    List<ASTNode> body;
    List<HintType> hints;

    public MethodDefNode(String functionName,List<ASTNode> body,List<HintType> hint) {
        this.functionName = functionName;
        this.body = body;
        this.hints = hint;
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