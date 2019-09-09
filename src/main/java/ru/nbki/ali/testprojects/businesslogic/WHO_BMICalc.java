package ru.nbki.ali.testprojects.businesslogic;

import ru.nbki.ali.testprojects.dataaccess.DataInputUnit;

/**
 * A basic {@link IBMICalc} implementation that is based on
 * World Health Organization's formula and category thresholds.
 *
 * @author Arseniy Lee
 * @version 1.0
 */
public class WHO_BMICalc implements IBMICalc {
    @Override
    public float getBMI(DataInputUnit dataInputUnit) {
        return dataInputUnit.getWeight() / dataInputUnit.getHeightInMeters() / dataInputUnit.getHeightInMeters();
    }

    @Override
    public String getCategory(float bmi) {
        return WHO_BMICategory.getCategory(bmi);
    }
}
