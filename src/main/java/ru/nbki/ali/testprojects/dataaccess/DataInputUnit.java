package ru.nbki.ali.testprojects.dataaccess;

import java.io.Serializable;

/**
 * An input JavaBean that contains weight and height.
 *
 * @author Arseniy Lee
 * @version 1.1
 */
public class DataInputUnit implements Serializable {
    private final static int CM_IN_M = 100;
    /**
     * Contains weight in kilos.
     */
    private float weight;
    /**
     * Contains height in centimeters.
     */
    private float height;

    /**
     * Public no-argument constructor to adhere to JavaBean specification.
     */
    public DataInputUnit() {
    }
    public DataInputUnit(float weight, float height) {
        this.weight = weight;
        this.height = height;
    }

    /**
     * Copy constructor
     * @param dataInputUnit {@link DataInputUnit} to copy
     */
    DataInputUnit(DataInputUnit dataInputUnit) {
        weight = dataInputUnit.weight;
        height = dataInputUnit.height;
    }

    /**
     * Returns weight parameter of the implementing object.
     * @return Weight parameter.
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Returns height in centimeters of the implementing object.
     * @return Height parameter.
     */
    public float getHeight() {
        return height;
    }
    public float getHeightInMeters() {
        return cmToMeter(height);
    }
    public void setWeight (float weight) {
        this.weight = weight;
    }
    public void setHeight (float height) {
        this.height = height;
    }
    public static float cmToMeter(float cm) {
        return cm / CM_IN_M;
    }
    public static float meterToCm (float m) {
        return m * CM_IN_M;
    }
}
