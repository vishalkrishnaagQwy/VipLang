package org.lang.vip;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.lang.memmory.SymbolTable;

import java.io.FileOutputStream;
import java.io.IOException;

public class JavaBytecodeGenerator implements AST, Opcodes {
    private SymbolTable symbolTable;
    private ClassWriter classWriter;
    private String className = "dev_main";

    public JavaBytecodeGenerator(SymbolTable _symbolTable){
        this.symbolTable = _symbolTable;
        this.className = className.replace('.', '/');
        this.classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        // Define the class
        classWriter.visit(V1_8, ACC_PUBLIC, this.className, null, "java/lang/Object", null);

        // Add default constructor
        MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }
    @Override
    public void visitMethodDefNode(MethodDefNode methodDefNode) {
        String functionName = "hello_test";
        MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, functionName, "()V", null, null);
        mv.visitCode();

        // Simple bytecode for demonstration purposes (e.g., printing "Hello World")
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("Hello from " + functionName);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 1);
        mv.visitEnd();
    }

    @Override
    public void visitBlockNode(BlockNode blockNode) {

    }

    @Override
    public void visitMethodCallNode(MethodCallNode methodCallNode) {
        methodCallNode.body.accept(this);
    }

    @Override
    public void visitIFNode(IFNode ifNode) {

    }

    @Override
    public void visitWhileNode(WhileNode whileNode) {

    }

    @Override
    public void visitExperNode(ExprNode exprNode) {

    }

    @Override
    public void visitBooleanExprNode(BooleanExpr booleanExpr) {

    }

    @Override
    public void visitParserExceptionNode(ParserExceptionNode parserExceptionNode) {
        System.out.println("error catched"+parserExceptionNode.errorCode);
    }

    @Override
    public void visitClassDeclNode(ClassDeclNode classDeclNode) {
        System.out.println("reached code gen or class decl");
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