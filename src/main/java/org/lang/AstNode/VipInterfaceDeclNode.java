package org.lang.AstNode;

import org.lang.exceptions.ExceptionOnCodeAnalysis;
import org.objectweb.asm.MethodVisitor;

public class VipInterfaceDeclNode extends ASTNode {
    private int InterfaceId;
    private String interfaceName;
    private PackageDeclNode Package;
    private ASTNode version;
    public ASTNode InterfaceBody;

    public ASTNode getFrom() {
        return From;
    }

    public void setFrom(ASTNode from) {
        From = from;
    }

    private ASTNode From;

    public VipInterfaceDeclNode(int classId) {
        this.InterfaceId = classId;
    }

    public int getClassId(){
        return this.InterfaceId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public PackageDeclNode getPackage() {
        return Package;
    }

    public ASTNode getVersion() {
        return version;
    }

    public ASTNode getInterfaceBody() {
        return this.InterfaceBody;
    }



    public void setInterfaceName(String mInterfaceName){
        this.interfaceName = mInterfaceName;
    }

    public void setVersion(ASTNode version){
        this.version = version;
    }
    public void setPackage(PackageDeclNode vip_package){
        this.Package = vip_package;
    }

    public void setInterfaceBody(ASTNode mInterfaceBody){
        this.InterfaceBody = mInterfaceBody;
    }
    @Override
    public void accept(AST visitor) throws ExceptionOnCodeAnalysis {
        visitor.visitVipInterfaceDeclNode(this);
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) throws ExceptionOnCodeAnalysis {
        visitor.visitVipInterfaceDeclNode(this,methodVisitor);
    }
}
