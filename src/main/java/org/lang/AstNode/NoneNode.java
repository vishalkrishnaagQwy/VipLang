package org.lang.AstNode;

import org.objectweb.asm.MethodVisitor;

public class NoneNode extends ASTNode {
    @Override
    public void accept(AST visitor) {

    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {

    }
}
