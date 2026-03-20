package model.expression;

import model.state.IHeap;
import model.state.MyIDictionary;
import model.type.Type;
import model.value.Value;

public record ValueExp(Value value) implements Expression {

    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, IHeap<Value> heap) {
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

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) {
        return value.getType();
    }
}
