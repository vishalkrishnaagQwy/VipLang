package org.lang.vip;

import org.lang.exceptions.VipCompilerException;

import java.io.File;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//@author vishalkrishnaag
public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java Compiler <directory-path>");
            return;
        }

        File directory = new File(args[0]);

        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Invalid directory path: " + args[0]);
            return;
        }

        processDirectory(directory);
    }

    /**
     * Process all .vp files in the specified directory.
     *
     * @param directory the directory containing .vp files
     */
    private static void processDirectory(File directory) {
        File[] files = directory.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("No files found in directory: " + directory.getPath());
            return;
        }

        for (File file : files) {
            if (isVipFile(file)) {
                try {
                    processFile(file);
                } catch (IOException | VipCompilerException e) {
                    System.err.println("Error processing file: " + file.getName());
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Check if the file is a valid .jack file.
     *
     * @param file the file to check
     * @return true if the file is a .vp file, false otherwise
     */
    private static boolean isVipFile(File file) {
        return file.isFile() && file.getName().endsWith(".py");
    }

    /**
     * Process an individual .jack file using the Lexer and Parser.
     *
     * @param file the .vp file to process
     * @throws IOException if an error occurs while reading the file
     */
    private static void processFile(File file) throws IOException, VipCompilerException {
        System.out.println("Processing file: " + file.getName());
        Lexer lexer = new Lexer(file.getPath());
        Parser parser = new Parser(lexer);
//        SymbolTable symbolTable = new SymbolTable();
//        ASTNode astNodes = parser.getParseTree();
//        ASTAnalyser astAnalyser = new ASTAnalyser(symbolTable);
//        ASTPrinter astPrinter = new ASTPrinter();
//        JavaBytecodeGenerator codeGen = new JavaBytecodeGenerator(symbolTable);
//        codeGen.writeClassToFile();
//        if (astNodes != null) {
//                astNodes.accept(astPrinter);
//                astNodes.accept(astAnalyser);
//                astNodes.accept(codeGen);
//        }
        // we set it whole for future


        // Perform further actions like semantic analysis and VM code generation here
    }
}