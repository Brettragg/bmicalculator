package ru.nbki.ali.testprojects.dataaccess;

import com.google.inject.Inject;
import ru.nbki.ali.testprojects.dbc.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A {@link DataLayer} implementation that stores data values in PostgreSQL database.
 * @author Arseniy Lee
 * @version 1.3
 */
public class DBDataLayer implements DataLayer {
    /**
     * Stored {@link DBConnector} that retrieves {@link Connection} object.
     */
    private DBConnector connector;

    /**
     * Simple injectable constructor
     * @param connector {@link DBConnector} to set.
     */
    @Inject
    public DBDataLayer(DBConnector connector) {
        this.connector = connector;
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
             Statement statement = connection.createStatement() ){
            statement.execute("CREATE TABLE IF NOT EXISTS bmicalcdata(id int AUTO_INCREMENT," +
                    " height real, weight real, bmi real, category varchar(30));");
        } catch (SQLException e1) {
            try (Connection connection = connector.getConnection();
                 Statement statement = connection.createStatement() ){
                statement.execute("CREATE TABLE IF NOT EXISTS bmicalcdata(id int GENERATED ALWAYS AS IDENTITY," +
                        " height real, weight real, bmi real, category varchar(30));");
            } catch (SQLException e2) {
                throw new RuntimeException(e2);
            }
        }

    }
    private Connection generateConnection() throws SQLException {
        return connector.getConnection();
    }

}
