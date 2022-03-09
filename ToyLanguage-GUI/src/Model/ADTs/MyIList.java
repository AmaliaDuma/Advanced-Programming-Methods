package Model.ADTs;

import java.util.ArrayList;

public interface MyIList<T> {
    boolean append(T obj);
    T get(int index);
    boolean isEmpty();
    int size();
    void clear();
    String toString();
    ArrayList<T> getList();
}
