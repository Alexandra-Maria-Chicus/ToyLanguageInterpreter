package model.value;

import model.type.BooleanType;
import model.type.Type;

public record BoolValue(boolean val) implements Value {
    public String toString() {
        return Boolean.toString(val);
    }

    @Override
    public boolean equals(Value other) {
        return other instanceof BoolValue && this.val == ((BoolValue) other).val;
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }

    @Override
    public Type getType() {
        return new BooleanType();
    }
}
