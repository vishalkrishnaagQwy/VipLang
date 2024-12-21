package org.lang.vip;



public class NumberNode extends ASTNode {
  public  enum Type {
        INT,
        DOUBLE,
        FLOAT,
        BOOLEAN
    }
    String value;
    Type type;

    public NumberNode(String value,Type mtype) {
        this.type = mtype;
        this.value = value;
    }

    @Override
    public void accept(AST visitor) {

    }
}
