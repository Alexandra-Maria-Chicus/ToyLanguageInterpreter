package model.value;

import model.type.StringType;
import model.type.Type;

public record StringValue(String val) implements Value {
    @Override
    public Type getType() {
        return new StringType();
    }
    @Override
    public String toString() {
        return val;
    }
    @Override
    public boolean equals(Value other) {
        return other instanceof StringValue && this.val == ((StringValue) other).val;
    }

    @Override
    public Value deepCopy() {
        return new StringValue(val);
    }

}
