package org.lang.AstNode;

import org.lang.exceptions.ExceptionOnCodeAnalysis;
import org.lang.Lexer.Token;
import org.objectweb.asm.MethodVisitor;

import java.util.List;

public class FromNode extends ASTNode {
    List<Token> inheritedList;
    public List<Token> getInheritedList() {
        return inheritedList;
    }

    public FromNode(List<Token> implimentationList) {
        inheritedList = implimentationList;
    }

    public void setInheritedList(List<Token> inheritedList) {
        this.inheritedList = inheritedList;
    }

    @Override
    public void accept(AST visitor) throws ExceptionOnCodeAnalysis {

    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) throws ExceptionOnCodeAnalysis {

    }
}
