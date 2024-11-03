package org.lang.vip;

public class ClassDeclNode extends ASTNode{
    private String className;
   private ASTNode pack_age;
   private ASTNode version;
   public ASTNode classBody;
    public ClassDeclNode() {

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

    }
}
