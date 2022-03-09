package Model.ADTs;

import Model.Exceptions.ADTException;
import Model.Values.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MyHeap<V> implements MyIHeap<V>{
    private HashMap<Integer,V> map;
    AtomicInteger free_loc;

    public MyHeap() {
        map = new HashMap<>();
        free_loc = new AtomicInteger(0);
    }

    @Override
    public int allocate(V value) {
        synchronized (this) {
            map.put(free_loc.incrementAndGet(), value);
            return free_loc.get();
        }
    }

    @Override
    public void deallocate(int addr) throws ADTException {
        synchronized (this) {
            if (!map.containsKey(addr))
                throw new ADTException("Element doesn't exists.");
            map.remove(addr);
        }
    }

    @Override
    public void update(int addr, V value) throws ADTException{
        synchronized (this) {
            if (!map.containsKey(addr))
                throw new ADTException("Element doesn't exists.");
            map.replace(addr, value);
        }
    }

    @Override
    public V lookup(int addr) {
        return map.get(addr);
    }

    @Override
    public boolean isDefined(int addr) {
        return map.containsKey(addr);
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
    public Map<Integer, V> getContent() {
        return map;
    }

    @Override
    public void setContent(Map<Integer,V> map) {
        synchronized (this) {
            this.map = (HashMap<Integer, V>) map;
        }
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
