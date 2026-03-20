package model.type;

import model.value.BoolValue;
import model.value.Value;

public class BooleanType implements Type {

    @Override
    public Value getDefaultValue() {
        return new BoolValue(false);
    }

    @Override
    public String getTypeName() {
        return "Boolean";
    }
    @Override
    public boolean equals(Object other) {
        return other instanceof BooleanType;
    }

    @Override
    public Type deepCopy() {
        return new BooleanType();
    }
}
