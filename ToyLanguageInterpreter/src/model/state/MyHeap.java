package model.state;

import java.util.HashMap;
import java.util.Map;

public class MyHeap<Value> implements IHeap<Value>  {
    public static int nextAddr = 1;
    protected Map<Integer,Value> map;

    public MyHeap() {
        this.map = new HashMap<>();
    }

    private int nextFreeAddr() {
        return nextAddr++;
    }

    @Override
    public int put(Value value){
        int newAddr = nextFreeAddr();
        map.put(newAddr, value);
        return newAddr;
    }

    @Override
    public void setContent(Map<Integer, Value> map) {
        this.map = map;
    }

    @Override
    public Map<Integer, Value> getContent() {
        return map;
    }

    @Override
    public Value getValue(int a) {
        return this.map.get(a);
    }

    @Override
    public void update(int a, Value value) {
        this.map.put(a, value);
    }
    // Example needed in your model.state.MyHeap class
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Heap:");
        for (Map.Entry<Integer, Value> entry : map.entrySet()) {
            builder.append("\n").append(entry.getKey()).append(" -> ").append(entry.getValue().toString());
        }
        return builder.toString();
    }
}
