package ru.nbki.ali.testprojects.dataaccess;

/**
 * SQL resolver for PostgreSQL database.
 * @author Arseniy Lee
 */
public class PostgreSQLResolver  implements IDBSQLResolver {
    @Override
    public String autoIncrement() {
        return "GENERATED ALWAYS AS IDENTITY";
    }
}
