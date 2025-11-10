package org.lang.AstNode;

import org.lang.exceptions.ExceptionOnCodeAnalysis;
import org.lang.Lexer.Token;

import java.util.List;

public class ImplementsNode extends ASTNode {
    List<Token> ImplimentationList;
    public List<Token> getImplimentationList() {
        return ImplimentationList;
    }

    public ImplementsNode(List<Token> implimentationList) {
        ImplimentationList = implimentationList;
    }

    public void setImplimentationList(List<Token> implimentationList) {
        ImplimentationList = implimentationList;
    }

    @Override
    public void accept(AST visitor) throws ExceptionOnCodeAnalysis {

    }
}
