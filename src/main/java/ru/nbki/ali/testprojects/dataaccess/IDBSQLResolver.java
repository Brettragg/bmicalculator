package ru.nbki.ali.testprojects.dataaccess;

/**
 * Resolves SQL code of provided database based on concrete implementation.
 * @author Arseniy Lee
 * @version 1.5
 */
public interface IDBSQLResolver {
    /**
     * Resolves auto increment syntax that is different in some databases.
     * @return Auto increment syntax
     */
    String autoIncrement(); // Ideally methods of this interface could return
                            // java.sql.Statement instead of String, but it
                            // wasn't necessary in the scope of this project.
}
