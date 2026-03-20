package model.type;

import model.value.StringValue;
import model.value.Value;

public class StringType implements Type{
    @Override
    public Value getDefaultValue() {
        return new StringValue("");
    }

    @Override
    public String getTypeName() {
        return "String";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof StringType;
    }

    @Override
    public Type deepCopy() {
        return new StringType();
    }
}
