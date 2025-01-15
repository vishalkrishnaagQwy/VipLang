package org.lang.vip;

import org.lang.exceptions.VipCompilerException;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static java.lang.System.exit;


public class Parser {
    private final Lexer lexer;
    private Token currentToken;
    private List<Token> LineTokens;
    String className = "";
    int classId = 0;
    int readIndex = 0;
    ASTNode VipAstNodeBASE;
    boolean eof_reached = false;


    public Parser(Lexer lexer, int mClassId) throws VipCompilerException {
        this.lexer = lexer;
        this.LineTokens = lexer.getNextLine();
        this.classId = mClassId;
        getNextToken();// Initialize the current token
        VipAstNodeBASE = parseProgram();
    }

    ASTNode getParseTree() {
        return VipAstNodeBASE;
    }

    // Advance to the next token
    private void consume(Token.TokenType expectedType) {
        System.out.println(currentToken);
        if (currentToken.getType() == expectedType) {
            getNextToken();
        } else {
            throw new RuntimeException("Unexpected token: " + currentToken + " expected " + expectedType);
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

    private boolean isHint()
    {
        return currentToken.getType() == Token.TokenType.HINT;
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
        ClassDeclNode classDeclNode = new ClassDeclNode(this.classId);
        classDeclNode.setPackage(parsePackage());
        classDeclNode.setVersion(parseVersion());
        consume(Token.TokenType.KEYWORD, ParsingType.CLASS);
        String vipClasName = currentToken.getLexme();
        consume(Token.TokenType.IDENTIFIER);
        consume(Token.TokenType.INDENT);
        this.className = vipClasName;
        classDeclNode.setClassName(this.className);
        classDeclNode.setClassBody(parseClassBody());
        return classDeclNode;
    }

    private PackageDeclNode parsePackage() throws VipCompilerException {
        consume(Token.TokenType.KEYWORD, "package");
        PackageDeclNode pack = new PackageDeclNode(currentToken.getLexme());
        consume(Token.TokenType.STRING);
        return pack;
    }

    private ASTNode parseVersion() throws VipCompilerException {
        consume(Token.TokenType.KEYWORD, "version");
        String version = currentToken.getLexme();
        consume(Token.TokenType.STRING);
        return new VersionNode(version);
    }

    private ASTNode parseClassBody() throws VipCompilerException {
        List<ASTNode> body = new ArrayList<>();
        while (LineTokens != null || eof_reached) {
            switch (currentToken.getType()) {
                case KEYWORD:
                    if (match(ParsingType.DEF.getValue())) {
                        body.add(parseMethodDefinition());
                    } else {
                        // Handle other keywords like if, for, etc.
                        throw new RuntimeException("Illegal codes inside class body of '" + className + "' .vp");
                    }
                    break;
                case HINT:
                    body.add(parseVarDecl());
                    // assignment a = 20 or a , b = 10 , 20
                    break;
//                case INDENT:
//                    consume(Token.TokenType.INDENT);
//                    break;
//                case DEDENT:
//                    consume(Token.TokenType.DEDENT);
//                    break;
                default:
                    System.out.println("something went wrong got "+currentToken);
                    exit(-1);
                    break;
            }
        }
        return new BlockNode(body);
    }

    // Parse a single statement
    private ASTNode parseStatement() throws VipCompilerException {
        List<ASTNode> astList = new ArrayList<>();
        switch (currentToken.getType()) {
            case HINT:
                astList.add(parseVarDecl());
                break;
            case IDENTIFIER:
                astList.add(parseIdentifier());
                break;
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


    private ASTNode parseIdentifier() throws VipCompilerException {
        String currentId = currentToken.getLexme();
        getNextToken();
        if (match("=")) {
            /*
             * note: assignment node may contain variable declaration . in the case of no hints given
             * a = 10 for example ,here a will be considered as any or a: any = 10
             * */
            return parseAssignment(currentId);
        } else {
            return parseMethodCall(currentId);
        }
    }

    private ASTNode parseVarDecl() throws VipCompilerException {
        VarDeclNode varDeclNode = new VarDeclNode();
        varDeclNode.setHints(this.calculateCollectiveHints());
        varDeclNode.setVariableName(currentToken.getLexme());
        consume(Token.TokenType.IDENTIFIER);
        // todo : need more implmentations like int a; int a, b , c
        consume(Token.TokenType.OPERATOR, "=");
        varDeclNode.setExpr(parseExpression());
        return varDeclNode;
    }


    public static boolean getHint(String valueToCheck) {
        try {
            HintType hint = HintType.valueOf(valueToCheck);
            return true; // Value exists
        } catch (IllegalArgumentException e) {
            return false; // Value doesn't exist
        }
    }

    public static HintType convertToHint(String valueToCheck) {
        try {
            return HintType.valueOf(valueToCheck);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * list<int> or params : Map<int,Pair<Integer,Pair<String,SomeClass>>>
     * Array<int> or parms : Optional
     */

    private ASTNode calculateCollectiveHints() throws VipCompilerException {
        // type <xyz | p q r s >
        TypeBucket typeBucket = new TypeBucket();
            List<Token> values = new ArrayList<>();
            values.add(currentToken);
            getNextToken();
            if (match("<")) {
                int Count =1;
                values.add(currentToken);
                getNextToken();
                while (Count != 0) {
                    if(match("<")) {Count++;}
                    if(match(">")) {Count--;}
                    values.add(currentToken);
                    getNextToken();
                }
            }

            typeBucket.setIdBucket(values);
        return typeBucket;
    }

    private List<Token> calculateObjectHints(){
        List<Token> values = new ArrayList<>();
        int Count =1;
        values.add(currentToken);
        getNextToken();
        while (Count != 0) {
            if(match("<")) {Count++;}
            if(match(">")) {Count--;}
            values.add(currentToken);
            getNextToken();
        }
        return values;
    }

    /*
    // someMethod(param1,param2) , someMethod(param1:tuple<int,str,int> , param2:map<str,pair<int,char>)
    * */
    private ASTNode parseParams() throws VipCompilerException {
        consume(Token.TokenType.OPERATOR, "(");
        List<ASTNode> astList = new ArrayList<>();
        while (!match(")")) {
            DefParams defParams = new DefParams();
            // (int param, any something ...remaining)
            if (isHint() || match(Token.TokenType.IDENTIFIER)) {
                defParams.setValue(this.calculateCollectiveHints());
                getNextToken();
                defParams.setParam(currentToken.getLexme());
            }
            else if (match(",")) {
                getNextToken();
            }
            else if (match("=")) {
                getNextToken();
                defParams.setAssignExpr(parseExpression());
            }
            else {
                break;
            }
            astList.add(defParams);
        }

        consume(Token.TokenType.OPERATOR, ")");
        return new BlockNode(astList);
    }

    private HintType toHint(String lexeme) throws VipCompilerException {
        switch (lexeme.toLowerCase()) {
            case "int" -> {
                return HintType.INT;
            }
            case "str" -> {
                return HintType.STR;
            }
            case "float" -> {
                return HintType.FLOAT;
            }
            case "chr" -> {
                return HintType.CHR;
            }
            case "none" -> {
                return HintType.NONE;
            }
            case "optional" -> {
                return HintType.OPTIONAL;
            }
            case "bool" -> {
                return HintType.BOOL;
            }
            case "any" -> {
                return HintType.ANY;
            }
            default -> {
                throw new VipCompilerException("illegal type " + lexeme);
            }
        }
    }


    // if OBJ package.ClassA object then if condition will be executed otherwise else condition will be taken
    private ASTNode parseObjectCreation() throws VipCompilerException {
        consume(Token.TokenType.KEYWORD);
        String identifier = currentToken.getLexme();
        consume(Token.TokenType.IDENTIFIER);
        List<Token> hints = null;
        if(match("<"))
        {
            hints= this.calculateObjectHints();
        }

        return new ClassObjectDeclNode(identifier,hints,this.parseExpressionList(true));
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
        return new ParserExceptionNode("broken_from_for", "E02");
    }

    private ASTNode parseWhileStatement() {
        return new ParserExceptionNode("broken_from_while_state", "E05");
    }

    private ASTNode parseIfStatement() {
        return new ParserExceptionNode("broken_from_if_state", "E04");
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


    // Parse an assignment if assignment else declaration
    private ASTNode parseAssignment(String identifier) throws VipCompilerException {
        ASTNode variable = new VariableNode(identifier);
        getNextToken();
        if (match("new")) {
            return parseObjectCreation();
        } else {
            ASTNode expr = parseExpression();
            return new AssignmentNode(variable, expr);
        }
    }


    // Parse a function definition
    private ASTNode parseMethodDefinition() throws VipCompilerException {
        consume(Token.TokenType.KEYWORD, ParsingType.DEF); // Consume 'def'
        ASTNode params =null;
        ASTNode ReturnType =null;
        String functionName = currentToken.getLexme();
        List<ASTNode> statements = new ArrayList<>();
        consume(Token.TokenType.IDENTIFIER);
        if(match("("))
        {
            params = this.parseParams();
        }

        if (match("-")) {
            getNextToken();
            consume(Token.TokenType.OPERATOR, ">");
            ReturnType = calculateCollectiveHints();
        }
        consume(Token.TokenType.INDENT);
        // Parse method body
        while (!match("return")) {
            System.out.println("looping ...");
            statements.add(parseStatement());
        }
        getNextToken();
//        consume(Token.TokenType.DEDENT);
        return new MethodDefNode(functionName,params, statements, ReturnType);
    }

    private ASTNode parseExpression() throws VipCompilerException {
        if (isEol()) {
            return new ParserExceptionNode("unexpected_eol", "E07");
        }
        List<ASTNode> list = new ArrayList<>();
        ASTNode left = parseTerminal();
        String operator = "";
        if (match(")")) {
            return left;
        }
        while (isArithematicOperator(currentToken) || isLogicalOperator(currentToken)) {
            if (isArithematicOperator(currentToken)) {
                List<ASTNode> right = new ArrayList<>();
                operator = currentToken.getLexme();
                getNextToken();
                if (match("(") || match(")")) {
                    return new ParserExceptionNode("broken_from_expression", "E06");
                }
                right.add(parseTerminal());
                list.add(new ArithematicExpr(operator, left, right));
                left = null;
            } else if (isLogicalOperator(currentToken)) {
                List<ASTNode> right = new ArrayList<>();
                operator = currentToken.getLexme();
                getNextToken();
                if (match("(") || match(")")) {
                    return new ParserExceptionNode("broken_from_expression", "E06");
                }
                right.add(parseTerminal());
                list.add(new BooleanExpr(operator, left, right));
                left = null;
            }

        }
        if (list.isEmpty() && left != null) {
            return left;
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
        if (strict) {
            consume(Token.TokenType.OPERATOR, "(");
        }

        while (readIndex < LineTokens.size()) {
            if (match(")")) {
                break;
            }

            if (match(",")) {
                consume(Token.TokenType.OPERATOR, ",");
            }

            this.parseExpression();
        }
        if (strict) {
            consume(Token.TokenType.OPERATOR, ")");
        }
        return new BlockNode(exprNode);
    }

    private ASTNode parseTerminal() throws VipCompilerException {
        switch (currentToken.getType()) {
            case IDENTIFIER:
                return this.parseId();
            case KEYWORD:
                return this.parseExprKeyword();
            case STRING:
                return this.parseString();
            case NUMBER:
                return this.parseNumber();
            case FLOAT:
                return this.parseFloat();
            case DOUBLE:
                return this.parseDouble();
            case OPERATOR:
                if (match("(")) {
                    return this.parseExpressionList(true);
                }
                break;
            default:
                throw new VipCompilerException("unknown token " + currentToken);
        }
        return new ParserExceptionNode("broken_from_parse_terminal", "E01");
    }

    private ASTNode parseExprKeyword() throws VipCompilerException {
        System.out.println(currentToken);
        if (currentToken.getLexme().equals("new"))
        {
          return parseObjectCreation();
        }
       else if(currentToken.getLexme().toLowerCase().equals("none"))
        {
            return new NoneNode();
        }
        throw new VipCompilerException("undefined operation");
    }

    private ASTNode parseId() {
        String remap = currentToken.getLexme();
        getNextToken();
        return new VariableNode(remap);
    }

    private ASTNode parseString() {
        String StringToken = currentToken.getLexme();
        getNextToken();
        return new StringLiteralNode(StringToken);
    }

    private ASTNode parseNumber() throws VipCompilerException {
        String number = currentToken.getLexme();
        getNextToken();
        return new NumberNode(number, NumberNode.Type.INT);
    }

    private ASTNode parseFloat() throws VipCompilerException {
        String number = currentToken.getLexme();
        getNextToken();
        return new NumberNode(number, NumberNode.Type.FLOAT);
    }

    private ASTNode parseDouble() throws VipCompilerException {
        String number = currentToken.getLexme();
        getNextToken();
        return new NumberNode(number, NumberNode.Type.DOUBLE);
    }

    private ASTNode parseMethodCall(String methodCall) throws VipCompilerException {
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

