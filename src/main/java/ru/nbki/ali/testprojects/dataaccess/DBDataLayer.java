package ru.nbki.ali.testprojects.dataaccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

/**
 * A {@link DataLayer} implementation that stores data values in PostgreSQL database.
 * @author Arseniy Lee
 * @version 1.3
 */
public class DBDataLayer implements DataLayer {
    /**
     * Stored {@link java.sql.Connection}.
     */
    private Connection connection;
    public DBDataLayer() {
        String url = "jdbc:postgresql://localhost:5432/bmidb";
        Properties props = new Properties();
        props.setProperty("user","bmiuser");
        props.setProperty("password","ogbrett");
                    //props.setProperty("ssl","true"); DOES NOT SUPPORT SSL
        try {
             connection = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO: 03.09.2019 Is there a better way to handle those?
    }
    }
    @Override
    public Iterator<DataStorageUnit> getDataIterator() {
        ArrayList<DataStorageUnit> arrayList = new ArrayList<>();
        try {
            ResultSet results = generateStatement().executeQuery("SELECT * FROM bmicalcdata;");
            while(results.next()) {
                float height = results.getFloat("HEIGHT");
                float weight= results.getFloat("WEIGHT");
                float bmi = results.getFloat("BMI");
                String category = results.getString("CATEGORY");    // Category is stored in varchar(30). It could be stored more efficiently.
                arrayList.add(new DataStorageUnit(new DataInputUnit(weight, height), bmi, category));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);  // TODO: 03.09.2019 NOW, HANDLE IT!
        }

        return arrayList.iterator();
    }

    @Override
    public void addElement(DataStorageUnit dataStorageUnit) {
        try {
            generateStatement().execute("INSERT INTO bmicalcdata (height, weight, bmi, category) VALUES (" +
                    dataStorageUnit.getHeight() + ", " +
                    dataStorageUnit.getWeight() + ", " +
                    dataStorageUnit.getBmi() + ", '" +
                    dataStorageUnit.getCategory() + "')");
        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO: 03.09.2019 Handle this as well.
        }
    }
    private Statement generateStatement() throws SQLException {
        return connection.createStatement();
    }

}
