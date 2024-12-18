package org.lang.vip;

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
}
