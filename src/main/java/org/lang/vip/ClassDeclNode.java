package org.lang.vip;

import org.objectweb.asm.MethodVisitor;

public class ClassDeclNode extends ASTNode{
    private String className;
    private int classId;

    public int getClassId(){
        return this.classId;
    }

    public String getClassName() {
        return className;
    }

    public ASTNode getPackage() {
        return pack_age;
    }

    public ASTNode getVersion() {
        return version;
    }

    public ASTNode getClassBody() {
        return classBody;
    }

    private ASTNode pack_age;
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
    public void setPackage(ASTNode pack_age){
        this.pack_age = pack_age;
    }

    public void setClassBody(ASTNode classBody){
        this.classBody = classBody;
    }

    @Override
    public void accept(AST visitor) {
        visitor.visitClassDeclNode(this);
    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {

    }
}
