package Model.ADTs;

import Model.Exceptions.ADTException;
import Model.Values.Value;

import java.util.Map;

public interface MyIHeap<V> {
    int allocate(V value);
    void deallocate(int addr) throws ADTException;
    void update(int addr, V value) throws ADTException;
    V lookup(int addr);
    boolean isDefined(int addr);
    int size();
    boolean isEmpty();
    Map<Integer, V> getContent();
    void setContent(Map<Integer,V> map);
    String toString();

}
