package org.lang.vip;

public class ASTPrinter implements AST{
    @Override
    public void visitMethodDefNode(MethodDefNode methodDefNode) {
        System.out.println("--> Reached Method Declaration");
    }

    @Override
    public void visitBlockNode(BlockNode blockNode) {
        System.out.println("--> Reached statements");
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
}
