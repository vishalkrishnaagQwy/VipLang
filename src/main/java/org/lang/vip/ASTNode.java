package org.lang.vip;

import org.lang.exceptions.ExceptionOnCodeAnalysis;
import org.lang.memmory.SymbolTable;
import org.objectweb.asm.MethodVisitor;

public abstract class ASTNode {
    public SymbolTable symbolTable;
    public abstract void accept(AST visitor) throws ExceptionOnCodeAnalysis;
    public abstract void accept(AST visitor, MethodVisitor methodVisitor) throws ExceptionOnCodeAnalysis;
}
