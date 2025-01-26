package org.lang.Lexer;

import org.lang.exceptions.ExceptionOnCodeReading;

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
    private int NewLineCheckEnabled = 0;
    private boolean usesTabs = false;   // Track if the file uses tabs
    private boolean usesSpaces = false;

    private final Set<String> keywords = new HashSet<String>();
    private final Set<String> hints = new HashSet<String>();
    private final Set<String> specialKeywords = new HashSet<String>();
    private final Set<String> dataTypes = new HashSet<String>();

    // Define symbols and their types
    private final Map<String, Token.TokenType> symbols = new HashMap<>();
    private static final int minimum_space = 1;

    public Lexer(String fileName) {
        this.encoding = "UTF-8"; // Change as needed
        this.path = fileName;
        this.file = new File(path);
        this.lineNumber = 0;
        initSymbols();
        initLocalFile(); // Read file content
    }

    private void initLocalFile() {
        try {
            textContent = new ArrayList<>();
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineText;
                while ((lineText = bufferedReader.readLine()) != null) {
                    textContent.add(lineText); // Store each line separately
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
        symbols.put("<", Token.TokenType.OPERATOR);
        symbols.put(">", Token.TokenType.OPERATOR);
        symbols.put("=", Token.TokenType.OPERATOR);
        symbols.put("+", Token.TokenType.OPERATOR);
        symbols.put("-", Token.TokenType.OPERATOR);
        symbols.put("*", Token.TokenType.OPERATOR);
        symbols.put("/", Token.TokenType.OPERATOR);
        symbols.put(":", Token.TokenType.OPERATOR);
        symbols.put(",", Token.TokenType.OPERATOR);
        symbols.put(".", Token.TokenType.OPERATOR);
        symbols.put("|", Token.TokenType.OPERATOR);
        symbols.put("and", Token.TokenType.OPERATOR);
        symbols.put("or", Token.TokenType.OPERATOR);
        symbols.put("not", Token.TokenType.OPERATOR);
        keywords.add("def");
        keywords.add("class");
        keywords.add("interface");
        keywords.add("in");
        keywords.add("break");
        keywords.add("continue");
        keywords.add("return");
        keywords.add("try");
        keywords.add("finally");
        keywords.add("with");
        keywords.add("as");
        keywords.add("import");
        keywords.add("from");
        keywords.add("global");
        keywords.add("static");
        keywords.add("lambda");
        keywords.add("True");
        keywords.add("False");
        keywords.add("None");
        keywords.add("package");
        keywords.add("version");
        keywords.add("implements");
        keywords.add("extends");
        keywords.add("abstract");
        keywords.add("new");
        hints.add("obj");
        hints.add("str");
        hints.add("float");
        hints.add("char");
        hints.add("bool");
        hints.add("Optional");
        hints.add("int");
        hints.add("any");
        hints.add("Either");
        specialKeywords.add("if");
        specialKeywords.add("elif");
        specialKeywords.add("else");
        specialKeywords.add("while");
        specialKeywords.add("for");
        specialKeywords.add("except");
    }

    private int calculateIndent(String line) {
        int indentLevel = 0;
        boolean foundIndentation = false;
        for (char c : line.toCharArray()) {
            if (c == ' ') {
                indentLevel++;
                if (!foundIndentation) {
                    usesSpaces = true;
                    foundIndentation = true;
                }
            } else if (c == '\t') {
                indentLevel += 4;
                if (!foundIndentation) {
                    usesTabs = true;
                    foundIndentation = true;
                }
            } else {
                break;
            }
        }

        // Check if the file is mixing tabs and spaces
        if (usesTabs && usesSpaces) {
            throw new IllegalArgumentException("IndentationError: Mixed tabs and spaces detected in the file!");
        }
        return indentLevel / 4;
    }


//    private void handleIndentation(List<Token> tokens, int indentLevel) {
//
//        if (indentLevel > currentIndentation) {
//            tokens.add(new Token(Token.TokenType.INDENT, "INDENT", lineNumber));
//        } else if (indentLevel < currentIndentation) {
//            tokens.add(new Token(Token.TokenType.DEDENT, "DEDENT", lineNumber));
//        }
//        currentIndentation = indentLevel;
//    }

    private void handleIndentation(List<Token> tokens, String line) {
        int newIndentation = calculateIndent(line);

        if (newIndentation > currentIndentation) {
            // Emit an INDENT token
            tokens.add(new Token(Token.TokenType.INDENT, "", lineNumber));
        } else if (newIndentation < currentIndentation) {
            // Emit multiple DEDENT tokens based on the difference
            int dedentCount = currentIndentation - newIndentation;
            for (int i = 0; i < dedentCount; i++) {
                tokens.add(new Token(Token.TokenType.DEDENT, "", lineNumber));
            }
        }

        // Update the current indentation level
        currentIndentation = newIndentation;
    }




    public List<Token> getNextLine() {
        if (lineNumber < textContent.size()) {
            int lineRead = lineNumber;
            lineNumber++;
            return lexLine(textContent.get(lineRead));
        } else {
//            ArrayList<Token> last_token = new ArrayList<>();
//            last_token.add(new Token(Token.TokenType.EOF,"EOF",lineNumber));
            return null;
        }
    }

    List<Token> lexLine(String input_line) {
        List<Token> tokens = new ArrayList<>();
        if (input_line.isEmpty()) {
            return getNextLine();
        }

        handleIndentation(tokens, input_line);

        // Now lex the actual content of the line
        char[] chars = input_line.trim().toCharArray(); // Ignore leading whitespace
        StringBuilder currentToken = new StringBuilder();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            if (Character.isWhitespace(c)) {
                continue; // Ignore whitespace
            } else if (c == '#') {
                if (tokens.isEmpty()) {
                    return getNextLine();
                } else {
                    return tokens;
                }

            }

            if (Character.isLetter(c) || c == '_') {
                currentToken.append(c);
                i = readIdentifier(tokens, chars, i, currentToken); // Read identifier/keyword
            } else if (Character.isDigit(c)) {
                currentToken.append(c);
                i = readNumber(tokens, chars, i, currentToken); // Read number
            } else if (c == '"') {
                i = readString(tokens, chars, i, currentToken); // Read string
            } else if (isOperator(c)) {
                tokens.add(new Token(Token.TokenType.OPERATOR, String.valueOf(c), lineNumber)); // Add operator token
            } else {
                throw new IllegalStateException("something went wrong on compiler");
            }
        }
        return tokens;
    }

    private boolean isOperator(char c) {
        return symbols.containsKey(String.valueOf(c));
    }


    private int readIdentifier(List<Token> tokens, char[] chars, int i, StringBuilder currentToken) {
        while (i + 1 < chars.length && (Character.isLetterOrDigit(chars[i + 1]) || chars[i + 1] == '_')) {
            currentToken.append(chars[++i]);
        }
        if (keywords.contains(currentToken.toString())) {
            tokens.add(new Token(Token.TokenType.KEYWORD, currentToken.toString(), lineNumber));
        } else if (specialKeywords.contains(currentToken.toString())) {
            if (this.NewLineCheckEnabled >= 20) {
                throw new ExceptionOnCodeReading("Line fault  found , please have a check");
            }
            this.NewLineCheckEnabled++;
            tokens.add(new Token(Token.TokenType.KEYWORD, currentToken.toString(), lineNumber));
        } else if (hints.contains(currentToken.toString())) {
            tokens.add(new Token(Token.TokenType.HINT, currentToken.toString(), lineNumber));
        }
        else {
            tokens.add(new Token(Token.TokenType.IDENTIFIER, currentToken.toString(), lineNumber));
        }

        currentToken.setLength(0); // Clear the token
        return i;
    }


//    private int readNumber(List<Token> tokens, char[] chars, int i, StringBuilder currentToken) {
//        while (i + 1 < chars.length && Character.isDigit(chars[i + 1])) {
//            currentToken.append(chars[++i]);
//        }
//        tokens.add(new Token(Token.TokenType.NUMBER, currentToken.toString(), lineNumber));
//        currentToken.setLength(0); // Clear the token
//        return i;
//    }

    private int readNumber(List<Token> tokens, char[] chars, int i, StringBuilder currentToken) {
        // Start by handling the integer part (digits before any decimal point)
        while (i + 1 < chars.length && Character.isDigit(chars[i + 1])) {
            currentToken.append(chars[++i]);
        }

        // Check if there's a decimal point for float or double
        if (i + 1 < chars.length && chars[i + 1] == '.') {
            currentToken.append(chars[++i]); // Append the decimal point

            // Handle the fractional part (digits after the decimal point)
            while (i + 1 < chars.length && Character.isDigit(chars[i + 1])) {
                currentToken.append(chars[++i]);
            }

            // After a decimal, the number is typically a float or double, so we mark it as such
            tokens.add(new Token(Token.TokenType.DOUBLE, currentToken.toString(), lineNumber));
            currentToken.setLength(0); // Clear the token
            return i;
        }

        // Now check for the 'f' or 'd' suffix for floats or doubles (after integer or decimal)
        if (i + 1 < chars.length && (chars[i + 1] == 'f' || chars[i + 1] == 'd')) {
            currentToken.append(chars[++i]); // Append the suffix ('f' or 'd')
            tokens.add(new Token(Token.TokenType.FLOAT, currentToken.toString(), lineNumber));
            currentToken.setLength(0); // Clear the token
            return i;
        }

        // If there was no decimal, suffix, or float handling, it's an integer
        tokens.add(new Token(Token.TokenType.NUMBER, currentToken.toString(), lineNumber));
        currentToken.setLength(0); // Clear the token
        return i;
    }


    private int readString(List<Token> tokens, char[] chars, int i, StringBuilder currentToken) {
        while (i + 1 < chars.length && chars[i + 1] != '"') {
            currentToken.append(chars[++i]);
        }
        tokens.add(new Token(Token.TokenType.STRING, currentToken.toString(), lineNumber));
        currentToken.setLength(0); // Clear the token
        i = i + 1;
        return i;
    }


    public String getFolderPath() {
        return file.getParent();
    }
}
