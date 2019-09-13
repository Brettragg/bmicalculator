package ru.nbki.ali.testprojects.dataaccess;

import com.google.inject.Inject;
import ru.nbki.ali.testprojects.dbc.IDBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A {@link IDataLayer} implementation that stores data values in PostgreSQL database.
 * @author Arseniy Lee
 * @version 1.3
 */
public class DBDataLayer implements IDataLayer {
    /**
     * Stored {@link IDBConnector} that retrieves {@link Connection} object.
     */
    private final IDBConnector connector;
    /**
     * Resolves SQL code based on provided implementation.
     */
    private final IDBSQLResolver sqlResolver;

    /**
     * Simple injectable constructor
     * @param connector {@link IDBConnector} to set.
     */
    @Inject
    public DBDataLayer(IDBConnector connector, IDBSQLResolver sqlResolver) {
        this.connector = connector;
        this.sqlResolver = sqlResolver;
    }
    @Override
    public Iterator<DataStorageUnit> getDataIterator() {
        checkDataBaseIntegrity();
        ArrayList<DataStorageUnit> arrayList = new ArrayList<>();

        try (Connection connection = generateConnection();
             Statement stmt = connection.createStatement();
             ResultSet results = stmt.executeQuery("SELECT * FROM bmicalcdata ORDER BY ID DESC;")) {
            while(results.next()) {
                float height = results.getFloat("HEIGHT");
                float weight= results.getFloat("WEIGHT");
                float bmi = results.getFloat("BMI");
                String category = results.getString("CATEGORY");    // Category is stored in varchar(30). It could be stored more efficiently.
                arrayList.add(new DataStorageUnit(new DataInputUnit(weight, height), bmi, category));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return arrayList.iterator();
    }

    @Override
    public void addElement(DataStorageUnit dataStorageUnit) {
        checkDataBaseIntegrity();
        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO bmicalcdata" +
                     " (height, weight, bmi, category) VALUES (?, ?, ?, ?);")) {
            ps.setFloat(1, dataStorageUnit.getHeight());
            ps.setFloat(2, dataStorageUnit.getWeight());
            ps.setFloat(3, dataStorageUnit.getBmi());
            ps.setString(4, dataStorageUnit.getCategory());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkDataBaseIntegrity() {
        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement()){
            statement.execute("CREATE TABLE IF NOT EXISTS bmicalcdata(id int " + sqlResolver.autoIncrement() +"," +
                    " height real, weight real, bmi real, category varchar(30));");
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

    }
    private Connection generateConnection() throws SQLException {
        return connector.getConnection();
    }

}
