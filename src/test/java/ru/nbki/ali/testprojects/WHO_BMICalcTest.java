package ru.nbki.ali.testprojects;

import junit.framework.TestCase;
import org.junit.Test;
import ru.nbki.ali.testprojects.businesslogic.WHO_BMICalc;
import ru.nbki.ali.testprojects.dataaccess.DataInputUnit;


public class WHO_BMICalcTest {
    @Test
    public void getBMITest() {
        DataInputUnit dataInputUnit= new DataInputUnit(80f, 200f);
        WHO_BMICalc bmiCalc = new WHO_BMICalc();
        TestCase.assertEquals(20f, bmiCalc.getBMI(dataInputUnit));
    }

    @Test
    public void getCategoryTest() {
        WHO_BMICalc bmiCalc = new WHO_BMICalc();
        TestCase.assertEquals("Normal", bmiCalc.getCategory(20f));
    }
}
