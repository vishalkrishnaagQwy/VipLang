package org.lang.vip;

import org.lang.Services.DBService;
import org.objectweb.asm.MethodVisitor;

import java.util.List;

public class PackageDeclNode extends ASTNode{
    private final String packageRoute;

    public PackageDeclNode(String folderList) {
        this.packageRoute = folderList;
    }

    public String getCurrentPackage(){
        String [] bucket = this.packageRoute.split("\\.");
        return bucket[bucket.length -1];
    }
    public String getPackageRoute(){
        String [] bucket = this.packageRoute.split("\\.");
        return bucket[bucket.length -1];
    }

    @Override
    public void accept(AST visitor) {
        visitor.visitPackageDeclNode(this);
        DBService.createContext();
        String currentPackage = this.getCurrentPackage();
        DBService.createPackage(currentPackage);
        return currentPackage;
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {

    }
}
