package org.lang.AstNode;

import org.lang.Services.DBService;

public class PackageDeclNode extends ASTNode {
    private final String packageRoute;

    public PackageDeclNode(String folderList) {
        this.packageRoute = folderList;
    }

    public String getCurrentPackage(){
        String [] bucket = this.packageRoute.split("\\.");
        return bucket[bucket.length -1];
    }
    public String getPackageRoute(){
        return this.packageRoute;
    }

    @Override
    public void accept(AST visitor) {
        visitor.visitPackageDeclNode(this);
//        DBService.createContext();
//        DBService.createPackage(this.getCurrentPackage());
    }

}
