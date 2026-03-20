package model.expression;

import model.state.MyIDictionary;
import model.value.Value;

public record ValueExp(Value value) implements Expression {

    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable) {
        return value;
    }
    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public Expression deepCopy() {
        return new ValueExp(value);
    }
}
