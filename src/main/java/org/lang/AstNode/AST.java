package org.lang.AstNode;

import org.lang.exceptions.ExceptionOnCodeAnalysis;
import org.lang.utils.Pair;

public interface AST {
   void visitMethodDefNode(MethodDefNode methodDefNode);
   void visitBlockNode(BlockNode blockNode);
   void visitMethodCallNode(MethodCallNode methodCallNode);
   void visitIFNode(IFNode ifNode);
   void visitWhileNode(WhileNode whileNode);
   void visitExperNode(ExprNode exprNode);
   void visitBooleanExprNode(BooleanExpr booleanExpr);
   void visitParserExceptionNode(ParserExceptionNode parserExceptionNode);
   void visitClassDeclNode(ClassDeclNode classDeclNode) throws ExceptionOnCodeAnalysis;
   void visitClassObjectNode(ClassObjectDeclNode objClassNode);
   void visitObjectDeclNode(ObjectDeclNode objectDeclNode);
   void visitPackageDeclNode(PackageDeclNode packageDeclNode);
   void visitStringLiteralNode(StringLiteralNode stringLiteralNode);
   void visitVersionNode(VersionNode versionNode);
   String visitVariableNode(VariableNode variableNode);
   void visitArithematicExpr(ArithematicExpr arithematicExpr);
   void visitForEachNode(ForEachNode forEachNode);
   void visitForNode(ForNode forEachNode);
   Pair<String,NumberNode.Type> visitNumberNode(NumberNode numberNode);
   void visitAssignmentNode(AssignmentNode assignmentNode) throws ExceptionOnCodeAnalysis;
   void visitVarDeclNode(VarDeclNode varDeclNode);
   void visitVipInterfaceDeclNode(VipInterfaceDeclNode vipInterfaceDeclNode);
}
