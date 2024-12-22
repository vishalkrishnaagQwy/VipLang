package org.lang.vip;

import org.objectweb.asm.MethodVisitor;

public class StringLiteralNode extends ASTNode {
    String value;
    /*
    * string literal which means "<data>"
    *
    * */

    public StringLiteralNode(String value) {
        this.value = value;
    }

    @Override
    public void accept(AST visitor) {
      visitor.visitStringLiteralNode(this);
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {
        visitor.visitStringLiteralNode(this,methodVisitor);
    }
}
