package org.lang.AstNode;

import org.lang.exceptions.ExceptionOnCodeAnalysis;

public class VipInterfaceDeclNode extends ASTNode {
    private String interfaceName;
    private int interfaceId;
    private PackageDeclNode Package;
    private ASTNode version;
    public ASTNode InterfaceBody;
    private ASTNode Extends;
    private ASTNode Implements;

    public ASTNode getExtends() {
        return Extends;
    }

    public void setExtends(ASTNode anExtends) {
        Extends = anExtends;
    }

    public ASTNode getImplements() {
        return Implements;
    }

    public void setImplements(ASTNode anImplements) {
        Implements = anImplements;
    }


    public VipInterfaceDeclNode(int classId) {
        this.interfaceId = classId;
    }

    public int getClassId(){
        return this.interfaceId;
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

}
