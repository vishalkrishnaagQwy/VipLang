package org.lang.vip;

import org.lang.exceptions.VipCompilerException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.System.exit;


public class Parser {
    private final Lexer lexer;
    private Token currentToken;
    private List<Token> LineTokens;
    String className = "";
    int readIndex = 0;
    boolean eof_reached = false;
    List<ASTNode> node;

    public Parser(Lexer lexer) throws VipCompilerException {
        this.lexer = lexer;
        this.LineTokens = lexer.getNextLine();
        getNextToken();// Initialize the current token
        parseProgram();
    }

    List<ASTNode> getParseTree() {
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
            if (LineTokens != null && !LineTokens.isEmpty()) {
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

    private boolean match(Token.TokenType type) {
        return (currentToken.getType() == type);
    }

    private boolean eitherMatch(Token.TokenType type, Token.TokenType type1) {
        return (currentToken.getType() == type || currentToken.getType() == type1);
    }

    private boolean isEol() {
        return (LineTokens.size() == readIndex + 1);
    }

    private Token nextToken() {
        if (readIndex > this.LineTokens.size()) {
            return null;
        }
        return LineTokens.get(readIndex + 1);
    }


    private void consume(Token.TokenType expectedType, ParsingType parsingType) throws VipCompilerException {
        System.out.println(currentToken + "parsing type : " + parsingType);
        if (currentToken.getType() == expectedType && currentToken.getLexme().equals(parsingType.getValue())) {
            getNextToken();
        } else {
            throw new VipCompilerException("Unexpected token: " + currentToken + " expected " + expectedType);
        }
    }

    private void consume(Token.TokenType expectedType, String expected) throws VipCompilerException {
        System.out.println(currentToken + "parsing type : " + expected);
        if (currentToken.getType() == expectedType && currentToken.getLexme().equals(expected)) {
            getNextToken();
        } else {
            throw new VipCompilerException("Unexpected token: " + currentToken + " expected " + expectedType);
        }
    }

    // Parse a program consisting of statements
    public void parseProgram() throws VipCompilerException {
        parseClass();
    }


    public void parseClass() throws VipCompilerException {
        consume(Token.TokenType.KEYWORD, ParsingType.CLASS);
        String vipClasName = currentToken.getLexme();
        consume(Token.TokenType.IDENTIFIER);
        this.className = vipClasName;
        consume(Token.TokenType.INDENT);
        parseClassBody();
        consumeSilent(Token.TokenType.DEDENT);
    }

    private void parseClassBody() throws VipCompilerException {
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
                    System.out.println("something went wrong");
                    exit(-1);
                    break;
            }
        }

    }

    // Parse a single statement
    private ASTNode parseStatement() throws VipCompilerException {
        List<ASTNode> astList=new ArrayList<>();
        switch (currentToken.getType()) {
            case IDENTIFIER:
                parseAssignment();
                break;
            case KEYWORD:
                if (match("if")) {
                   astList.add(this.parseIfStatement());
                } else if (match("while")) {
                   astList.add(this.parseWhileStatement());
                }
                else if (match("for")) {
                    astList.add(this.parseForStatement());
                }
                else {
                    // Handle other keywords like if, for, etc.
                }
                break;
            default:
                throw new RuntimeException("Unexpected token in statement: " + currentToken);
        }
        return new BlockNode();
    }

    private ASTNode parseForStatement() {
        return null;
    }

    private ASTNode parseWhileStatement() {
        return null;
    }

    private ASTNode parseIfStatement() {
        return null;
    }

    private void parseBuiltInClassMethods() {
        consume(Token.TokenType.IDENTIFIER); // system
        if (match(ParsingType.DOT.getValue())) {
            getNextToken();
            String methodName = currentToken.getLexme();
            consume(Token.TokenType.IDENTIFIER);
            consume(Token.TokenType.STRING);
        }
    }


    // Parse an assignment statement
    private void parseAssignment() throws VipCompilerException {
        String identifier = currentToken.getLexme();
        consume(Token.TokenType.IDENTIFIER);
        consume(Token.TokenType.OPERATOR,"="); // Expect '='
        parseExpression();
    }


    // Parse a function definition
    private void parseMethodDefinition() throws VipCompilerException {
        consume(Token.TokenType.KEYWORD, ParsingType.DEF); // Consume 'def'
        String functionName = currentToken.getLexme();
        consume(Token.TokenType.IDENTIFIER);
        consume(Token.TokenType.OPERATOR, ParsingType.L_PARENTHESIS);
        // Optionally parse parameters
        consume(Token.TokenType.OPERATOR, ParsingType.R_PARENTHESIS);
        // Parse method body
        ASTNode statements=parseStatement();
        consumeSilent(Token.TokenType.DEDENT);
        MethodDefNode methodDefNode = new MethodDefNode(functionName,statements);
        node.add(methodDefNode);
    }

