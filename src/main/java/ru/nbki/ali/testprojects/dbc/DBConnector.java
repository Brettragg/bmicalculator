package ru.nbki.ali.testprojects.dbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Layer interface that is responsible for database connection.
 * @author Arseniy Lee
 * @version 1.4
 */
public interface DBConnector {
    Connection getConnection() throws SQLException;
}
