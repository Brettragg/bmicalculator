package ru.nbki.ali.testprojects;

import com.google.inject.AbstractModule;
import ru.nbki.ali.testprojects.businesslogic.IBMICalc;
import ru.nbki.ali.testprojects.businesslogic.WHO_BMICalc;
import ru.nbki.ali.testprojects.dataaccess.DBDataLayer;
import ru.nbki.ali.testprojects.dataaccess.IDataLayer;
import ru.nbki.ali.testprojects.dbc.IDBConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Arseniy Lee
 */
public class MockModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IBMICalc.class).to(WHO_BMICalc.class);
        bind(IDataLayer.class).to(DBDataLayer.class);
        bind(IDBConnector.class).to(DBConnectorMock.class);
    }

    static class DBConnectorMock implements IDBConnector {
        @Override
        public Connection getConnection() {
            Connection ans;
            try {
                ans = DriverManager.getConnection("jdbc:h2:mem:h2db;DB_CLOSE_DELAY=-1", "as", "as");
            } catch (SQLException e) {
               throw new RuntimeException(e);
            }
            return ans;
        }
    }
}
