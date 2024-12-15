package org.lang.vip;

import org.lang.exceptions.VipCompilerException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;


public class ParserCopy {
    private final Lexer lexer;
    private Token currentToken;
    private List<Token> LineTokens;
    String className = "";
    int readIndex = 0;
    ASTNode astNode;
    boolean eof_reached = false;

    public ParserCopy(Lexer lexer) throws VipCompilerException {
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
                currentToken = LineTokens.get(0);
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
        ClassDeclNode classDeclNode = new ClassDeclNode();
        classDeclNode.setPackage(parsePackage());
        classDeclNode.setVersion(parseVersion());
        ASTNode classIn = null;
        consume(Token.TokenType.KEYWORD, ParsingType.CLASS);
        String vipClasName = currentToken.getLexme();
        consume(Token.TokenType.IDENTIFIER);
        this.className = vipClasName;
        classDeclNode.setClassName(this.className);
        classDeclNode.setClassBody(parseClassBody());
        return classDeclNode;
    }

    private ASTNode parsePackage() throws VipCompilerException {
        consume(Token.TokenType.KEYWORD, "package");
        ASTNode pack = new PackageDeclNode(parseChainString(false));
        return pack;
    }

    private ASTNode parseVersion() throws VipCompilerException {
        consume(Token.TokenType.KEYWORD, "version");
        String version = currentToken.getLexme();
        consume(Token.TokenType.STRING);
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
                    astNodes.add(parseIdentifier());
                    // assignment a = 20 or a , b = 10 , 20
                    break;
//                case INDENT:
//                    consume(Token.TokenType.INDENT);
//                    break;
//                case DEDENT:
//                    consume(Token.TokenType.DEDENT);
//                    break;
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
                return parseIdentifier();
            case KEYWORD:
                if (match("if")) {
                    astList.add(this.parseIfStatement());
                } else if (match("while")) {
                    astList.add(this.parseWhileStatement());
                } else if (match("for")) {
                    astList.add(this.parseForStatement());
                } else if (match("var")) {
                    astList.add(this.parseVarDecl());
                } else if (match("obj")) {
                    astList.add(this.parseObjectCreation());
                } else {
                    // Handle other keywords like if, for, etc.
                }
                break;
            default:
                throw new RuntimeException("Unexpected token in statement: " + currentToken);
        }
        return new BlockNode(astList);
    }

    private ASTNode parseVarDecl() {
        consume(Token.TokenType.KEYWORD);
        return new ParserExceptionNode("broken_from_variable_decl","E08");
    }

    private ASTNode parseIdentifier() throws VipCompilerException {
        String currentId = currentToken.getLexme();
        getNextToken();
        if (match(Token.TokenType.IDENTIFIER)) {
            return parseMethodCall(currentId);
        } else if (match("=")) {
            return parseAssignment(currentId);
        } else if (match("(")) {
            return parseMethodCall(currentId);
        } else {
            throw new VipCompilerException(("compilationFault()"));
        }
    }


    // if OBJ package.ClassA object then if condition will be executed otherwise else condition will be taken
    private ASTNode parseObjectCreation() throws VipCompilerException {
        consume(Token.TokenType.KEYWORD);
        String identifier = currentToken.getLexme();
        consume(Token.TokenType.IDENTIFIER);
        if (!isEol()) {
            consume(Token.TokenType.OPERATOR,"=");
            return new ObjectDeclNode(identifier, this.parseExpression());
        }
        return new ParserExceptionNode("unexpected_end_of_line","E10");

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
        return new ParserExceptionNode("broken_from_for","E02");
    }

    private ASTNode parseWhileStatement() {
        return new ParserExceptionNode("broken_from_while_state","E05");
    }

    private ASTNode parseIfStatement() {
        return new ParserExceptionNode("broken_from_if_state","E04");
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
    private ASTNode parseAssignment(String identifier) throws VipCompilerException {
        ASTNode variable = new VariableNode(identifier);
        consume(Token.TokenType.IDENTIFIER);
        consume(Token.TokenType.OPERATOR, "="); // Expect '='
        ASTNode expr = parseExpression();
        return new AssignmentNode(variable, expr);
    }


    // Parse a function definition
    private ASTNode parseMethodDefinition() throws VipCompilerException {
        consume(Token.TokenType.KEYWORD, ParsingType.DEF); // Consume 'def'
        String functionName = currentToken.getLexme();
        List<ASTNode> statements =new ArrayList<>();
        consume(Token.TokenType.IDENTIFIER);
        consumeSilent(Token.TokenType.OPERATOR,"(");
        // Optionally parse parameters
        consumeSilent(Token.TokenType.OPERATOR,")");
        // Parse method body
        while (!match("return"))
        {
            System.out.println("looping ...");
            statements.add(parseStatement());
        }
        return new MethodDefNode(functionName,statements);
    }

    private ASTNode parseExpression() throws VipCompilerException {
        if (isEol()) {
            return new ParserExceptionNode("unexpected_eol","E07");
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
                    return new ParserExceptionNode("broken_from_expression","E06");
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
                    return new ParserExceptionNode("broken_from_expression","E04");
                }
                right.add(parseTerminal());
                list.add(new BooleanExpr(operator, left, right));
                left = null;
            }

        }

        return new BlockNode(list);
    }

    // like a.b.c.d string means this type of combination not the actual string
    private List<String> parseChainString(boolean stringAlreadYCaught) throws VipCompilerException {
        List<String> tokenList = new ArrayList<>(100);
        if (!stringAlreadYCaught) {
            tokenList.add(currentToken.getLexme());
            consume(Token.TokenType.IDENTIFIER);
        }

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
                this.parseExpressionList(true);
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
        return parseExpressionList(true);
    }

    private ASTNode parseExpressionList(boolean strict) throws VipCompilerException {
        List<ASTNode> exprNode = new ArrayList<>();
        if (nextTokenIs("(")) {
            consume(Token.TokenType.OPERATOR);// eat left parenthesis
            consume(Token.TokenType.OPERATOR);// eat right parenthesis
            return new ParserExceptionNode("broken_from_expressionList","E03");
        }
        if (strict) {
            consume(Token.TokenType.OPERATOR, "(");
        }


        while (readIndex < LineTokens.size()) {
            if (match("(") || match(")")) {
                break;
            }

            if (match(","))
            {
                consume(Token.TokenType.OPERATOR,",");
            }

            exprNode.add(this.parseExpression());
        }
        if (strict) {
            consume(Token.TokenType.OPERATOR, ")");
        }
        return new BlockNode(exprNode);
    }

    private ASTNode parseTerminal() throws VipCompilerException {
        switch (currentToken.getType()) {
            case IDENTIFIER:
                return parseIdentifier();
            case STRING:
                return this.parseString();
            case NUMBER:
                return this.parseNumber();
            case KEYWORD:
                if(match("new"))
                {
                    return this.parseInstance();
                }
                return new ParserExceptionNode("no returns found","E09");
            case OPERATOR:
                if(match("("))
                {
                    return this.parseExpressionList(true);
                }
                break;
            default:
                if (currentToken.getLexme().equals(")")) {
                    return new ParserExceptionNode("broken_from_parse_terminal","parser_fail");
                }
                throw new VipCompilerException("unknown token " + currentToken);
        }
        return new ParserExceptionNode("broken_from_parse_terminal","E01");
    }

    private ASTNode parseInstance() throws VipCompilerException {
        consume(Token.TokenType.KEYWORD);
        InstanceClassNode instanceClassNode = new InstanceClassNode();
        instanceClassNode.setClassName(currentToken.getLexme());
        consume(Token.TokenType.IDENTIFIER);
        instanceClassNode.setParams(this.parseExpressionList(true));
        return instanceClassNode;
    }

    private ASTNode parseString() {
        String StringToken = currentToken.getLexme();
        return new StringLiteralNode(StringToken);
    }

    private ASTNode parseNumber() throws VipCompilerException {

        try {
            int number = Integer.parseInt(currentToken.getLexme());
            getNextToken();
            return new NumberNode(number);
        } catch (NumberFormatException e) {
            throw new VipCompilerException("Please enter a valid Number in expression");
        }

    }

    private ASTNode parseMethodCall(String methodCall) throws VipCompilerException {
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
                    "greater than or equal", "greater than", "not", "not equal" -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }
}