    private void parseExpression() throws VipCompilerException {

        if (isEol()) {
            return;
        }

        parseTerminal();
        while (isOperator(currentToken)) {
            nextToken();
            if (match("(") || match(")")) {
                return;
            }
            parseTerminal();
        }

    }

    private void compileChainStatements(String methodName) throws VipCompilerException {
        List<String> tokenList = new ArrayList<>(100);
        String output;
        tokenList.add(currentToken.getLexme());
        nextToken();
        if (match(".")) {

            while (match(".") || currentToken.getType() == Token.TokenType.IDENTIFIER) {
                // container.method(this.somethingFishy);
                //exit on see hear

                if (match(".")) {
                    getNextToken();
                }

                if (currentToken.getType() == Token.TokenType.IDENTIFIER) {
                    tokenList.add(currentToken.getLexme());
                    getNextToken();
                }
            }

            output = convertListToString(tokenList);
            if (match("(")) {
                // a.b.c.d.method call
                this.compileExpressionList(true);
            } else if (match("=")) {
                consume(Token.TokenType.OPERATOR);
                // a = 10 format
                this.parseExpression();
            }

            System.out.println("out : " + output);
        } else {
            throw new VipCompilerException("Expected . in a.b format");
        }
    }

    public static String convertListToString(List<String> strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : strings) {
            stringBuilder.append(string).append(".");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private void compileChainMethod() throws VipCompilerException {
        String output = currentToken.getLexme() + ".";
        String offset1 = currentToken.getLexme();
        consume(Token.TokenType.IDENTIFIER);
        consume(Token.TokenType.OPERATOR, ".");
        output += currentToken.getLexme();
        String offset2 = currentToken.getLexme();
        consume(Token.TokenType.IDENTIFIER);
        if (currentToken.getLexme().equals(".") && nextToken().getType() == Token.TokenType.IDENTIFIER) {
            throw new VipCompilerException("cannot invoke  field '" + output + "." + nextToken().getLexme());
        }
        compileExpressionList(true);
    }


    private void compileExpressionList(boolean strict) throws VipCompilerException {
        if (Objects.requireNonNull(nextToken()).getLexme().equals("(")) {
            consume(Token.TokenType.OPERATOR);// eat left parenthesis
            consume(Token.TokenType.OPERATOR);// eat right parenthesis
            return;
        }
        if (strict) {
            consume(Token.TokenType.OPERATOR, "(");
        }


        while (readIndex < LineTokens.size()) {
            if (match("(") || match(")")) {
                break;
            }

            this.parseExpression();
            consume(Token.TokenType.OPERATOR, ".");
        }
        if (strict) {
            consume(Token.TokenType.OPERATOR, ")");
        }
    }

    private void parseTerminal() throws VipCompilerException {
        switch (currentToken.getType()) {
            case IDENTIFIER:
                if (Objects.requireNonNull(nextToken()).getType() == Token.TokenType.OPERATOR) {
                    if (Objects.requireNonNull(nextToken()).getLexme().equals("(")) {
                        this.parseMethodCall();
                    } else if (Objects.requireNonNull(nextToken()).getLexme().equals(".")) {
                        this.compileChainMethod();
                    } else if (Objects.requireNonNull(nextToken()).getLexme().equals("=")) {
                        parseAssignment();
                    }

                    break;
                } else {
                    getNextToken();
                    break;
                }

            case DATA_TYPE, STRING:
                getNextToken();
                break;
            case KEYWORD:
                break;
            case OPERATOR:
                this.compileExpressionList(true);

                if (Objects.requireNonNull(nextToken()).getLexme().equals("=")) {
                    nextToken();
                    this.parseTerminal();
                } else {
                    getNextToken();
                    this.parseTerminal();
                    break;
                }
                break;
            default:
                if (currentToken.getLexme().equals(")")) {
                    return;
                }
                throw new VipCompilerException("unknown token " + currentToken);
        }
    }

    private void parseMethodCall() {
    }

    private boolean isOperator(Token token) throws VipCompilerException {
        switch (token.getLexme()) {
            case "+", "-", "/", "and", "or", "equals", "less--than", "less--than--or--equal",
                 "greater--than--or--equal", "GREATER_THAN", "=", "*", "not", "not_equal" -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }
}

