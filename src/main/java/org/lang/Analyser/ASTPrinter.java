package org.lang.Analyser;

import org.lang.AstNode.*;
import org.lang.utils.Pair;
import org.lang.AstNode.ForNode;
import org.lang.AstNode.IFNode;
import org.lang.AstNode.ParserExceptionNode;
import org.lang.AstNode.StringLiteralNode;
import org.objectweb.asm.MethodVisitor;

public class ASTPrinter implements AST {
    @Override
    public void visitMethodDefNode(MethodDefNode methodDefNode) {
        System.out.println("--> Reached Method Declaration");
    }

    @Override
    public void visitMethodDefNode(MethodDefNode methodDefNode, MethodVisitor methodVisitor) {

    }

    @Override
    public void visitBlockNode(BlockNode blockNode) {
        System.out.println("--> Reached statements : "+ blockNode.getList().size());
    }

    @Override
    public void visitBlockNode(BlockNode blockNode, MethodVisitor methodVisitor) {

    }

    @Override
    public void visitMethodCallNode(MethodCallNode methodCallNode, MethodVisitor methodVisitor) {

    }

    @Override
    public void visitMethodCallNode(MethodCallNode methodCallNode) {
        System.out.println("--> Reached Method Call");
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
    public void visitBooleanExprNode(BooleanExpr booleanExpr, MethodVisitor methodVisitor) {

    }

    @Override
    public void visitParserExceptionNode(ParserExceptionNode parserExceptionNode) {
        System.out.println("error catched"+parserExceptionNode.errorCode);
    }

    @Override
    public void visitClassDeclNode(ClassDeclNode classDeclNode) {
        System.out.println("---> Reached class Declaration");
    }

    @Override
    public void visitClassObjectNode(ClassObjectDeclNode objClassNode) {

    }

    @Override
    public void visitClassObjectNode(ClassObjectDeclNode objClassNode, MethodVisitor methodVisitor) {

    }


    @Override
    public void visitObjectDeclNode(ObjectDeclNode objectDeclNode) {
        System.out.println("---> Reached object declaration Node");
    }

    @Override
    public void visitPackageDeclNode(PackageDeclNode packageDeclNode) {
        System.out.println("---> Reached package declaration Node");
    }

    @Override
    public void visitStringLiteralNode(StringLiteralNode stringLiteralNode) {
        System.out.println("---> Reached String literal node");
    }

    @Override
    public void visitStringLiteralNode(StringLiteralNode stringLiteralNode, MethodVisitor methodVisitor) {

    }

    @Override
    public void visitVersionNode(VersionNode versionNode) {
        System.out.println("---> Reached version node");
    }

    @Override
    public String visitVariableNode(VariableNode variableNode) {
        System.out.println("[var] "+ variableNode.getName());
        return variableNode.getName();
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
        System.out.println("val : "+ numberNode.getValue() +"  type : "+ numberNode.getType());
        return new Pair<>(numberNode.getValue(), numberNode.getType());
    }

    @Override
    public void visitNumberNode(NumberNode numberNode, MethodVisitor methodVisitor) {
        System.out.println("[number("+ numberNode.getValue() +")]");
    }

    @Override
    public void visitAssignmentNode(AssignmentNode assignmentNode) {
        System.out.println("reached assignment node");
    }

    @Override
    public void visitVarDeclNode(VarDeclNode varDeclNode) {

    }

    @Override
    public void visitVarDeclNode(VarDeclNode varDeclNode, MethodVisitor methodVisitor) {

    }

    @Override
    public void visitVipInterfaceDeclNode(VipInterfaceDeclNode vipInterfaceDeclNode) {
        System.out.println("----> Reached Interface Declaration");
    }

    @Override
    public void visitVipInterfaceDeclNode(VipInterfaceDeclNode vipInterfaceDeclNode, MethodVisitor methodVisitor) {

    }
}
