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
    private String textContent;
    private int index;
    private Stack<Integer> indentStack = new Stack<>();
    private int currentIndentation = 0;
    int expectIndent = 0;

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
            StringBuilder sb = new StringBuilder();
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineText;
                while ((lineText = bufferedReader.readLine()) != null) {
                    sb.append(lineText).append("\n");
                }
                read.close();
            } else {
                System.out.println("Cannot find the file");
            }
            textContent = sb.toString();
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

//    public Token getNextToken() {
//        String tokenValue = "";
//
//        while (index < textContent.length()) {
//            char currentChar = textContent.charAt(index);
//
//            // Handle whitespaces
//            if (Character.isWhitespace(currentChar)) {
//                if (currentChar == '\n') {
//                    lineNumber++;
//                    index++;
//                    if (currentIndentation > 0) {
//                        // Check if current indentation is less than stack top
//                        if (!indentStack.isEmpty() && currentIndentation < indentStack.peek()) {
//                            indentStack.pop(); // Dedent
//                            return new Token(Token.TokenType.DEDENT, "DEDENT", lineNumber);
//                        }
//                        // If current indentation is greater than stack top, push new indent
//                        indentStack.push(currentIndentation);
//                        return new Token(Token.TokenType.INDENT, "INDENT", lineNumber);
//                    }
//                    currentIndentation = 0; // Reset on newline
//                    continue;
//                } else if (currentChar == ' ' || currentChar == '\t') {
//                    currentIndentation++; // Increment indentation count
//                    index++;
//                    continue;
//                }
//            }
//
//            // Handle single-line comments
//            if (currentChar == '#') {
//                while (index < textContent.length() && textContent.charAt(index) != '\n') {
//                    index++; // Skip the comment
//                }
//                continue; // Move to the next line
//            }
//
//            // Handle multi-line comments (triple double quotes)
//            if (currentChar == '"' && index + 2 < textContent.length() &&
//                    textContent.charAt(index + 1) == '"' && textContent.charAt(index + 2) == '"') {
//                index += 3; // Skip opening triple quotes
//                while (index < textContent.length() && !(textContent.charAt(index) == '"' &&
//                        textContent.charAt(index + 1) == '"' &&
//                        textContent.charAt(index + 2) == '"')) {
//                    index++;
//                }
//                index += 3; // Skip closing triple quotes
//                continue; // Move to the next token
//            }
//
//            // Handle multi-line comments (triple single quotes)
//            if (currentChar == '\'' && index + 2 < textContent.length() &&
//                    textContent.charAt(index + 1) == '\'' && textContent.charAt(index + 2) == '\'') {
//                index += 3; // Skip opening triple quotes
//                while (index < textContent.length() && !(textContent.charAt(index) == '\'' &&
//                        textContent.charAt(index + 1) == '\'' &&
//                        textContent.charAt(index + 2) == '\'')) {
//                    index++;
//                }
//                index += 3; // Skip closing triple quotes
//                continue; // Move to the next token
//            }
//
//            // Handle identifiers and keywords
//            if (Character.isLetter(currentChar) || currentChar == '_') {
//                while (index < textContent.length() &&
//                        (Character.isLetterOrDigit(textContent.charAt(index)) || textContent.charAt(index) == '_')) {
//                    tokenValue += textContent.charAt(index);
//                    index++;
//                }
//
//                // Check if the token is a keyword
//                for (String keyword : keywords) {
//                    if (tokenValue.equals(keyword)) {
//                        return new Token(Token.TokenType.KEYWORD, tokenValue, lineNumber);
//                    }
//                }
//
//                // If not a keyword, return as an identifier
//                return new Token(Token.TokenType.IDENTIFIER, tokenValue, lineNumber);
//            }
//
//            // Handle numbers (including floating-point)
//            if (Character.isDigit(currentChar)) {
//                while (index < textContent.length() && Character.isDigit(textContent.charAt(index))) {
//                    tokenValue += textContent.charAt(index);
//                    index++;
//                }
//                // Check for a floating point
//                if (index < textContent.length() && textContent.charAt(index) == '.') {
//                    tokenValue += textContent.charAt(index);
//                    index++;
//                    while (index < textContent.length() && Character.isDigit(textContent.charAt(index))) {
//                        tokenValue += textContent.charAt(index);
//                        index++;
//                    }
//                }
//                return new Token(Token.TokenType.NUMBER, tokenValue, lineNumber);
//            }
//
//            // Handle string literals
//            if (currentChar == '"') {
//                index++; // Skip opening quote
//                while (index < textContent.length() && textContent.charAt(index) != '"') {
//                    tokenValue += textContent.charAt(index);
//                    index++;
//                }
//                index++; // Skip closing quote
//                return new Token(Token.TokenType.STRING, tokenValue, lineNumber);
//            }
//
//            // Handle byte strings (e.g., b"string")
//            if (currentChar == 'b' && index + 1 < textContent.length() && textContent.charAt(index + 1) == '"') {
//                index += 2; // Skip 'b' and opening quote
//                while (index < textContent.length() && textContent.charAt(index) != '"') {
//                    tokenValue += textContent.charAt(index);
//                    index++;
//                }
//                index++; // Skip closing quote
//                return new Token(Token.TokenType.BYTE_STRING, tokenValue, lineNumber);
//            }
//
//            // Handle formatted strings (e.g., f"string")
//            if (currentChar == 'f' && index + 1 < textContent.length() && textContent.charAt(index + 1) == '"') {
//                index += 2; // Skip 'f' and opening quote
//                while (index < textContent.length() && textContent.charAt(index) != '"') {
//                    tokenValue += textContent.charAt(index);
//                    index++;
//                }
//                index++; // Skip closing quote
//                return new Token(Token.TokenType.FORMATTED_STRING, tokenValue, lineNumber);
//            }
//
//            // Handle symbols and operators
//            if (symbols.containsKey(String.valueOf(currentChar))) {
//                tokenValue = String.valueOf(currentChar);
//                index++;
//                return new Token(Token.TokenType.OPERATOR, tokenValue, lineNumber);
//            }
//
//            // Unknown character handling (error)
//            System.out.println("Unknown character: " + currentChar);
//            index++;
//        }
//
//        // End of file
//        return new Token(Token.TokenType.EOF, "", lineNumber);
//    }


    public Token getNextToken() {

        StringBuilder tokenValue = new StringBuilder();
        currentIndentation = 0; // Reset for new lines

        while (index < textContent.length()) {
            char currentChar = textContent.charAt(index);

            // Handle whitespaces
            if (Character.isWhitespace(currentChar) || textContent.charAt(index) == '\n') {
                // Increment the current indentation for spaces or tabs
                if (currentChar == ' ' || currentChar == '\t') {
                    currentIndentation++;
                    index++;
                    continue; // Continue processing whitespace
                }

                // Handle new lines
                if (currentChar == '\n') {
                    lineNumber++;
                    index++;

                    // Reset for the next line
                    currentIndentation = 0;
                    continue;
                }
            }

            // Handle identifiers and keywords
            if (Character.isLetter(currentChar) || currentChar == '_') {
                if (currentIndentation > minimum_space &&  currentIndentation > (indentStack.isEmpty() ? 0 : indentStack.peek())) {
                    if (expectIndent > 0) {
                        indentStack.push(currentIndentation);
                    } else {
                        throw new RuntimeException("expected intentation");
                    }

                }
                while (index < textContent.length() &&
                        (Character.isLetterOrDigit(textContent.charAt(index)) || textContent.charAt(index) == '_')) {
                    tokenValue.append(textContent.charAt(index));
                    index++;
                }
                String idValue = tokenValue.toString();

                if (idValue.equals("class") || idValue.equals("def")) {
                    expectIndent++; // Set the flag to expect an indent after the newline
                }



                // After a line found check if an indent/dedent is required
                if (!indentStack.isEmpty() && currentIndentation < indentStack.peek() && currentIndentation > minimum_space) {
                    if(expectIndent>0)
                    {
                        indentStack.pop();
                    }
                    return new Token(Token.TokenType.DEDENT, "DEDENT", lineNumber);
                }

                // Return as a keyword or identifier
                if (keywords.contains(idValue)) {
                    return new Token(Token.TokenType.KEYWORD, idValue, lineNumber);
                }
                return new Token(Token.TokenType.IDENTIFIER, idValue, lineNumber);
            }

            // Handle single-line comments
            if (currentChar == '#') {
                while (index < textContent.length() && textContent.charAt(index) != '\n') {
                    index++; // Skip the comment
                }

                continue; // Move to the next line (comments are ignored)
            }


            // Handle multi-line comments (triple double quotes)
            if (currentChar == '"' && index + 2 < textContent.length() &&
                    textContent.charAt(index + 1) == '"' && textContent.charAt(index + 2) == '"') {
                index += 3; // Skip opening triple quotes
                while (index < textContent.length() && !(textContent.charAt(index) == '"' &&
                        index + 2 < textContent.length() &&
                        textContent.charAt(index + 1) == '"' &&
                        textContent.charAt(index + 2) == '"')) {
                    if (textContent.charAt(index) == '\\') { // Handle escape sequences
                        index++; // Skip the escape character
                    }
                    index++;
                }
                index += 3; // Skip closing triple quotes
                continue; // Move to the next token
            }

            // Handle string literals (single and double quotes)
            if (currentChar == '"' || currentChar == '\'') {
                char quoteType = currentChar; // Store the type of quote
                index++;
                while (index < textContent.length() && textContent.charAt(index) != quoteType) {
                    if (textContent.charAt(index) == '\\') { // Handle escape sequences
                        index++; // Skip the escape character
                    }
                    tokenValue.append(textContent.charAt(index));
                    index++;
                }
                index++; // Skip the closing quote
                return new Token(Token.TokenType.STRING, tokenValue.toString(), lineNumber);
            }


            // Handle numbers (integers, floats, and complex numbers)
            if (Character.isDigit(currentChar)) {
                // Parse integer part
                while (index < textContent.length() && Character.isDigit(textContent.charAt(index))) {
                    tokenValue.append(textContent.charAt(index));
                    index++;
                }
                // Check for a floating-point number
                if (index < textContent.length() && textContent.charAt(index) == '.') {
                    tokenValue.append('.');
                    index++;
                    while (index < textContent.length() && Character.isDigit(textContent.charAt(index))) {
                        tokenValue.append(textContent.charAt(index));
                        index++;
                    }
                }
                // Check for complex number (e.g., 1 + 2j)
                if (index < textContent.length() && (textContent.charAt(index) == 'j' || textContent.charAt(index) == 'J')) {
                    tokenValue.append(textContent.charAt(index));
                    index++;
                }
                return new Token(Token.TokenType.NUMBER, tokenValue.toString(), lineNumber);
            }

            // Handle multi-character operators
            String twoCharOperator = String.valueOf(currentChar) +
                    (index + 1 < textContent.length() ? textContent.charAt(index + 1) : "");
            if (symbols.containsKey(twoCharOperator)) {
                tokenValue.append(twoCharOperator);
                index += 2; // Move past both characters
                return new Token(Token.TokenType.OPERATOR, tokenValue.toString(), lineNumber);
            }

            // Handle single-character operators
            if (symbols.containsKey(String.valueOf(currentChar))) {
                tokenValue.append(currentChar);
                index++;
                return new Token(Token.TokenType.OPERATOR, tokenValue.toString(), lineNumber);
            }

            // Unknown character handling (error)
            System.out.println("Unknown character: " + currentChar);
            index++;
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
