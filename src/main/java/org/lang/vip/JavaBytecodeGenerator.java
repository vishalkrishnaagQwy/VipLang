package org.lang.vip;
import org.lang.utils.Pair;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Label;
import org.lang.memmory.SymbolTable;

import java.io.FileOutputStream;
import java.io.IOException;

public class JavaBytecodeGenerator implements AST, Opcodes {
    private SymbolTable symbolTable;
    private ClassWriter classWriter;
    private String className = "_dev_";


    public JavaBytecodeGenerator(SymbolTable _symbolTable){
        this.symbolTable = _symbolTable;
        this.className = className.replace('.', '/');
        this.classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        // Define the class
    }
    @Override
    public void visitMethodDefNode(MethodDefNode methodDefNode) {
        String descriptor = "()V";
        if(methodDefNode.functionName.equals("main"))
        {
            descriptor= "([Ljava/lang/String;)V";
        }
        MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, methodDefNode.functionName, descriptor, null, null);
        mv.visitCode();
        methodDefNode.body.forEach(element -> element.accept(this));
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 1);
        mv.visitEnd();
    }

    @Override
    public void visitBlockNode(BlockNode blockNode) {
      for(ASTNode block : blockNode.list)
      {
         block.accept(this);
      }
    }

    @Override
    public void visitMethodCallNode(MethodCallNode methodCallNode) {
        MethodVisitor Method = classWriter.visitMethod(
                ACC_PUBLIC + ACC_STATIC,      // public static
                "main",                       // method name
                "([Ljava/lang/String;)V",     // descriptor (main(String[] args))
                null,
                null
        );

        Method.visitCode();

        if(methodCallNode.methodName.equals("print"))
        {
            // Add bytecode for System.out.println("Hello");
            Method.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            Method.visitLdcInsn("Hello");
            Method.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            // Return from the main method
            Method.visitInsn(RETURN);

            // Specify stack size and local variables for main method
            Method.visitMaxs(2, 1);
            Method.visitEnd();
        }
        methodCallNode.body.accept(this);
    }

    @Override
    public void visitIFNode(IFNode ifNode) {
        MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, "exampleIfElse", "(I)V", null, null);

        Label elseLabel = new Label();
        Label endLabel = new Label();

// Load variable x (index 1) and compare it with 10
        mv.visitVarInsn(ILOAD, 1);
        mv.visitLdcInsn(10);
        mv.visitJumpInsn(IF_ICMPLE, elseLabel);

// If greater, execute this block
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("Greater");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        mv.visitJumpInsn(GOTO, endLabel);

// Else block
        mv.visitLabel(elseLabel);
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("Smaller or Equal");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

// End of the method
        mv.visitLabel(endLabel);
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();

    }

    @Override
    public void visitWhileNode(WhileNode whileNode) {
        MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, "exampleWhile", "()V", null, null);

        Label loopStart = new Label();
        Label loopEnd = new Label();

// Initialize i = 0
        mv.visitInsn(ICONST_0);
        mv.visitVarInsn(ISTORE, 1);

// Start of loop
        mv.visitLabel(loopStart);
        mv.visitVarInsn(ILOAD, 1);
        mv.visitLdcInsn(5);
        mv.visitJumpInsn(IF_ICMPGE, loopEnd); // Exit loop if i >= 5

// Print i
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(ILOAD, 1);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);

// Increment i
        mv.visitIincInsn(1, 1);
        mv.visitJumpInsn(GOTO, loopStart);

// End of loop
        mv.visitLabel(loopEnd);
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();

    }

    @Override
    public void visitExperNode(ExprNode exprNode) {
        exprNode.accept(this);
    }

    @Override
    public void visitBooleanExprNode(BooleanExpr booleanExpr) {
        MethodVisitor methodVisitor = classWriter.visitMethod(
                ACC_PUBLIC + ACC_STATIC,
                "evaluate",
                "()I",
                null,
                null
        );
        methodVisitor.visitCode();
    }

    @Override
    public void visitParserExceptionNode(ParserExceptionNode parserExceptionNode) {
        System.out.println("error catched"+parserExceptionNode.errorCode);
    }

    @Override
    public void visitClassDeclNode(ClassDeclNode classDeclNode) {
        System.out.println("reached code gen or class decl");
        this.className = classDeclNode.getClassName();
        classWriter.visit(V1_8, ACC_PUBLIC, this.className, null, "java/lang/Object", null);
        // Add default constructor
        MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
        classDeclNode.classBody.accept(this);
   }

    @Override
    public void visitInstanceClassNode(InstanceClassNode instanceClassNode) {

    }

    @Override
    public void visitObjectDeclNode(ObjectDeclNode objectDeclNode) {

    }

    @Override
    public void visitPackageDeclNode(PackageDeclNode packageDeclNode) {

    }

    @Override
    public void visitStringLiteralNode(StringLiteralNode stringLiteralNode) {

    }

    @Override
    public void visitVersionNode(VersionNode versionNode) {

    }

    @Override
    public void visitVariableNode(VariableNode variableNode) {

    }

    @Override
    public void visitArithematicExpr(ArithematicExpr arithematicExpr) {
        arithematicExpr.getLeft().accept(this);
        for (ASTNode right : arithematicExpr.getRight()) {
            right.accept(this);
        }

        MethodVisitor methodVisitor = classWriter.visitMethod(
                ACC_PUBLIC + ACC_STATIC,
                "evaluate",
                "()I",
                null,
                null
        );
        methodVisitor.visitCode();
        switch (arithematicExpr.getOperator()) {
            case "+" -> methodVisitor.visitInsn(IADD);
            case "-" -> methodVisitor.visitInsn(ISUB);
            case "*" -> methodVisitor.visitInsn(IMUL);
            case "/" -> methodVisitor.visitInsn(IDIV);
            default -> throw new IllegalArgumentException("Unsupported operator: " + arithematicExpr.getOperator());
        }
        methodVisitor.visitEnd();
    }

    @Override
    public void visitForEachNode(ForEachNode forEachNode) {

    }

    @Override
    public void visitForNode(ForNode forEachNode) {

    }

    @Override
    public Pair<String,NumberNode.Type> visitNumberNode(NumberNode numberNode) {
        return new Pair<>(numberNode.value,numberNode.type);
    }


    public void writeClassToFile() {
        try {
            FileOutputStream fos = new FileOutputStream(className + ".class");
            fos.write(classWriter.toByteArray());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}