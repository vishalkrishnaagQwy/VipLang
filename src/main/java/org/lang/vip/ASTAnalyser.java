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

    @Override
    public void visitClassDeclNode(ClassDeclNode classDeclNode) {
        System.out.println("class decl need analysis");
    }

    @Override
    public void visitInstanceClassNode(InstanceClassNode instanceClassNode) {

    }

    @Override
    public void visitObjectDeclNode(ObjectDeclNode objectDeclNode) {

    }

    @Override
    public void visitPackageDeclNode(PackageDeclNode packageDeclNode) {

    }

    @Override
    public void visitStringLiteralNode(StringLiteralNode stringLiteralNode) {

    }

    @Override
    public void visitVersionNode(VersionNode versionNode) {

    }

    @Override
    public void visitVariableNode(VariableNode variableNode) {

    }
}
