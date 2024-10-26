package org.lang.memmory;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SymbolTable {
    private Stack<Map<String,Object>> scopes;

    public SymbolTable() {
        scopes = new Stack<>();
        enterScope(); // Start with a global scope
    }

    public void enterScope() {
        scopes.push(new HashMap<>());
    }

    public void exitScope() {
        if (!scopes.isEmpty()) {
            scopes.pop();
        }
    }

    public void define(String name,Object value) {
        scopes.peek().put(name, value);
    }
    public void undefine(String name){
        scopes.peek().remove(name);
    }

    public Object lookup(String name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Map<String, Object> scope = scopes.get(i);
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        return null; // Not found
    }

    public Object findClass(String name,Object object) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Map<String, Object> scope = scopes.get(i);
            if (scope.containsKey(name) && scope.get(name) instanceof Classes) {
                return scope.get(name);
            }
        }
        return null; // Not found
    }

    public Object findMethod(String name,Object object) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Map<String, Object> scope = scopes.get(i);
            if (scope.containsKey(name) && scope.get(name) instanceof Methods) {
                return scope.get(name);
            }
        }
        return null; // Not found
    }


    public Object findField(String name,Object object) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Map<String, Object> scope = scopes.get(i);
            if (scope.containsKey(name) && scope.get(name) instanceof Fields) {
                return scope.get(name);
            }
        }
        return null; // Not found
    }

    // find will only look up local scope
    public Object find(String name) {
            Map<String, Object> scope = scopes.getLast();
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        return null; // Not found
    }

    public void defineClass(String className) {
        define(className, new Classes(className));
        enterScope();
    }

    public void defineMethod(int ClassId, String methodName) {
        define(methodName, new Methods(methodName,ClassId));
    }
    public void undefineMethod(String methodName) {
        undefine(methodName);
    }

    public void defineField(String className, String fieldName, Object initialValue) {
        define(fieldName, new Fields(fieldName));
    }

    public void undefineField(String className, String fieldName, Object initialValue) {
        undefine(fieldName);
    }


    public void exitClassScope() {
        exitScope(); // Exit the class scope when done defining methods and fields
    }

}
