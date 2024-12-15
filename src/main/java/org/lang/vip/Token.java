package org.lang.vip;

public class Token {

    public enum TokenType {
        OPERATOR,
        KEYWORD,
        EOF,
        NUMBER,
        DOUBLE,
        FLOAT,
        STRING,
        BYTE_STRING,
        FORMATTED_STRING,
        IDENTIFIER,
//        INDENT,
//        DEDENT,
        NEW_LINE,
        ERROR
    }

    public String value;  // The actual string value of the token
    public TokenType type; // The type of the token
    public int line;
    public int count;

    // Constructor for creating a new token
    public Token(TokenType type, String value, int line) {
        this.type = type;
        this.value = value;
        this.line = line;
    }

    public Token(TokenType type,int value, int line) {
        this.type = type;
        this.count = value;
        this.line = line;
    }

    public TokenType getType() {
        return type;
    }

    public Token getToken() {
        return this;
    }

    public String getLexme() {
        return (this).value;
    }

    // Returns a string representation of the token
    @Override
    public String toString() {
        return "Token(" + type + ", " + value + ", Line: " + line + ")";
    }
}
