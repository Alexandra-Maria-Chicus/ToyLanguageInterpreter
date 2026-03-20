package model.expression;

import com.sun.jdi.BooleanValue;
import exception.InvalidTypeException;
import exception.UnknownOperatorException;
import model.state.MyIDictionary;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public record RelationalExp(Expression left, Expression right, String operator) implements Expression {
    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable) {
        Value leftValue = left.evaluate(symbolTable);
        if (!(leftValue instanceof IntValue(int leftInt)))
            throw new InvalidTypeException("Invalid type operators");

        Value rightValue = right.evaluate(symbolTable);
        if (!(rightValue instanceof IntValue(int rightInt)))
            throw new InvalidTypeException("Invalid type operators");

        boolean result= switch (operator){
            case "==" -> leftInt == rightInt;
            case "!=" -> leftInt != rightInt;
            case ">" -> leftInt > rightInt;
            case "<" -> leftInt < rightInt;
            case ">=" -> leftInt >= rightInt;
            case "<=" -> leftInt <= rightInt;
            default -> throw new UnknownOperatorException("Invalid operator");
        };

        return new BoolValue(result);
    }

    @Override
    public Expression deepCopy() {
        return new RelationalExp(left.deepCopy(), right.deepCopy(), operator);
    }
}
