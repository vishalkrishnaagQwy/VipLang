package org.lang.memmory;

public class Methods {
    private String methodName;
    private Integer classId;
    private int methodId;
    private int callCount;


    public int getCallCount() {
        return callCount;
    }

    public void setCallCount(int callCount) {
        this.callCount = callCount;
    }
    public int getMethodId() {
        return methodId;
    }

    public void setMethodId(int methodId) {
        this.methodId = methodId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Methods(String methodName, Integer classId) {
        this.methodName = methodName;
        this.classId = classId;

    }
}
