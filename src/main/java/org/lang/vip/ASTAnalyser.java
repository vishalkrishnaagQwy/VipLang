package org.lang.vip;

import org.lang.memmory.SymbolTable;

public class ASTAnalyser implements AST{
    private SymbolTable symbolTable;
    public ASTAnalyser(SymbolTable _symbolTable){
        this.symbolTable = _symbolTable;
    }

    @Override
    public void visitMethodDefNode(MethodDefNode methodDefNode) {

    }

    @Override
    public void visitBlockNode(BlockNode blockNode) {

    }

    @Override
    public void visitMethodCallNode(MethodCallNode methodCallNode) {

    }

    @Override
    public void visitIFNode(IFNode ifNode) {

    }

    @Override
    public void visitWhileNode(WhileNode whileNode) {

    }

    @Override
    public void visitExperNode(ExprNode exprNode) {

    }

    @Override
    public void visitBooleanExprNode(BooleanExpr booleanExpr) {

    }

    @Override
    public void visitParserExceptionNode(ParserExceptionNode parserExceptionNode) {
        System.out.println("error catched"+parserExceptionNode.errorCode);
    }
}
