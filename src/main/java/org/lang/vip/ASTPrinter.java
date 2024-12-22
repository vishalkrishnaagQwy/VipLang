package org.lang.vip;

import org.lang.utils.Pair;
import org.objectweb.asm.MethodVisitor;

public class ASTPrinter implements AST{
    @Override
    public void visitMethodDefNode(MethodDefNode methodDefNode) {
        System.out.println("--> Reached Method Declaration");
        methodDefNode.body.forEach(statement->statement.accept(this));
    }

    @Override
    public void visitMethodDefNode(MethodDefNode methodDefNode, MethodVisitor methodVisitor) {

    }

    @Override
    public void visitBlockNode(BlockNode blockNode) {
        System.out.println("--> Reached statements : "+ blockNode.list.size());
        blockNode.list.forEach(body->body.accept(this));
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
        methodCallNode.expr.accept(this);
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
        classDeclNode.getPackage().accept(this);
        classDeclNode.getVersion().accept(this);
        classDeclNode.classBody.accept(this);
    }

    @Override
    public void visitInstanceClassNode(InstanceClassNode instanceClassNode) {
        System.out.println("---> Reached class instantiation");
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
        System.out.println("[var] "+variableNode.name);
        return variableNode.name;
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
        System.out.println("val : "+numberNode.value+"  type : "+numberNode.type);
        return new Pair<>(numberNode.value,numberNode.type);
    }

    @Override
    public void visitAssignmentNode(AssignmentNode assignmentNode) {
        System.out.println("reached assignment node");
    }
}
