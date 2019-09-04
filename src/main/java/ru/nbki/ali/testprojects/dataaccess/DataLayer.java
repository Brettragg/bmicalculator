package ru.nbki.ali.testprojects.dataaccess;

import java.util.Iterator;

/**
 * An interface exposed by data layer.
 *
 * @author Arseniy Lee
 * @version 1.0
 */
public interface DataLayer {
    /**
     * returns an {@link Iterator} to data.
     * @return {@link Iterator} to data.
     */
    Iterator<DataStorageUnit> getDataIterator();

    /**
     * Adds an element to data.
     * @param dataStorageUnit An element to add.
     */
    void addElement(DataStorageUnit dataStorageUnit);
}
