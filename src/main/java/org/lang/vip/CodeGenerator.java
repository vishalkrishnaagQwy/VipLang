package org.lang.vip;
import org.lang.memmory.SymbolTable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CodeGenerator {
    private SymbolTable symbolTable;
    private String className = "dev_main";

    public CodeGenerator(SymbolTable _symbolTable){
       this.symbolTable = _symbolTable;
        this.className = className.replace('.', '/');
    }

    public void writeClassToFile() {
        try {
            FileOutputStream fos = new FileOutputStream(className + ".java");
            fos.write("hello world".getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
