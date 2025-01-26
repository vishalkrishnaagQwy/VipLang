package org.lang.utils;

public enum ParsingType {
    SYSTEM("system"),
    CLASS("class"),
    IF("if"),
    ELSE("else"),
    WHILE("while"),
    ELIF("elif"),
    L_PARENTHESIS("("),
    R_PARENTHESIS(")"),
    L_BRACE("{"),
    R_BRACE("}"),
    DOT("."),
    DEF("def");


    private final String value;
    

    ParsingType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
