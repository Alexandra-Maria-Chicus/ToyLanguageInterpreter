package model.value;

import model.type.Type;

public interface Value {
    Type getType();
    String toString();
    boolean equals(Value other);
    Value deepCopy();
}
