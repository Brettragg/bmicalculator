package ru.nbki.ali.testprojects.dataaccess;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A {@link IDataLayer} implementation that stores data values in Random Access Memory.
 *
 * @author Arseniy Lee
 * @version 1.0
 */
public class RAMDataLayer implements IDataLayer {
    /**
     * An {@link List<DataStorageUnit>} that stores values.
     */
    private List<DataStorageUnit> dataSyncList;

    /**
     * A simple constructor
     */
    public RAMDataLayer() {
        this.dataSyncList = new CopyOnWriteArrayList<> ();
    }

    @Override
    public Iterator<DataStorageUnit> getDataIterator() {
        return dataSyncList.iterator();
    }

    @Override
    public void addElement(DataStorageUnit dataStorageUnit) {
        dataSyncList.add(0, dataStorageUnit);
    }
}
