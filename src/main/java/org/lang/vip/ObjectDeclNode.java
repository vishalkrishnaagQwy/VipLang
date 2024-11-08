package org.lang.vip;

import java.util.List;

public class ObjectDeclNode extends ASTNode{
    String ObjectName;
    List<String> ObjectList;
    String instanceName;
   private ASTNode expr;

    public ObjectDeclNode(String objectName ,String instanceName,ASTNode expr) {
        this.ObjectName = objectName;
        this.instanceName = instanceName;
        this.expr = expr;
    }

    public ObjectDeclNode(List<String> objectList,String instanceName,ASTNode expr) {
        this.ObjectList = objectList;
        this.instanceName = instanceName;
        this.expr = expr;
    }

    @Override
    public void accept(AST visitor) {

    }
}
