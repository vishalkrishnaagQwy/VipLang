package org.lang.vip;

import org.objectweb.asm.MethodVisitor;

import java.util.List;

public class TypeBucket extends ASTNode{
   // only support int | str etc
    List<HintType> hintBucket;
    List<Token> IdBucket=null;
    public List<HintType> getHintBucket() {
        return hintBucket;
    }

    public void setHintBucket(List<HintType> hintBucket) {
        this.hintBucket = hintBucket;
    }

    public List<Token> getIdBucket() {
        return IdBucket;
    }

    public void setIdBucket(List<Token> idbucket) {
        IdBucket = idbucket;
    }



    @Override
    public void accept(AST visitor) {

    }

    @Override
    public void accept(AST visitor, MethodVisitor methodVisitor) {

    }
}
