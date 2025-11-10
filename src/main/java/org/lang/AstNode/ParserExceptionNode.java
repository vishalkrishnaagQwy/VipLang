package org.lang.AstNode;


public class ParserExceptionNode extends ASTNode {
    String message;

    public ParserExceptionNode(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String errorCode;
    @Override
    public void accept(AST visitor) {
       visitor.visitParserExceptionNode(this);
    }

}
