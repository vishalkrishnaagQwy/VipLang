package org.lang.vip;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Parser {
    private Lexer lexer;
    private Token currentToken;
    private List<Token> LineTokens;
    String className = "";
    int readIndex = 0;
    boolean eof_reached = false;
    List<ASTNode> node;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.LineTokens = lexer.getNextLine();
        getNextToken();// Initialize the current token
        parseProgram();
    }

    List<ASTNode> getParseTree(){
        return this.node;
    }

    // Advance to the next token
    private void consume(Token.TokenType expectedType) {
        System.out.println(currentToken);
        if (currentToken.getType() == expectedType) {
            getNextToken();
        } else {
            throw new RuntimeException("Unexpected token: " + currentToken);
        }
    }

    private void getNextToken() {
        if (readIndex < LineTokens.size() && !eof_reached) {
            currentToken = LineTokens.get(readIndex);
        } else {
            LineTokens = lexer.getNextLine();
            if (LineTokens != null && LineTokens.size() != 0) {
                readIndex = 0;
                currentToken = LineTokens.getFirst();
            } else {
                if (LineTokens != null) {
                    eof_reached = true;
                }

            }
        }
        readIndex++;

    }

    private void consumeSilent(Token.TokenType expectedType) {
        System.out.println("silent = " + currentToken);
        if (currentToken.getType() == expectedType) {
            getNextToken();
        }
    }

    private boolean match(String lexeme) {
        return (currentToken.getLexme().equals(lexeme));
    }

    private void consume(Token.TokenType expectedType, ParsingType parsingType) {
        System.out.println(currentToken + "parsing type : " + parsingType);
        if (currentToken.getType() == expectedType && currentToken.getLexme().equals(parsingType.getValue())) {
            getNextToken();
        } else {
            throw new RuntimeException("Unexpected token: " + currentToken + " expected " + expectedType);
        }
    }

    // Parse a program consisting of statements
    public void parseProgram() {
        parseClass();
    }


    public void parseClass() {
        consume(Token.TokenType.KEYWORD, ParsingType.CLASS);
        String vipClasName = currentToken.getLexme();
        consume(Token.TokenType.IDENTIFIER);
        this.className = vipClasName;
        consume(Token.TokenType.INDENT);
        parseClassBody();
        consumeSilent(Token.TokenType.DEDENT);
    }

    private void parseClassBody() {
        while (LineTokens != null || eof_reached) {
            switch (currentToken.getType()) {
                case KEYWORD:
                    if (match(ParsingType.DEF.getValue())) {
                        parseMethodDefinition();
                    } else {
                        // Handle other keywords like if, for, etc.
                        throw new RuntimeException("Illegal codes inside class body of '" + className + "' .vp");
                    }
                    break;
                case IDENTIFIER:
                    parseAssignment();
                    // assignment a = 20 or a , b = 10 , 20
                    break;
                case INDENT:
                    consume(Token.TokenType.INDENT);
                    break;
                case DEDENT:
                    consume(Token.TokenType.DEDENT);
                    break;
                default:
                    System.out.println("jinki jink jin ji");
                    break;
            }
        }

    }

    // Parse a single statement
    private void parseStatement() {
        switch (currentToken.getType()) {
            case IDENTIFIER:
                parseAssignment();
                break;
            case KEYWORD:
                if (match(ParsingType.IF.getValue())) {

                } else if (match(ParsingType.WHILE.getValue())) {

                } else {
                    // Handle other keywords like if, for, etc.
                }
                break;
            default:
                throw new RuntimeException("Unexpected token in statement: " + currentToken);
        }
    }

    // Parse an assignment statement
    private void parseAssignment() {
        String identifier = currentToken.getLexme();
        consume(Token.TokenType.IDENTIFIER);
        consume(Token.TokenType.OPERATOR); // Expect '='
        parseExpression();
    }

    // Parse an expression (this can be expanded based on your language's rules)
    private void parseExpression() {
        // Implement your expression parsing logic here
        if (currentToken.getType() == Token.TokenType.NUMBER) {
            consume(Token.TokenType.NUMBER);
        } else if (currentToken.getType() == Token.TokenType.IDENTIFIER) {
            consume(Token.TokenType.IDENTIFIER);
        }
        // Add logic for handling operators, etc.
    }

    // Parse a function definition
    private void parseMethodDefinition() {
        consume(Token.TokenType.KEYWORD, ParsingType.DEF); // Consume 'def'
        String functionName = currentToken.getLexme();
        consume(Token.TokenType.IDENTIFIER);
        consume(Token.TokenType.OPERATOR, ParsingType.L_PARENTHESIS); // Expect '('
        // Optionally parse parameters
        consume(Token.TokenType.OPERATOR, ParsingType.R_PARENTHESIS); // Expect ')'
        // Parse method body
        parseStatement();
        consumeSilent(Token.TokenType.DEDENT);
    }

    //Insert the VM codes into the VM file
    private void vmCodeInput(String vmCodes) {
        try {
            File log = new File(lexer.getFolderPath() + File.separator + className + ".vm");
            FileWriter fileWriter = new FileWriter(log.getAbsoluteFile(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(vmCodes);
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

