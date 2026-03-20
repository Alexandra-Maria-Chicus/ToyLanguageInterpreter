package model.expression;
import model.state.IHeap;
import model.state.MyIDictionary;
import model.type.Type;
import model.value.Value;

public interface Expression {
    Value evaluate(MyIDictionary<String,Value> symbolTable, IHeap<Value> heap);
    String toString();
    Expression deepCopy();
    Type typecheck(MyIDictionary<String, Type> typeEnv);
}
