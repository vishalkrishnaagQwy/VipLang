package org.lang.vip;

import java.util.List;

public class PackageDeclNode extends ASTNode{
    String packageRoute;
    public PackageDeclNode(String folderList) {
        this.packageRoute = folderList;
    }

    @Override
    public void accept(AST visitor) {
        visitor.visitPackageDeclNode(this);
    }
}
