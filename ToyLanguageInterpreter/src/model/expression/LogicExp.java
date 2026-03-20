package model.expression;

import com.sun.jdi.BooleanValue;
import exception.InvalidTypeException;
import exception.UnknownOperatorException;
import model.state.MyIDictionary;
import model.value.BoolValue;
import model.value.Value;

public record LogicExp(Expression left, Expression right, String operator) implements Expression {
    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable) {
        Value leftValue = left.evaluate(symbolTable);
        if (!(leftValue instanceof BoolValue(boolean leftBool)))
            throw new InvalidTypeException("Invalid type operators");

        Value rightValue = right.evaluate(symbolTable);
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
}
