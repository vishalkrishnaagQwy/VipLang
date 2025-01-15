package org.lang.Services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.sqlite.SQLiteDataSource;

import static org.jooq.impl.DSL.*;

public class DBService {
    private static final String DB_URL = "jdbc:sqlite:src\\test\\java\\db.sqlite";

    public static DSLContext createContext() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(DB_URL);
        Connection connection;
        try {
            connection = dataSource.getConnection();
            return DSL.using(connection, SQLDialect.SQLITE);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating connection", e);
        }
    }

    public static void createPackage(String PackageName){
        DSLContext create = createContext();
        String createTableSql = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "class_name TEXT, " +
                        "package_name TEXT, " +
                        "fields JSON, " +
                        "methods JSON" +
                        ")",
                PackageName
        );

        create.execute(createTableSql);
    }

    public static void insert(String tableName, String className, String packageName, String fields, String methods) {

        /**
         * Sample JSON Structure
         *     Fields:
         * [
         *   {"id": 1, "name": "field1", "type": "int", "visibility": "private"},
         *   {"id": 2, "name": "field2", "type": "String", "visibility": "public"}
         * ]
         *
         * [
         *   {"id": 1, "name": "method1", "return_type": "void", "visibility": "public"},
         *   {"id": 2, "name": "method2", "return_type": "String", "visibility": "private"}
         * ]
         *
         */
        DSLContext create = createContext();
        Table<?> table = table(name(tableName));

        Field<String> classNameField = field("class_name", String.class);
        Field<String> packageNameField = field("package_name", String.class);
        Field<String> fieldsField = field("fields", String.class);
        Field<String> methodsField = field("methods", String.class);

        create.insertInto(table, classNameField, packageNameField, fieldsField, methodsField)
                .values(className, packageName, fields, methods)
                .execute();
    }

    public static List<Record> select(String tableName) {
        DSLContext create = createContext();
        Table<?> table = table(name(tableName));
        return create.select().from(table).fetch();
    }

    public static Result<Record4<String, String, String, String>> read(String tableName, int id) {
        DSLContext create = createContext();
        Table<?> table = table(name(tableName));

        Field<Integer> idField = field("id", Integer.class);
        Field<String> classNameField = field("class_name", String.class);
        Field<String> packageNameField = field("package_name", String.class);
        Field<String> fieldsField = field("fields", String.class);
        Field<String> methodsField = field("methods", String.class);

        return create.select(classNameField, packageNameField, fieldsField, methodsField)
                .from(table)
                .where(idField.eq(id))
                .fetch();
    }


    public static Result<Record1<String>> readOnly(String tableName, int id,String specificField) {
        DSLContext create = createContext();
        Table<?> table = table(name(tableName));
        Field<Integer> idField = field("id", Integer.class);
        switch (specificField) {
            case "class_name" -> {
                Field<String> classNameField = field("class_name", String.class);
                return create.select(classNameField)
                        .from(table)
                        .where(idField.eq(id))
                        .fetch();
            }
            case "package_name" -> {
                Field<String> packageNameField = field("package_name", String.class);
                return create.select(packageNameField)
                        .from(table)
                        .where(idField.eq(id))
                        .fetch();
            }
            case "fields" -> {
                Field<String> fieldsField = field("fields", String.class);
                return create.select(fieldsField)
                        .from(table)
                        .where(idField.eq(id))
                        .fetch();
            }
            case "methods" -> {
                Field<String> methodsField = field("methods", String.class);
                return create.select(methodsField)
                        .from(table)
                        .where(idField.eq(id))
                        .fetch();
            }
            default -> {
                return null;
            }
        }
    }


    public static void update(String tableName, int id, String className, String packageName, String fields, String methods) {
        DSLContext create = createContext();
        Table<?> table = table(name(tableName));

        Field<Integer> idField = field("id", Integer.class);
        Field<String> classNameField = field("class_name", String.class);
        Field<String> packageNameField = field("package_name", String.class);
        Field<String> fieldsField = field("fields", String.class);
        Field<String> methodsField = field("methods", String.class);

        create.update(table)
                .set(classNameField, className)
                .set(packageNameField, packageName)
                .set(fieldsField, fields)
                .set(methodsField, methods)
                .where(idField.eq(id))
                .execute();
    }


    public static void delete(String tableName, int id) {
        DSLContext create = createContext();
        Table<?> table = table(name(tableName));

        Field<Integer> idField = field("id", Integer.class);

        create.deleteFrom(table)
                .where(idField.eq(id))
                .execute();
    }

}
