package ru.nbki.ali.testprojects.dbc;

import com.google.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Connector that is responsible for getting connections to databases.
 * @author Arseniy Lee
 * @version 1.4
 */
public class SimpleConnector implements IDBConnector {
    private final DataSource dataSource;
    @Inject
    SimpleConnector(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
