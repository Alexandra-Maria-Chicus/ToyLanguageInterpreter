package model.value;

import model.type.IntType;
import model.type.Type;

public record IntValue(int val) implements Value {
    @Override
    public Type getType() {
        return new IntType();
    }
    @Override
    public String toString() {
        return Integer.toString(this.val);
    }
    @Override
    public boolean equals(Value other) {
        return other instanceof IntValue && this.val == ((IntValue) other).val;
    }

    @Override
    public Value deepCopy() {
        return  new IntValue(val);
    }

}
