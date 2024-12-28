package org.lang.vip;

import org.objectweb.asm.MethodVisitor;

public class DefParams extends ASTNode{

    public ASTNode getValue() {
        return value;
    }

    public void setValue(ASTNode value) {
        this.value = value;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    private ASTNode value;
    private String param;



    public DefParams() {
    }

    @Override
    public void accept(AST visitor) {

    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {

    }
}
