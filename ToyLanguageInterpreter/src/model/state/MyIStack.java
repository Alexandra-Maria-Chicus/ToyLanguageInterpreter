package model.state;

import java.util.EmptyStackException;

public interface MyIStack<T> {
    T pop() throws EmptyStackException;
    void push(T item);
    boolean isEmpty();
    String toString();
    MyIStack<T> deepCopy();
}
