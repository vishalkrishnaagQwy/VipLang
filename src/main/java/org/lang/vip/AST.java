package org.lang.vip;

public interface AST {
   void visitMethodDefNode(MethodDefNode methodDefNode);
   void visitBlockNode(BlockNode blockNode);
   void visitMethodCallNode(MethodCallNode methodCallNode);
   void visitIFNode(IFNode ifNode);
   void visitWhileNode(WhileNode whileNode);
   void visitExperNode(ExprNode exprNode);
}
