package org.lang.vip;

public class ParserExceptionNode extends ASTNode{
    String message;

    public ParserExceptionNode(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    String errorCode;
    @Override
    public void accept(AST visitor) {

    }
}
