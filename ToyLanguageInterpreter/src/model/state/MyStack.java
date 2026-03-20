package model.state;

import java.util.EmptyStackException;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{
    private final Stack<T> exeStack;
    public MyStack(){
        exeStack = new Stack<>();
    }
    @Override
    public T pop() throws EmptyStackException {
        if(exeStack.isEmpty()){
            throw new EmptyStackException();
        }
        return exeStack.pop();
    }

    @Override
    public void push(T item) {
        exeStack.push(item);
    }

    @Override
    public boolean isEmpty() {
        return exeStack.isEmpty();
    }

    @Override
    public MyIStack<T> deepCopy() {
        MyStack<T> newStack = new MyStack<>();
        newStack.exeStack.addAll(exeStack);
        return newStack;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ExeStack:"); // Required Header

        for (int i = exeStack.size() - 1; i >= 0; i--) {
            builder.append("\n").append(exeStack.get(i).toString());
        }
        return builder.toString();
    }
}
