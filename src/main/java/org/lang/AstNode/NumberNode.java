package org.lang.AstNode;




public class NumberNode extends ASTNode {
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public  enum Type {
        INT,
        DOUBLE,
        FLOAT,
        BOOLEAN
    }
    private String value;
    private Type type;

    public NumberNode(String value,Type mtype) {
        this.type = mtype;
        this.value = value;
    }

    @Override
    public void accept(AST visitor) {

    }

}
