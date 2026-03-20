package model.expression;
import model.state.MyIDictionary;
import model.value.Value;

public interface Expression {
    Value evaluate(MyIDictionary<String,Value> symbolTable);
    String toString();
    Expression deepCopy();
}
