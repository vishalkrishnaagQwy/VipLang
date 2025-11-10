package org.lang.AstNode;


public class ObjectDeclNode extends ASTNode {
    String ObjectName;

   private ASTNode expr;

    public ObjectDeclNode(String objectName,ASTNode expr) {
        this.ObjectName = objectName;
        this.expr = expr;
    }

    @Override
    public void accept(AST visitor) {
     visitor.visitObjectDeclNode(this);
    }
}
