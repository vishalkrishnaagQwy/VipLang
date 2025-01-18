package org.lang.vip;

import org.lang.exceptions.ExceptionOnCodeAnalysis;
import org.objectweb.asm.MethodVisitor;

import java.util.List;

public class ExtendsNode extends ASTNode{
    private List<Token> ExtendedClasses;
    public List<Token> getExtendedClasses() {
        return ExtendedClasses;
    }

    public ExtendsNode(List<Token> extendedClasses) {
        ExtendedClasses = extendedClasses;
    }

    public void setExtendedClasses(List<Token> extendedClasses) {
        ExtendedClasses = extendedClasses;
    }

    @Override
    public void accept(AST visitor) throws ExceptionOnCodeAnalysis {

    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) throws ExceptionOnCodeAnalysis {

    }
}
