package org.lang.vip;

public class VersionNode extends ASTNode{
    String version;

    public VersionNode(String version) {
        this.version = version;
    }

    @Override
    public void accept(AST visitor) {
        visitor.visitVersionNode(this);
    }
}
