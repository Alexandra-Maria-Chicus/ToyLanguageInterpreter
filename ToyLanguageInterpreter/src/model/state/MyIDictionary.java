package model.state;

import model.type.Type;
import model.value.Value;

public interface MyIDictionary<K, V> {
    void setValue(K variableName, V value);
    boolean isDefined(K variableName);
    Type getType(K variableName);
    void declareVariable(K variableName, Type type);
    MyIDictionary<K, V> deepCopy();
    Value getValue(K variableName);
    String toString();
}
