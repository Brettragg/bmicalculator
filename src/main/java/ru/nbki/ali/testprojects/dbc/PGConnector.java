package ru.nbki.ali.testprojects.dbc;

import com.google.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Arseniy Lee
 */
public class PGConnector implements DBConnector {
    private DataSource dataSource;
    @Inject
    PGConnector(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public Connection getConnection() throws SQLException{
        return dataSource.getConnection();
    }
}
