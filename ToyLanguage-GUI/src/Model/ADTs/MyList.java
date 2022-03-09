package Model.ADTs;

import java.util.ArrayList;

public class MyList<T> implements MyIList<T>{

    private ArrayList<T> list;

    public MyList() { list = new ArrayList<>();}

    @Override
    public boolean append(T obj) {
        return list.add(obj);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void clear() {
        list.clear();
    }

    public ArrayList<T> getList() {return list;}

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (T elem: list) {
            if (elem != null) {
                s.append(elem.toString());
                s.append("\n");
            }
        }
        return s.toString();
    }
}
