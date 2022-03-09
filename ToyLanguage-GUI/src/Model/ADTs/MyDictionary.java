package Model.ADTs;

import Model.Exceptions.ADTException;

import java.util.HashMap;
import java.util.Map;


public class MyDictionary<K,V> implements MyIDictionary<K,V>{

    private HashMap<K,V> map;

    public MyDictionary() {map = new HashMap<>();}

    @Override
    public void add(K key, V value) throws ADTException {
        synchronized (this) {
            if (map.containsKey(key))
                throw new ADTException("Element already exists.");
            map.put(key, value);
        }
    }

    @Override
    public void remove(K key) throws ADTException {
        synchronized (this) {
            if (!map.containsKey(key))
                throw new ADTException("Element doesn't exists.");
            map.remove(key);
        }
    }

    @Override
    public void update(K key, V value) throws ADTException {
        synchronized (this) {
            if (!map.containsKey(key))
                throw new ADTException("Element doesn't exists.");
            map.replace(key, value);
        }
    }

    @Override
    public V lookup(K key) {
        return map.get(key);
    }

    @Override
    public boolean isDefined(K key) {
        return map.containsKey(key);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Map<K, V> getContent() {
        return map;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(var elem: map.keySet()) {
            if (elem != null)
                s.append(elem.toString()).append(" -> ").append(map.get(elem).toString()).append('\n');
        }
        return s.toString();
    }
}
