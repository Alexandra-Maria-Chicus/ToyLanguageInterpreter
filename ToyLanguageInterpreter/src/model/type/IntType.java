package model.type;

import model.value.IntValue;
import model.value.Value;

public class IntType implements Type {

    @Override
    public Value getDefaultValue() {
        return new IntValue(0);
    }

    @Override
    public String getTypeName() {
        return "Integer";
    }
    @Override
    public boolean equals(Object other) {
        return other instanceof IntType;
    }

    @Override
    public Type deepCopy() {
        return new IntType();
    }
}
