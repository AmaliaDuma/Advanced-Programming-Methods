package Model.ADTs;
import Model.Exceptions.ADTException;

import java.util.Map;

public interface MyIDictionary<K,V> {
    void add(K key, V value) throws ADTException;
    void remove(K key) throws ADTException;
    void update(K key, V value) throws ADTException;
    V lookup(K key);
    boolean isDefined(K key);
    int size();
    boolean isEmpty();
    Map<K,V> getContent();
    String toString();
}
