package org.lang.vip;

import org.lang.Services.DBService;
import org.objectweb.asm.MethodVisitor;

import java.util.List;

public class PackageDeclNode extends ASTNode{
    private final String packageRoute;

    public PackageDeclNode(String folderList) {
        this.packageRoute = folderList;
    }

    @Override
    public void accept(AST visitor) {
        visitor.visitPackageDeclNode(this);
        DBService.createContext();
        DBService.createPackage(packageRoute);
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {

    }
}
