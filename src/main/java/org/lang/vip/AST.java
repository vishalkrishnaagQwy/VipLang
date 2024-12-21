package org.lang.vip;

public interface AST {
   void visitMethodDefNode(MethodDefNode methodDefNode);
   void visitBlockNode(BlockNode blockNode);
   void visitMethodCallNode(MethodCallNode methodCallNode);
   void visitIFNode(IFNode ifNode);
   void visitWhileNode(WhileNode whileNode);
   void visitExperNode(ExprNode exprNode);
   void visitBooleanExprNode(BooleanExpr booleanExpr);
   void visitParserExceptionNode(ParserExceptionNode parserExceptionNode);
   void visitClassDeclNode(ClassDeclNode classDeclNode);
   void visitInstanceClassNode(InstanceClassNode instanceClassNode);
   void visitObjectDeclNode(ObjectDeclNode objectDeclNode);
   void visitPackageDeclNode(PackageDeclNode packageDeclNode);
   void visitStringLiteralNode(StringLiteralNode stringLiteralNode);
   void visitVersionNode(VersionNode versionNode);
   void visitVariableNode(VariableNode variableNode);
   void visitArithematicExpr(ArithematicExpr arithematicExpr);
   void visitForEachNode(ForEachNode forEachNode);
   void visitForNode(ForNode forEachNode);
}
