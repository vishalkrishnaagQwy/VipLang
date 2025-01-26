package org.lang.AstNode;

import org.lang.exceptions.ExceptionOnCodeAnalysis;
import org.objectweb.asm.MethodVisitor;

public class ClassDeclNode extends ASTNode {
    private String className;
    private int classId;
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


    public int getClassId(){
        return this.classId;
    }

    public String getClassName() {
        return className;
    }

    public PackageDeclNode getPackage() {
        return Package;
    }

    public ASTNode getVersion() {
        return version;
    }

    public ASTNode getClassBody() {
        return classBody;
    }

    private PackageDeclNode Package;
   private ASTNode version;
   public ASTNode classBody;
    public ClassDeclNode(int ClassId) {
        this.classId = ClassId;
    }

    public void setClassName(String className){
        this.className = className;
    }

    public void setVersion(ASTNode version){
        this.version = version;
    }
    public void setPackage(PackageDeclNode vip_package){
        this.Package = vip_package;
    }

    public void setClassBody(ASTNode classBody){
        this.classBody = classBody;
    }

    @Override
    public void accept(AST visitor) throws ExceptionOnCodeAnalysis {
        visitor.visitClassDeclNode(this);
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {

    }
}
