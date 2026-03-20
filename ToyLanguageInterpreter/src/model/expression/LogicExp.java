package model.expression;

import exception.InvalidTypeException;
import exception.UnknownOperatorException;
import model.state.IHeap;
import model.state.MyIDictionary;
import model.type.BooleanType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public record LogicExp(Expression left, Expression right, String operator) implements Expression {
    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, IHeap<Value> heap) {
        Value leftValue = left.evaluate(symbolTable, heap);
        if (!(leftValue instanceof BoolValue(boolean leftBool)))
            throw new InvalidTypeException("Invalid type operators");

        Value rightValue = right.evaluate(symbolTable, heap);
        if (!(rightValue instanceof BoolValue(boolean rightBool)))
            throw new InvalidTypeException("Invalid type operators");

        boolean result = switch (operator) {
            case "&&" -> leftBool && rightBool;
            case "||" -> leftBool || rightBool;
            default -> throw new UnknownOperatorException("Invalid operator");
        };

        return new BoolValue(result);
    }

    @Override
    public Expression deepCopy() {
        return new LogicExp(left.deepCopy(), right.deepCopy(), operator);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) {
        Type type1 = left.typecheck(typeEnv);
        Type type2 = right.typecheck(typeEnv);
        if (type1.equals(new BooleanType())) {
            if (type2.equals(new BooleanType())) {
                return new BooleanType();
            } else
                throw new InvalidTypeException("second operand is not a boolean");
        } else
            throw new InvalidTypeException("first operand is not a boolean");
    }
}
