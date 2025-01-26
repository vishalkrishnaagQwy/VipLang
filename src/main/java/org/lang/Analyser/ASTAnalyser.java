package org.lang.Analyser;

import org.lang.AstNode.*;
import org.lang.exceptions.ExceptionOnCodeAnalysis;
import org.lang.memmory.SymbolTable;
import org.lang.utils.Pair;
import org.lang.AstNode.ForNode;
import org.lang.AstNode.IFNode;
import org.lang.AstNode.ParserExceptionNode;
import org.lang.AstNode.StringLiteralNode;
import org.objectweb.asm.MethodVisitor;

public class ASTAnalyser implements AST {
    private SymbolTable symbolTable;
    private String vipCurrentPackage;
    private String FullPackageName;

    public ASTAnalyser(SymbolTable _symbolTable){
        this.symbolTable = _symbolTable;
    }

    @Override
    public void visitMethodDefNode(MethodDefNode methodDefNode) {

    }

    @Override
    public void visitMethodDefNode(MethodDefNode methodDefNode, MethodVisitor methodVisitor) {

    }

    @Override
    public void visitBlockNode(BlockNode blockNode) {

    }

    @Override
    public void visitBlockNode(BlockNode blockNode, MethodVisitor methodVisitor) {

    }

    @Override
    public void visitMethodCallNode(MethodCallNode methodCallNode, MethodVisitor methodVisitor) {

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
    public void visitBooleanExprNode(BooleanExpr booleanExpr, MethodVisitor methodVisitor) {

    }

    @Override
    public void visitParserExceptionNode(ParserExceptionNode parserExceptionNode) {
        System.out.println("error catched"+parserExceptionNode.errorCode);
    }

    @Override
    public void visitClassDeclNode(ClassDeclNode classDeclNode) throws ExceptionOnCodeAnalysis {
        this.vipCurrentPackage = classDeclNode.getPackage().getCurrentPackage();
        this.FullPackageName = classDeclNode.getPackage().getPackageRoute();
        if(this.FullPackageName.length() ==0)
        {
            throw new ExceptionOnCodeAnalysis("Package route is not correct , please do a recheck");
        } else if (this.vipCurrentPackage .length() == 0) {
            throw new ExceptionOnCodeAnalysis("Package Name is envalid");
        }
        System.out.println("class decl need analysis");
    }

    @Override
    public void visitClassObjectNode(ClassObjectDeclNode objClassNode) {

    }

    @Override
    public void visitClassObjectNode(ClassObjectDeclNode objClassNode, MethodVisitor methodVisitor) {

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
    public void visitStringLiteralNode(StringLiteralNode stringLiteralNode, MethodVisitor methodVisitor) {

    }

    @Override
    public void visitVersionNode(VersionNode versionNode) {

    }

    @Override
    public String visitVariableNode(VariableNode variableNode) {
       return "";
    }

    @Override
    public void visitArithematicExpr(ArithematicExpr arithematicExpr) {

    }

    @Override
    public void visitArithematicExpr(ArithematicExpr arithematicExpr, MethodVisitor methodVisitor) {

    }

    @Override
    public void visitForEachNode(ForEachNode forEachNode) {

    }

    @Override
    public void visitForNode(ForNode forEachNode) {

    }

    @Override
    public Pair<String,NumberNode.Type> visitNumberNode(NumberNode numberNode) {
        return new Pair<>(numberNode.value,numberNode.type);
    }

    @Override
    public void visitNumberNode(NumberNode numberNode, MethodVisitor methodVisitor) {

    }

    @Override
    public void visitAssignmentNode(AssignmentNode assignmentNode) {

    }

    @Override
    public void visitVarDeclNode(VarDeclNode varDeclNode) {

    }

    @Override
    public void visitVarDeclNode(VarDeclNode varDeclNode, MethodVisitor methodVisitor) {

    }

    @Override
    public void visitVipInterfaceDeclNode(VipInterfaceDeclNode vipInterfaceDeclNode) {

    }

    @Override
    public void visitVipInterfaceDeclNode(VipInterfaceDeclNode vipInterfaceDeclNode, MethodVisitor methodVisitor) {

    }
}
