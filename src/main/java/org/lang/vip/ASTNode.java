package org.lang.vip;

import org.lang.memmory.SymbolTable;
import org.objectweb.asm.MethodVisitor;

public abstract class ASTNode {
    public SymbolTable symbolTable;
    public abstract void accept(AST visitor);
    public abstract void accept(AST visitor, MethodVisitor methodVisitor);
}
