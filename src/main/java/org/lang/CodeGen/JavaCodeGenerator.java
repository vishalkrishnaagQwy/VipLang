package org.lang.CodeGen;

import org.lang.AstNode.*;
import org.lang.exceptions.ExceptionOnCodeAnalysis;
import org.lang.exceptions.ExceptionOnDetailedAnalysis;
import org.lang.memmory.SymbolTable;
import org.lang.utils.Pair;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JavaCodeGenerator implements AST {
    private SymbolTable symbolTab;
    private String className = "_dev_";
    private String vipCurrentPackage = "vip";
    private String FullPackageName = "compiler.vip";
    private List<String> code;


    public JavaCodeGenerator(SymbolTable _symbolTable){

        this.symbolTab = _symbolTable;
        this.className = className.replace('.', '/');
        this.code = new ArrayList<>();

        // Define the class
    }
    @Override
    public void visitMethodDefNode(MethodDefNode methodDefNode) {
        code.add(methodDefNode.getFunctionName() + "{");
        methodDefNode.body.forEach(element -> {
            try {
                element.accept(this);
            } catch (ExceptionOnCodeAnalysis e) {
                throw new ExceptionOnDetailedAnalysis(e);
            }
        });
        code.add("}\n");
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
    public void visitMethodCallNode(MethodCallNode methodCallNode) {
       //method call node
        code.add(methodCallNode.methodName + "(" + methodCallNode.expr+")");
    }

    @Override
    public void visitIFNode(IFNode ifNode) {

    }

    @Override
    public void visitWhileNode(WhileNode whileNode) {

    }


    @Override
    public void visitExperNode(ExprNode exprNode) {
        exprNode.accept(this);
    }

    @Override
    public void visitBooleanExprNode(BooleanExpr booleanExpr) {

    }


    @Override
    public void visitParserExceptionNode(ParserExceptionNode parserExceptionNode) {
        System.out.println("error catched"+parserExceptionNode.errorCode);
    }

    @Override
    public void visitClassDeclNode(ClassDeclNode classDeclNode) throws ExceptionOnCodeAnalysis {

        this.className = classDeclNode.getClassName();
        classDeclNode.getPackage().accept(this);
        this.vipCurrentPackage = classDeclNode.getPackage().getCurrentPackage();
        this.FullPackageName = classDeclNode.getPackage().getPackageRoute();
        symbolTab.defineClass(classDeclNode.getClassId(),className);
        code.add("public class "+className+ "{ \n");
//        DBService.insert(vipCurrentPackage,className,FullPackageName,"[]","[]");
        classDeclNode.classBody.accept(this);
        code.add("}\n");

   }

    @Override
    public void visitClassObjectNode(ClassObjectDeclNode objClassNode) {
         code.add(objClassNode.getClassName()+ " "+objClassNode.getParams());
    }


    @Override
    public void visitObjectDeclNode(ObjectDeclNode objectDeclNode) {

    }

    @Override
    public void visitPackageDeclNode(PackageDeclNode packageDeclNode) {
        code.add("package "+ packageDeclNode.getCurrentPackage()+ ";\n");
    }

    @Override
    public void visitStringLiteralNode(StringLiteralNode stringLiteralNode) {
        code.add(stringLiteralNode.getValue());
    }

    @Override
    public void visitVersionNode(VersionNode versionNode) {
        code.add("// "+ versionNode.getVersion()+ "\n");
    }

    @Override
    public String visitVariableNode(VariableNode variableNode)
    {
        code.add(variableNode.getName());
          return "";
    }

    @Override
    public void visitArithematicExpr(ArithematicExpr arithematicExpr) {

    }


    @Override
    public void visitForEachNode(ForEachNode forEachNode) {

    }

    @Override
    public void visitForNode(ForNode forEachNode) {

    }

    @Override
    public Pair<String,NumberNode.Type> visitNumberNode(NumberNode numberNode) {
        return new Pair<>(numberNode.getValue(), numberNode.getType());
    }

    @Override
    public void visitAssignmentNode(AssignmentNode assignmentNode) throws ExceptionOnCodeAnalysis {
        assignmentNode.getVariable().accept(this);
        assignmentNode.getExpr().accept(this);
    }

    @Override
    public void visitVarDeclNode(VarDeclNode varDeclNode) {

    }


    @Override
    public void visitVipInterfaceDeclNode(VipInterfaceDeclNode vipInterfaceDeclNode) {

    }



    public void writeClassToFile() {
        try {
            FileOutputStream fos = new FileOutputStream(className + ".java");
            fos.write(String.join(" ", code).getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}