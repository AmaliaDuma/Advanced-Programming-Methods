package Model.ADTs;
import Model.Exceptions.ADTException;

import java.util.Stack;

public interface MyIStack<T> {
    T pop() throws ADTException;
    void push(T v);
    boolean isEmpty();
    int size();
    String toString();
    Stack<T> getStack();
}
