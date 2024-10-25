package org.lang.vip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Lexer {
    boolean peek = false;

    private String encoding;
    private String path;
    private File file;
    private int lineNumber;
    private List<String> textContent;
    private int index;
    private int currentIndentation = 0;

    private final Set<String> keywords = new HashSet<String>();

    // Define symbols and their types
    private final Map<String, Token.TokenType> symbols = new HashMap<>();
    private static final int minimum_space=1;

    public Lexer(String fileName) {
        this.encoding = "UTF-8"; // Change as needed
        this.path = fileName;
        this.file = new File(path);
        this.lineNumber = 1;
        this.index = 0;
        initSymbols();
        initLocalFile(); // Read file content
    }

    private void initLocalFile() {
        try {
            List<String> lines = new ArrayList<>();
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineText;
                while ((lineText = bufferedReader.readLine()) != null) {
                    lines.add(lineText); // Store each line separately
                }
                read.close();
            } else {
                System.out.println("Cannot find the file");
            }
        } catch (Exception e) {
            System.out.println("Error reading file.");
            e.printStackTrace();
        }
    }


    private void initSymbols() {
        symbols.put("(", Token.TokenType.OPERATOR);
        symbols.put(")", Token.TokenType.OPERATOR);
        symbols.put("{", Token.TokenType.OPERATOR);
        symbols.put("}", Token.TokenType.OPERATOR);
        symbols.put("[", Token.TokenType.OPERATOR);
        symbols.put("]", Token.TokenType.OPERATOR);
        symbols.put("=", Token.TokenType.OPERATOR);
        symbols.put("+", Token.TokenType.OPERATOR);
        symbols.put("-", Token.TokenType.OPERATOR);
        symbols.put("*", Token.TokenType.OPERATOR);
        symbols.put("/", Token.TokenType.OPERATOR);
        symbols.put(":", Token.TokenType.OPERATOR);
        symbols.put(",", Token.TokenType.OPERATOR);
        symbols.put(".", Token.TokenType.OPERATOR);
        symbols.put("and", Token.TokenType.OPERATOR);
        symbols.put("or", Token.TokenType.OPERATOR);
        symbols.put("not", Token.TokenType.OPERATOR);
        keywords.add("def");
        keywords.add("class");
        keywords.add("if");
        keywords.add("elif");
        keywords.add("else");
        keywords.add("while");
        keywords.add("for");
        keywords.add("in");
        keywords.add("break");
        keywords.add("continue");
        keywords.add("return");
        keywords.add("try");
        keywords.add("except");
        keywords.add("finally");
        keywords.add("with");
        keywords.add("as");
        keywords.add("import");
        keywords.add("from");
        keywords.add("global");
        keywords.add("nonlocal");
        keywords.add("lambda");
        keywords.add("pass");
        keywords.add("yield");
        keywords.add("True");
        keywords.add("False");
        keywords.add("None");
    }


    private void handleIndentation(int indentLevel) {
        if (indentLevel > currentIndentation) {
            tokens.add(new Token(currentIndentation.INDENT, "", lineNumber));
        } else if (indentLevel < currentIndentation) {
            tokens.add(new Token(TokenType.DEDENT, "", lineNumber));
        }
        currentIndentation = indentLevel;
    }


    public List<Token> getNextLine() {

            lineNumber++;
        return lexLine(textContent.get(lineNumber));

    }
    List<Token> lexLine(String input_line){
        List<Token> tokens = new ArrayList<>();

        charIndex = 0;

        int indentLevel = calculateIndent(line);
        handleIndentation(indentLevel);

        // Now lex the actual content of the line
        char[] chars = line.trim().toCharArray(); // Ignore leading whitespace
        StringBuilder currentToken = new StringBuilder();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            if (Character.isWhitespace(c)) {
                continue; // Ignore whitespace
            }

            if (Character.isLetter(c)) {
                currentToken.append(c);
                i = readIdentifier(chars, i, currentToken); // Read identifier/keyword
            } else if (Character.isDigit(c)) {
                currentToken.append(c);
                i = readNumber(chars, i, currentToken); // Read number
            } else if (c == '"') {
                currentToken.append(c);
                i = readString(chars, i, currentToken); // Read string
            } else if (isOperator(c)) {
                tokens.add(new Token(TokenType.OPERATOR, String.valueOf(c), lineNumber)); // Add operator token
            } else if (c == '#') {
                break; // Ignore comments
            }
        }





















































        // End of file
        return new Token(Token.TokenType.EOF, "", lineNumber);
    }


    public String getFolderPath() {
        return file.getParent();
    }

    private void mangaeIntent() {

    }
}
