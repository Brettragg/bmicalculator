package ru.nbki.ali.testprojects;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import ru.nbki.ali.testprojects.dataaccess.DataInputUnit;

/**
 * @author Arseniy Lee
 */
public class DataInputUnitTest {
    DataInputUnit dataInputUnit;
    private static final float TEST_WEIGHT = 47f, TEST_HEIGHT = 173f;
    @Before
    public void init() {
        dataInputUnit = new DataInputUnit(TEST_WEIGHT, TEST_HEIGHT);
    }
    @Test
    public void getWeightTest() {
        TestCase.assertEquals(TEST_WEIGHT, dataInputUnit.getWeight());
    }
    @Test
    public void getHeightTest() {
        TestCase.assertEquals(TEST_HEIGHT, dataInputUnit.getHeight());
    }
    @Test
    public void setWeightTest() {
        dataInputUnit.setWeight(56f);
        TestCase.assertEquals(56f, dataInputUnit.getWeight());
    }
    @Test
    public void setHeightTest() {
        dataInputUnit.setHeight(183f);
        TestCase.assertEquals(183f, dataInputUnit.getHeight());
    }
    @Test
    public void cmToMeterTest() {
        TestCase.assertEquals(3.5f, DataInputUnit.cmToMeter(350f));
    }
    @Test
    public void meterToCmTest() {
        TestCase.assertEquals(273f, DataInputUnit.meterToCm(2.73f));
    }
}
