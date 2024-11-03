package org.lang.vip;

public class VesionNode extends ASTNode{
    String version;

    public VesionNode(String version) {
        this.version = version;
    }

    @Override
    public void accept(AST visitor) {

    }
}
