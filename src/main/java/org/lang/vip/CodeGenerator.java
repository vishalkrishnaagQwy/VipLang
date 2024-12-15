package org.lang.vip;
import org.lang.memmory.SymbolTable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {
    private SymbolTable symbolTable;
    private List<String> code;
    private String className = "dev_main";

    public CodeGenerator(){
        this.className = className.replace('.', '/');
        this.code = new ArrayList<>();
    }
    public void write(String code)
    {
        this.code.add(code);
    }

    public void writeFile() {
        try {
            FileOutputStream fos = new FileOutputStream(className + ".java");
            fos.write(("class "+className+"{").getBytes(StandardCharsets.UTF_8));
            String content = String.join("\n", code);
            byte[] bytes = content.getBytes();
            fos.write(bytes);
            fos.write("}".getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
