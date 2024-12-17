package org.lang.vip;

public class ASTPrinter implements AST{
    @Override
    public void visitMethodDefNode(MethodDefNode methodDefNode) {
        System.out.println("--> Reached Method Declaration");
        for(ASTNode statement : methodDefNode.body)
        {
            statement.accept(this);
        }
    }

    @Override
    public void visitBlockNode(BlockNode blockNode) {
        System.out.println("--> Reached statements : "+ blockNode.list.size());
        for(ASTNode body : blockNode.list)
        {
            body.accept(this);
        }
    }

    @Override
    public void visitMethodCallNode(MethodCallNode methodCallNode) {
        System.out.println("--> Reached Method Call");
        methodCallNode.body.accept(this);
    }

    @Override
    public void visitIFNode(IFNode ifNode) {
        System.out.println("--> Reached conditional if ");
    }

    @Override
    public void visitWhileNode(WhileNode whileNode) {
        System.out.println("--> Reached while loop");
    }

    @Override
    public void visitExperNode(ExprNode exprNode) {
        System.out.println("--> Reached Expression");
    }

    @Override
    public void visitBooleanExprNode(BooleanExpr booleanExpr) {
        System.out.println("---> Reached Boolean Expression");
    }

    @Override
    public void visitParserExceptionNode(ParserExceptionNode parserExceptionNode) {
        System.out.println("error catched"+parserExceptionNode.errorCode);
    }

    @Override
    public void visitClassDeclNode(ClassDeclNode classDeclNode) {
        System.out.println("---> Reached class Declaration");
        classDeclNode.classBody.accept(this);
    }
}
