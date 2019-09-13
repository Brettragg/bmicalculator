package ru.nbki.ali.testprojects.dataaccess;

/**
 * SQL resolver for H2 database.
 * @author Arseniy Lee
 */
public class H2SQLResolver implements IDBSQLResolver {
    @Override
    public String autoIncrement() {
        return "AUTO_INCREMENT";
    }
}
