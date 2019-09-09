package ru.nbki.ali.testprojects.businesslogic;

import ru.nbki.ali.testprojects.dataaccess.DataInputUnit;

/**
 * An interface exposed by business logic layer.
 *
 * @author Arseniy Lee
 * @version 1.0
 */
public interface IBMICalc {
    /**
     * Calculates Body Mass Index of {@link DataInputUnit}.
     * @param dataUnit A {@link DataInputUnit} object.
     * @return Body Mass Index
     */
    float getBMI(DataInputUnit dataUnit);

    /**
     * Returns a String specifying a category that {@link DataInputUnit} belongs to.
     * @param bmi Body Mass Index
     * @return A String that contains category name.
     */
    String getCategory(float bmi);
}
