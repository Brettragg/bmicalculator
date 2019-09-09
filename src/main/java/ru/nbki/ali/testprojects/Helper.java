package ru.nbki.ali.testprojects;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Arseniy Lee
 */
public class Helper {
    public static <T> ArrayList<T> getList(Iterator<T> iterator) {
        ArrayList<T> array = new ArrayList<>();
        while(iterator.hasNext()) {
            array.add(iterator.next());
        }
        return array;
    }
}
