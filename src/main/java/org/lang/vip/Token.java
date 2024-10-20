package org.lang.vip;

public class Token {

    public enum TokenType {
        ID,
        OPERATOR,
        KEYWORD,
        EOF,
        NUMBER,
        STRING,
        BYTE_STRING,
        FORMATTED_STRING,
        IDENTIFIER,
        INDENT,
        DEDENT,
        ERROR
    }

    public String value;  // The actual string value of the token
    public TokenType type; // The type of the token
    public int line;      // The line number where the token was found

    // Constructor for creating a new token
    public Token(TokenType type, String value, int line) {
        this.type = type;
        this.value = value;
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
