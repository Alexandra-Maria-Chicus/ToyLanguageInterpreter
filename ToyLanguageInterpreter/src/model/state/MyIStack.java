package model.state;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;

public interface MyIStack<T> {
    T pop() throws EmptyStackException;
    void push(T item);
    boolean isEmpty();
    String toString();
    MyIStack<T> deepCopy();
    List<T> getStackAsList();
}
