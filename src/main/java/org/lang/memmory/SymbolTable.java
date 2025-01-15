package org.lang.memmory;

import org.lang.Services.DBService;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SymbolTable {
    private DBService dbService;
    private Stack<Map<Integer,Object>> scopes;

    public SymbolTable() {
        scopes = new Stack<>();
        createScope(); // Start with a global scope
    }

    public void createScope() {
        scopes.push(new HashMap<>());
    }

    public void exitScope() {
        if (!scopes.isEmpty()) {
            scopes.pop();
        }
    }

    public void define(Integer id,Object value) {
        scopes.peek().put(id, value);
    }
    public void undefine(int id){
        scopes.peek().remove(id);
    }

    public Object lookup(String name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Map<Integer, Object> scope = scopes.get(i);
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        return null; // Not found
    }

    public Object findClass(Integer id,Object object) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Map<Integer, Object> scope = scopes.get(i);
            if (scope.containsKey(id) && scope.get(id) instanceof Classes) {
                return scope.get(id);
            }
        }

        return null; // Not found
    }

    public Object findMethod(Integer id,Object object) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Map<Integer, Object> scope = scopes.get(i);
            if (scope.containsKey(id) && scope.get(id) instanceof Methods) {
                return scope.get(id);
            }
        }
        return null; // Not found
    }


    public Object findField(Integer id,Object object) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Map<Integer, Object> scope = scopes.get(i);
            if (scope.containsKey(id) && scope.get(id) instanceof Fields) {
                return scope.get(id);
            }
        }
        return null; // Not found
    }

    // find will only look up local scope
    public Object find(Integer id) {
            Map<Integer, Object> scope = scopes.peek();
            if (scope.containsKey(id)) {
                return scope.get(id);
            }
        return null; // Not found
    }

    public void defineClass(int classId,String className) {
        define(classId, new Classes(className));
        createScope();
    }

    public void defineMethod(int ClassId, String methodName) {
        define(ClassId, new Methods(methodName,ClassId));
    }
    public void undefineMethod(int methodid) {
        undefine(methodid);
    }

    public void defineField(Integer classId, String fieldName, Object initialValue) {
        define(classId, new Fields(fieldName));
    }

    public void undefineField(int classId, String fieldName, Object initialValue) {

        undefine(classId);
    }


    public void exitClassScope() {

        exitScope(); // Exit the class scope when done defining methods and fields
    }

}
