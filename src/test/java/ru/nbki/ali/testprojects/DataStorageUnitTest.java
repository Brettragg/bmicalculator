package ru.nbki.ali.testprojects;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import ru.nbki.ali.testprojects.dataaccess.DataStorageUnit;

/**
 * @author Arseniy Lee
 */
public class DataStorageUnitTest extends DataInputUnitTest {
    private DataStorageUnit dataStorageUnit;
    private static final float BMI = 10f;
    private static final String CATEGORY = "Input string";
    @Before
    public void initialization() {
        dataStorageUnit = new DataStorageUnit(dataInputUnit, BMI, CATEGORY);
    }
    @Test
    public void getBmiTest() {
        TestCase.assertEquals(BMI, dataStorageUnit.getBmi());
    }
    @Test
    public void getCategoryTest() {
        TestCase.assertEquals(CATEGORY, dataStorageUnit.getCategory());
    }
    @Test
    public void setBmiTest() {
        dataStorageUnit.setBmi(25f);
        TestCase.assertEquals(25f, dataStorageUnit.getBmi());
    }
    @Test
    public void setCategoryTest() {
        dataStorageUnit.setCategory("Some other input string");
        TestCase.assertEquals("Some other input string", dataStorageUnit.getCategory());
    }
}
