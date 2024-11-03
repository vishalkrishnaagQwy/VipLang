package org.lang.vip;

import java.util.List;

public class ObjectDeclNode extends ASTNode{
    String ObjectName;
    List<String> ObjectList;
    String instanceName;

    public ObjectDeclNode(String objectName ,String instanceName) {
        this.ObjectName = objectName;
        this.instanceName = instanceName;
    }

    public ObjectDeclNode(List<String> objectList,String instanceName) {
        this.ObjectList = objectList;
        this.instanceName = instanceName;
    }

    @Override
    public void accept(AST visitor) {

    }
}
