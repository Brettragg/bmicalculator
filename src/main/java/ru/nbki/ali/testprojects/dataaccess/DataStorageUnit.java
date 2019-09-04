package ru.nbki.ali.testprojects.dataaccess;

import java.io.Serializable;

/**
 * JavaBean to be stored in the DataLayer and passed to the front-end.
 * @author Arseniy Lee
 */
public class DataStorageUnit extends DataInputUnit implements Serializable {
    /**
     * Body Mass Index
     */
    private float bmi;
    /**
     * Category the object belongs to
     */
    private String category;

    /**
     * No argument constructor to comply with JavaBeans specification.
     */
    public DataStorageUnit() {
    }
    public DataStorageUnit(DataInputUnit dataInputUnit, float bmi, String category) {
        super(dataInputUnit);
        this.bmi = bmi;
        this.category = category;
    }

    /**
     * Copy constructor
     * @param dataStorageUnit Object to copy
     */
    public DataStorageUnit(DataStorageUnit dataStorageUnit) {
        super(dataStorageUnit.getWeight(), dataStorageUnit.getHeight());
        bmi = dataStorageUnit.getBmi();
        category = dataStorageUnit.getCategory();

    }
    public float getBmi() {
        return bmi;
    }
    public String getCategory() {
        return category;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }
    public void setCategory(String category) {
        this.category = category;
    }
}
