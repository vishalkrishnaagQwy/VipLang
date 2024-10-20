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
    String className="";

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.getNextToken(); // Initialize the current token
        parseProgram();
    }

    // Advance to the next token
    private void consume(Token.TokenType expectedType) {
        System.out.println("token.lexme : "+currentToken.getLexme());
        if (currentToken.getType() == expectedType) {
            currentToken = lexer.getNextToken();
        } else {
            throw new RuntimeException("Unexpected token: " + currentToken);
        }
    }
    private void consumeSilent(Token.TokenType expectedType) {
        System.out.println("token.lexme : "+currentToken.getLexme());
        if (currentToken.getType() == expectedType) {
            currentToken = lexer.getNextToken();
        }
    }

    private boolean match(String lexeme){
        return (currentToken.getLexme().equals(lexeme));
    }

    private void consume(Token.TokenType expectedType,ParsingType parsingType)
    {
        System.out.println("token.lexme : "+currentToken.getLexme());
        if (currentToken.getType() == expectedType && currentToken.getLexme().equals(parsingType.getValue())) {
            currentToken = lexer.getNextToken();
        } else {
            throw new RuntimeException("Unexpected token: " + currentToken);
        }
    }

    // Parse a program consisting of statements
    public void parseProgram() {
        while (currentToken.getType() != Token.TokenType.EOF) {
            detectClass();
            parseClass();
        }
    }

    private void detectClass() {
        consume(Token.TokenType.KEYWORD,ParsingType.CLASS);
        String vipClasName = currentToken.getLexme();
        consume(Token.TokenType.IDENTIFIER);
        this.className = vipClasName;
    }

    public void parseClass(){
          switch (currentToken.getType())
          {
              case KEYWORD:
                  if (match(ParsingType.DEF.getValue())) {
                      parseMethodDefinition();
                  } else {
                      // Handle other keywords like if, for, etc.
                      throw new RuntimeException("Illegal codes inside class body of '"+className+"' .vp");
                  }
                  break;
              case IDENTIFIER:

                  // assignment a = 20 or a , b = 10 , 20
                  break;
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

                }
                else if (match(ParsingType.WHILE.getValue())) {

                }
                else {
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
        consume(Token.TokenType.KEYWORD); // Consume 'def'
        String functionName = currentToken.getLexme();
        consume(Token.TokenType.IDENTIFIER);
        consume(Token.TokenType.OPERATOR,ParsingType.L_PARENTHESIS); // Expect '('
        // Optionally parse parameters
        consume(Token.TokenType.OPERATOR,ParsingType.R_PARENTHESIS); // Expect ')'
        // Parse method body
        parseStatement();
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

