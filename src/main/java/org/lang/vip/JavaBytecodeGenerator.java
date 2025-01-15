package org.lang.vip;
import org.lang.exceptions.ExceptionOnCodeAnalysis;
import org.lang.exceptions.ExceptionOnDetailedAnalysis;
import org.lang.utils.Pair;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Label;
import org.lang.memmory.SymbolTable;

import java.io.FileOutputStream;
import java.io.IOException;

public class JavaBytecodeGenerator implements AST, Opcodes {
    private SymbolTable symbolTab;
    private ClassWriter classWriter;
    private String className = "_dev_";
    private String vipCurrentPackage = "vip";
    private String FullPackageName = "compiler.vip";


    public JavaBytecodeGenerator(SymbolTable _symbolTable){
        this.symbolTab = _symbolTable;
        this.className = className.replace('.', '/');
        this.classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        // Define the class
    }
    @Override
    public void visitMethodDefNode(MethodDefNode methodDefNode) {
        String descriptor = "()V";
        MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, methodDefNode.functionName, descriptor, null, null);
        mv.visitCode();
        methodDefNode.body.forEach(element -> element.accept(this,mv));
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 1);
        mv.visitEnd();
    }

    @Override
    public void visitMethodDefNode(MethodDefNode methodDefNode,MethodVisitor methodVisitor) {
        String descriptor = "()V";
        if(methodDefNode.functionName.equals("main"))
        {
            descriptor= "([Ljava/lang/String;)V";
        }
        MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, methodDefNode.functionName, descriptor, null, null);
        mv.visitCode();
        methodDefNode.body.forEach(element -> element.accept(this,mv));
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 1);
        mv.visitEnd();
    }

    @Override
    public void visitBlockNode(BlockNode blockNode) {
       blockNode.getList().forEach(block -> {
           try {
               block.accept(this);
           } catch (ExceptionOnCodeAnalysis e) {
               throw new ExceptionOnDetailedAnalysis(e);
           }
       });
    }

    @Override
    public void visitBlockNode(BlockNode blockNode,MethodVisitor methodVisitor) {
        blockNode.getList().forEach(block -> block.accept(this,methodVisitor));
    }

    @Override
    public void visitMethodCallNode(MethodCallNode methodCallNode,MethodVisitor Method) {
        Method.visitCode();

        if(methodCallNode.methodName.equals("print"))
        {
            // Add bytecode for System.out.println("Hello");
            Method.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            methodCallNode.expr.accept(this,Method);
            Method.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            Method.visitInsn(RETURN);
            Method.visitMaxs(2, 1);
            Method.visitEnd();
        }
        else {
            methodCallNode.expr.accept(this,Method);
        }

    }

    @Override
    public void visitMethodCallNode(MethodCallNode methodCallNode) {
       //method call node
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

    }

    @Override
    public void visitBooleanExprNode(BooleanExpr booleanExpr,MethodVisitor methodVisitor) throws ExceptionOnCodeAnalysis {
        booleanExpr.getLeft().accept(this);
        booleanExpr.getRight().forEach(right -> {
            try {
                right.accept(this);
            } catch (ExceptionOnCodeAnalysis e) {
                throw new ExceptionOnDetailedAnalysis(e);
            }
        });
        methodVisitor.visitCode();
        switch (booleanExpr.getOperator()) {
            case "+" -> methodVisitor.visitInsn(IADD);
            case "-" -> methodVisitor.visitInsn(ISUB);
            case "*" -> methodVisitor.visitInsn(IMUL);
            case "/" -> methodVisitor.visitInsn(IDIV);
            default -> throw new IllegalArgumentException("Unsupported operator: " + booleanExpr.getOperator());
        }
        methodVisitor.visitEnd();

    }

    @Override
    public void visitParserExceptionNode(ParserExceptionNode parserExceptionNode) {
        System.out.println("error catched"+parserExceptionNode.errorCode);
    }

    @Override
    public void visitClassDeclNode(ClassDeclNode classDeclNode) {
        this.className = classDeclNode.getClassName();
        classDeclNode.getPackage().accept(this);
        this.vipCurrentPackage = classDeclNode.getPackage().getCurrentPackage();
        this.FullPackageName = classDeclNode.getPackage().getPackageRoute();
        symbolTab.defineClass(classDeclNode.getClassId(),className);
        classWriter.visit(V1_8, ACC_PUBLIC, this.className, null, "java/lang/Object", null);
        // Add default constructor
        MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        classDeclNode.classBody.accept(this,mv);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

   }

    @Override
    public void visitClassObjectNode(ClassObjectDeclNode objClassNode) {

    }

    @Override
    public void visitClassObjectNode(ClassObjectDeclNode objClassNode, MethodVisitor methodVisitor) {

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
    public void visitStringLiteralNode(StringLiteralNode stringLiteralNode,MethodVisitor methodVisitor) {
        methodVisitor.visitLdcInsn(stringLiteralNode.value);
    }

    @Override
    public void visitVersionNode(VersionNode versionNode) {

    }

    @Override
    public String visitVariableNode(VariableNode variableNode) {
          return variableNode.name;
    }

    @Override
    public void visitArithematicExpr(ArithematicExpr arithematicExpr) {

    }

    @Override
    public void visitArithematicExpr(ArithematicExpr arithematicExpr,MethodVisitor methodVisitor) throws ExceptionOnCodeAnalysis {
        arithematicExpr.getLeft().accept(this);
        arithematicExpr.getRight().forEach(right -> {
            try {
                right.accept(this);
            } catch (ExceptionOnCodeAnalysis e) {
                throw new ExceptionOnDetailedAnalysis(e);
            }
        });
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

    @Override
    public void visitNumberNode(NumberNode numberNode, MethodVisitor methodVisitor) {
        switch (numberNode.type)
        {
            case INT -> {
                methodVisitor.visitLdcInsn(Integer.parseInt(numberNode.value));
                methodVisitor.visitVarInsn(Opcodes.ISTORE, 1);
            }
            case FLOAT -> {
                methodVisitor.visitLdcInsn(Float.parseFloat(numberNode.value));
                methodVisitor.visitVarInsn(Opcodes.FSTORE, 1);
            }
            case DOUBLE -> {
                methodVisitor.visitLdcInsn(Double.parseDouble(numberNode.value));
                methodVisitor.visitVarInsn(Opcodes.DSTORE, 1);
            }
        }

    }

    @Override
    public void visitAssignmentNode(AssignmentNode assignmentNode) throws ExceptionOnCodeAnalysis {
        assignmentNode.variable.accept(this);
        assignmentNode.expr.accept(this);
    }

    @Override
    public void visitVarDeclNode(VarDeclNode varDeclNode) {

    }

    @Override
    public void visitVarDeclNode(VarDeclNode varDeclNode, MethodVisitor methodVisitor) {

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