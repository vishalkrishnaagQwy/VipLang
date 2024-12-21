package org.lang.vip;

public class ForEachNode {
    public ForEachNode() {
    }
    public void accept(AST visitor) {
        visitor.visitForEachNode(this);
    }
}
