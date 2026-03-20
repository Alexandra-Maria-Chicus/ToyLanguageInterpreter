package model.state;

import java.util.Map;

public interface IHeap<Value> {
    int put(Value value);
    void setContent(Map<Integer, Value> map);
    Map<Integer, Value> getContent();
    Value getValue(int a);
    void update(int a, Value value);
}
