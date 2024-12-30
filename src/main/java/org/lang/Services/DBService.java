package org.lang.Services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.sqlite.SQLiteDataSource;

public class DBService {
    private static final String DB_URL = "jdbc:sqlite:software.db";

    private static DSLContext createContext() {
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

    public static void createTable(String tableName) {
        DSLContext create = createContext();
        String createTableSql = String.format("CREATE TABLE IF NOT EXISTS %s (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER)", tableName);
        create.execute(createTableSql);
    }

    public static void insert(String tableName, String name, int age) {
        DSLContext create = createContext();
        Table<?> table = DSL.table(DSL.name(tableName)); // Dynamically reference the table
        Field<?> nameField = DSL.field("name", String.class);
        Field<?> ageField = DSL.field("age", Integer.class);

        create.insertInto(table, nameField, ageField)
                .values(name, age)
                .execute();
    }

    public static List<Record> select(String tableName) {
        DSLContext create = createContext();
        Table<?> table = DSL.table(DSL.name(tableName)); // Dynamically reference the table
        return create.select().from(table).fetch();
    }

    public static void update(String tableName, int id, String newName, int newAge) {
        DSLContext create = createContext();
        Table<?> table = DSL.table(DSL.name(tableName)); // Dynamically reference the table
        Field<?> idField = DSL.field("id", Integer.class);
        Field<?> nameField = DSL.field("name", String.class);
        Field<?> ageField = DSL.field("age", Integer.class);

        create.update(table)
                .set(nameField, newName)
                .set(ageField, newAge)
                .where(idField.eq(id))
                .execute();
    }

    public static <Table> void delete(String tableName, int id) {
        DSLContext create = createContext();
        Table<?> table = DSL.table(DSL.name(tableName)); // Dynamically reference the table
        Field<?> idField = DSL.field("id", Integer.class);

        create.deleteFrom(table)
                .where(idField.eq(id))
                .execute();
    }
}
