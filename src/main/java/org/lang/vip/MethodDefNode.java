package org.lang.vip;

import java.util.List;

class MethodDefNode extends ASTNode {
    String functionName;
    List<ASTNode> body;

    public MethodDefNode(String functionName,List<ASTNode> body) {
        this.functionName = functionName;
        this.body = body;
    }

    @Override
    public void accept(AST visitor) {
      visitor.visitMethodDefNode(this);
    }
}