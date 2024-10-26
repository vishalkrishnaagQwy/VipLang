package org.lang.vip;

import org.lang.memmory.SymbolTable;

public abstract class ASTNode {
    public SymbolTable symbolTable;
    public abstract void accept(AST visitor);
}
