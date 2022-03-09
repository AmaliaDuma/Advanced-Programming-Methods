package Model.ADTs;
import Model.Exceptions.ADTException;

import java.util.ListIterator;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{

    private Stack<T> stack;

    public MyStack() {this.stack = new Stack<>();}

    @Override
    public T pop() throws ADTException {
        if (stack.isEmpty())
            throw new ADTException("Stack is empty.");
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.empty();
    }

    @Override
    public int size() {
        return stack.size();
    }

    public Stack<T> getStack() {return stack;}

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        ListIterator<T> stackIterator = stack.listIterator(stack.size());
        while(stackIterator.hasPrevious()) {
            s.append(stackIterator.previous().toString()).append('\n');
        }
        return s.toString();
    }
}
