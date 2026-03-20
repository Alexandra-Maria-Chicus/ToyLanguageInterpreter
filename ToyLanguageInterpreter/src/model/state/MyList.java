package model.state;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T>{
    private final List<T> outputList;

    public MyList() {
        outputList = new ArrayList<>();
    }
    @Override
    public void add(T item) {
        outputList.add(item);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Out:");

        for (T item : outputList) {
            builder.append("\n").append(item.toString());
        }
        return builder.toString();
    }

}
