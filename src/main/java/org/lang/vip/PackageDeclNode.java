package org.lang.vip;

import java.util.List;

public class PackageDeclNode extends ASTNode{
    List<String> packageRoute;
    public PackageDeclNode(List<String> folderList) {
        this.packageRoute = folderList;
    }

    @Override
    public void accept(AST visitor) {

    }
}
