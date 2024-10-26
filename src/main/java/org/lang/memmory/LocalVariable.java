package org.lang.memmory;

public class LocalVariable{
    private Object data;
    private int label;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public int getLabel() {
        return label;
    }
    public LocalVariable(String name) {
    }
}
