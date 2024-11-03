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
    ASTNode astNode;
    boolean eof_reached = false;

    public Parser(Lexer lexer) throws VipCompilerException {
        this.lexer = lexer;
        this.LineTokens = lexer.getNextLine();
        getNextToken();// Initialize the current token
        astNode = parseProgram();
    }

    ASTNode getParseTree() {
        return this.astNode;
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

    private void consumeSilent(Token.TokenType expectedType, String expected) {
        System.out.println("silent = " + currentToken);
        if (currentToken.getType() == expectedType && currentToken.getLexme().equals(expected)) {
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
        return (readIndex > LineTokens.size());
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
    public ASTNode parseProgram() throws VipCompilerException {
         ClassDeclNode classDeclNode=new ClassDeclNode();
        classDeclNode.setPackage(parsePackage());
        classDeclNode.setVersion(parseVersion());
        ASTNode classIn=null;
        consume(Token.TokenType.KEYWORD, ParsingType.CLASS);
        String vipClasName = currentToken.getLexme();
        consume(Token.TokenType.IDENTIFIER);
        this.className = vipClasName;
        classDeclNode.setClassName(this.className);
        consume(Token.TokenType.INDENT);
        classDeclNode.setClassBody(parseClassBody());
        consumeSilent(Token.TokenType.DEDENT);
        return classDeclNode;
    }

    private ASTNode parsePackage() throws VipCompilerException {
        consume(Token.TokenType.KEYWORD, "package");
        ASTNode pack = new PackageDeclNode(parseChainString());
        consumeSilent(Token.TokenType.DEDENT);
        return  pack;
    }

    private ASTNode parseVersion() throws VipCompilerException {
        consume(Token.TokenType.KEYWORD, "version");
        String version = currentToken.getLexme();
        consume(Token.TokenType.STRING);
        consumeSilent(Token.TokenType.DEDENT);
        return new VesionNode(version);
    }

    private ASTNode parseClassBody() throws VipCompilerException {
        List<ASTNode> astNodes = new ArrayList<>();
        while (LineTokens != null || eof_reached) {
            switch (currentToken.getType()) {
                case KEYWORD:
                    if (match(ParsingType.DEF.getValue())) {
                       astNodes.add(parseMethodDefinition());
                    } else {
                        // Handle other keywords like if, for, etc.
                        throw new RuntimeException("Illegal codes inside class body of '" + className + "' .vp");
                    }
                    break;
                case IDENTIFIER:
                    astNodes.add(parseAssignment());
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
        return new BlockNode(astNodes);
    }

    // Parse a single statement
    private ASTNode parseStatement() throws VipCompilerException {
        List<ASTNode> astList = new ArrayList<>();
        switch (currentToken.getType()) {
            case IDENTIFIER:
                if (nextTokenIs(Token.TokenType.IDENTIFIER)) {

                } else if (nextTokenIs(Token.TokenType.OPERATOR, "(")) {

                } else if (nextTokenIs(Token.TokenType.OPERATOR, "[")) {

                }
                else if (nextTokenIs(Token.TokenType.IDENTIFIER)) {
                 return parseObjectCreation();
                }
               return parseAssignment();

            case KEYWORD:
                if (match("if")) {
                    astList.add(this.parseIfStatement());
                } else if (match("while")) {
                    astList.add(this.parseWhileStatement());
                } else if (match("for")) {
                    astList.add(this.parseForStatement());
                } else {
                    // Handle other keywords like if, for, etc.
                }
                break;
            default:
                throw new RuntimeException("Unexpected token in statement: " + currentToken);
        }
        return new BlockNode(astList);
    }

    private ASTNode parseObjectCreation() {
       String objectName= currentToken.getLexme();

        return null;
    }

    private boolean nextTokenIs(Token.TokenType tokenType, String s) {
        if (nextToken() != null) {
            return nextToken().getType() == tokenType && nextToken().getLexme().equals(s);
        }
        return false;
    }

    private boolean nextTokenIs(Token.TokenType tokenType) {
        if (nextToken() != null) {
            return nextToken().getType() == tokenType;
        }
        return false;
    }

    private boolean nextTokenIs(String lexme) {
        if (nextToken() != null) {
            return nextToken().getLexme().equals(lexme);
        }
        return false;
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
    private ASTNode parseAssignment() throws VipCompilerException {
        String identifier = currentToken.getLexme();
        ASTNode variable =new VariableNode(identifier);
        consume(Token.TokenType.IDENTIFIER);
        consume(Token.TokenType.OPERATOR, "="); // Expect '='
        ASTNode expr = parseExpression();
        return new AssignmentNode(variable,expr);
    }


    // Parse a function definition
    private ASTNode parseMethodDefinition() throws VipCompilerException {
        consume(Token.TokenType.KEYWORD, ParsingType.DEF); // Consume 'def'
        String functionName = currentToken.getLexme();
        consume(Token.TokenType.IDENTIFIER);
        consume(Token.TokenType.OPERATOR, ParsingType.L_PARENTHESIS);
        // Optionally parse parameters
        consume(Token.TokenType.OPERATOR, ParsingType.R_PARENTHESIS);
        // Parse method body
        ASTNode statements = parseStatement();
        consumeSilent(Token.TokenType.DEDENT);
        MethodDefNode methodDefNode = new MethodDefNode(functionName, statements);
        return methodDefNode;
    }

    private ASTNode parseExpression() throws VipCompilerException {
        if (isEol()) {
            return null;
        }


        ASTNode left = parseTerminal();
        List<ASTNode> list = new ArrayList<>();
        String operator = "";
        while (isArithematicOperator(currentToken) || isLogicalOperator(currentToken)) {
            if (isArithematicOperator(currentToken)) {
                List<ASTNode> right = new ArrayList<>();
                operator = currentToken.getLexme();
                nextToken();
                if (match("(") || match(")")) {
                    return null;
                }
                right.add(parseTerminal());
                list.add(new ArithematicExpr(operator, left, right));
                left = null;
            }
            if (isLogicalOperator(currentToken)) {
                List<ASTNode> right = new ArrayList<>();
                operator = currentToken.getLexme();
                nextToken();
                if (match("(") || match(")")) {
                    return null;
                }
                right.add(parseTerminal());
                list.add(new BooleanExpr(operator, left, right));
                left = null;
            }

        }

        return new BlockNode(list);
    }

    // like a.b.c.d string means this type of combination not the actual string
    private List<String> parseChainString() throws VipCompilerException {
        List<String> tokenList = new ArrayList<>(100);

        tokenList.add(currentToken.getLexme());
        consume(Token.TokenType.IDENTIFIER);
        if (match(".")) {

            while (((match(".") || match(Token.TokenType.IDENTIFIER))) && !isEol()) {
                if (currentToken.getType() == Token.TokenType.IDENTIFIER) {
                    tokenList.add(currentToken.getLexme());
                    getNextToken();
                }

                if (match(".")) {
                    getNextToken();
                }


            }

        } else {
            throw new VipCompilerException("Expected . in a.b format");
        }
        return tokenList;
    }

    private void parseChainMethods() throws VipCompilerException {
        List<String> tokenList = new ArrayList<>(100);
        String output;
        tokenList.add(currentToken.getLexme());
        getNextToken();
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

    private ASTNode compileChainMethod() throws VipCompilerException {
        String output = currentToken.getLexme() + ".";
        String offset1 = currentToken.getLexme();
        consume(Token.TokenType.IDENTIFIER);
        consume(Token.TokenType.OPERATOR, ".");
        output += currentToken.getLexme();
        String offset2 = currentToken.getLexme();
        consume(Token.TokenType.IDENTIFIER);
        if (currentToken.getLexme().equals(".") && nextTokenIs(Token.TokenType.IDENTIFIER)) {
            throw new VipCompilerException("chain method exception '" + output + ".");
        }
       return compileExpressionList(true);
    }


    private ASTNode compileExpressionList(boolean strict) throws VipCompilerException {
        List<ASTNode> exprNode = new ArrayList<>();
        if (nextTokenIs("(")) {
            consume(Token.TokenType.OPERATOR);// eat left parenthesis
            consume(Token.TokenType.OPERATOR);// eat right parenthesis
            return null;
        }
        if (strict) {
            consume(Token.TokenType.OPERATOR, "(");
        }


        while (readIndex < LineTokens.size()) {
            if (match("(") || match(")")) {
                break;
            }

            exprNode.add(this.parseExpression());
            consume(Token.TokenType.OPERATOR, ".");
        }
        if (strict) {
            consume(Token.TokenType.OPERATOR, ")");
        }
        return new BlockNode(exprNode);
    }

    private ASTNode parseTerminal() throws VipCompilerException {
        switch (currentToken.getType()) {
            case IDENTIFIER:
                if (nextTokenIs(Token.TokenType.OPERATOR)) {
                    if (nextTokenIs("(")) {
                    return  this.parseMethodCall();
                    } else if (nextTokenIs(".")) {
                        return  this.compileChainMethod();
                    } else if (nextTokenIs("=")) {
                        return   parseAssignment();
                    }

                    break;
                } else {
                    getNextToken();
                    break;
                }

            case STRING:
                getNextToken();
                break;
            case KEYWORD:
                break;
            case OPERATOR:
                this.compileExpressionList(true);

                if (nextTokenIs("=")) {
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
                    return null;
                }
                throw new VipCompilerException("unknown token " + currentToken);
        }
        return null;
    }

    private ASTNode parseMethodCall() throws VipCompilerException {
        String methodCall = currentToken.getLexme();
        consume(Token.TokenType.IDENTIFIER);
        consumeSilent(Token.TokenType.OPERATOR, "(");
        ASTNode expression = this.parseExpression();
        consumeSilent(Token.TokenType.OPERATOR, ")");
        return new MethodCallNode(methodCall, expression);
    }

    private boolean isArithematicOperator(Token token) throws VipCompilerException {
        switch (token.getLexme()) {
            case "+", "-", "/", "=", "*" -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    private boolean isLogicalOperator(Token token) throws VipCompilerException {
        switch (token.getLexme()) {
            case "and", "or", "equals", "less than", "less than or equal",
                 "greater than or equal", "GREATER_THAN", "not", "not equal" -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }
}

