package org.lang.AstNode;

import org.objectweb.asm.MethodVisitor;

public class StringLiteralNode extends ASTNode {
   private String value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
