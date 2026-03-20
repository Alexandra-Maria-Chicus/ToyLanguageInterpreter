package model.expression;

import exception.DivisionByZeroException;
import exception.InvalidTypeException;
import exception.UnknownOperatorException;
import model.state.MyIDictionary;
import model.value.IntValue;
import model.value.Value;

public record ArithmeticExp(Expression left, Expression right, String operator) implements Expression {
    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable) {
        Value leftValue = left.evaluate(symbolTable);
        if (!(leftValue instanceof IntValue(int leftInt)))
            throw new InvalidTypeException("Invalid type operators");

        Value rightValue = right.evaluate(symbolTable);
        if (!(rightValue instanceof IntValue(int rightInt)))
            throw new InvalidTypeException("Invalid type operators");

        int result = switch (operator) {
            case "+" -> leftInt + rightInt;
            case "-" -> leftInt - rightInt;
            case "*" -> leftInt * rightInt;
            case "/" -> divide(leftInt, rightInt);
            default -> throw new UnknownOperatorException("Invalid operator");
        };

        return new IntValue(result);
    }

    @Override
    public Expression deepCopy() {
        return new ArithmeticExp(left.deepCopy(), right.deepCopy(), operator);
    }

    private static int divide(int leftInt, int rightInt) {
        if (rightInt == 0) throw new DivisionByZeroException("Division by zero!!!");
        return leftInt / rightInt;
    }
}
