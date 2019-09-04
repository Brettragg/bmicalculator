package ru.nbki.ali.testprojects;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import ru.nbki.ali.testprojects.dataaccess.DataInputUnit;
import ru.nbki.ali.testprojects.dataaccess.DataLayer;
import ru.nbki.ali.testprojects.dataaccess.DataStorageUnit;
import ru.nbki.ali.testprojects.dataaccess.RAMDataLayer;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Arseniy Lee
 * @version 1.0
 */
public class RAMDataLayerTest {
    private DataLayer dataLayer;

    @Before
    public void initialize() {
        dataLayer = new RAMDataLayer();
        dataLayer.addElement(new DataStorageUnit(new DataInputUnit(48f, 1.83f), 25f, "Some category"));
        dataLayer.addElement(new DataStorageUnit(new DataInputUnit(53f, 1.53f), 40f, "Some other category"));
        dataLayer.addElement(new DataStorageUnit(new DataInputUnit(78f, 1.63f), 45f, "Some tertiary category"));
        dataLayer.addElement(new DataStorageUnit(new DataInputUnit(68f, 1.43f), 50f, "Some last category"));
    }
    @Test
    public void iteratorTest() {
        Iterator<DataStorageUnit> iterator = dataLayer.getDataIterator();
        TestCase.assertTrue(iterator.hasNext());
        TestCase.assertEquals(68f, iterator.next().getWeight());
        TestCase.assertTrue(iterator.hasNext());
        TestCase.assertEquals(1.63f, iterator.next().getHeight());
        TestCase.assertTrue(iterator.hasNext());
        TestCase.assertEquals(40f, iterator.next().getBmi());
        TestCase.assertTrue(iterator.hasNext());
        TestCase.assertEquals("Some category", iterator.next().getCategory());
        TestCase.assertFalse(iterator.hasNext());
    }
    @Test
    public void addElementTest() {
        dataLayer.addElement(new DataStorageUnit(new DataInputUnit(35f, 1.53f), 20f, "Low height"));
        Iterator<DataStorageUnit> iterator = dataLayer.getDataIterator();
        TestCase.assertTrue(iterator.hasNext());
        TestCase.assertEquals("Low height", iterator.next().getCategory());
        for (int i = 0; i < 4; ++i) {
            TestCase.assertTrue(iterator.hasNext());
            iterator.next();
        }
        TestCase.assertFalse(iterator.hasNext());
    }

}
