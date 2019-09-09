package ru.nbki.ali.testprojects;

import com.google.inject.Guice;
import com.google.inject.Injector;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.nbki.ali.testprojects.dataaccess.*;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Arseniy Lee
 * @version 1.0
 */
@RunWith(Parameterized.class)
public class IDataLayerTest {
    private static Injector injector = Guice.createInjector(new MockModule());
    @Parameterized.Parameter
    public Class listClass;

    @Parameterized.Parameters
    public static Iterable<Object[]> DataLayer () {
        return Arrays.asList(new Object[][] {{RAMDataLayer.class}, {DBDataLayer.class}});
    }

    private IDataLayer initialize() {
        IDataLayer dataLayer;
        dataLayer = (IDataLayer)injector.getInstance(listClass);
        dataLayer.addElement(new DataStorageUnit(new DataInputUnit(48f, 183f), 25f, "Some category"));
        dataLayer.addElement(new DataStorageUnit(new DataInputUnit(53f, 153f), 40f, "Some other category"));
        dataLayer.addElement(new DataStorageUnit(new DataInputUnit(78f, 163f), 45f, "Some tertiary category"));
        dataLayer.addElement(new DataStorageUnit(new DataInputUnit(68f, 143f), 50f, "Some last category"));
        return dataLayer;
    }
    @Test
    public void iteratorTest() {
        IDataLayer dataLayer = initialize();
        Iterator<DataStorageUnit> iterator = dataLayer.getDataIterator();
        TestCase.assertTrue(iterator.hasNext());
        TestCase.assertEquals(68f, iterator.next().getWeight());
        TestCase.assertTrue(iterator.hasNext());
        TestCase.assertEquals(163f, iterator.next().getHeight());
        TestCase.assertTrue(iterator.hasNext());
        TestCase.assertEquals(40f, iterator.next().getBmi());
        TestCase.assertTrue(iterator.hasNext());
        TestCase.assertEquals("Some category", iterator.next().getCategory());
        TestCase.assertFalse(iterator.hasNext());
    }
    @Test
    public void addElementTest() {
        IDataLayer dataLayer = initialize();
        dataLayer.addElement(new DataStorageUnit(new DataInputUnit(35f, 153f), 20f, "Low height"));
        Iterator<DataStorageUnit> iterator = dataLayer.getDataIterator();
        TestCase.assertTrue(iterator.hasNext());
        TestCase.assertEquals("Low height", iterator.next().getCategory());
        for (int i = 0; i < 4; ++i) {
            TestCase.assertTrue(iterator.hasNext());
            iterator.next();
        }
    }


}
