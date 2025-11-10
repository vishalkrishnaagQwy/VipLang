package org.lang.AstNode;


public class VersionNode extends ASTNode {
    public String getVersion() {
        return version;
    }

    String version;

    public VersionNode(String version) {
        this.version = version;
    }

    @Override
    public void accept(AST visitor) {
        visitor.visitVersionNode(this);
    }
}
