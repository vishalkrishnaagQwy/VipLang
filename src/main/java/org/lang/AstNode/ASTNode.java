package org.lang.AstNode;

import org.lang.exceptions.ExceptionOnCodeAnalysis;
import org.lang.memmory.SymbolTable;

public abstract class ASTNode {
    public SymbolTable symbolTable;
    public abstract void accept(AST visitor) throws ExceptionOnCodeAnalysis;
}
