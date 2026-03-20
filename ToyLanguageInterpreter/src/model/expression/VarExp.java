package model.expression;

import exception.VariableNotDefinedExpression;
import model.state.IHeap;
import model.state.MyIDictionary;
import model.type.Type;
import model.value.Value;

public record VarExp(String varName) implements Expression {

    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, IHeap<Value> heap) {
        if(!symbolTable.isDefined(varName))
            throw new VariableNotDefinedExpression("Variable "+varName+" not defined");
        return symbolTable.getValue(varName);
    }
    @Override
    public String toString(){
        return varName;
    }

    @Override
    public Expression deepCopy() {
        return  new VarExp(varName);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) {
        return typeEnv.getType(varName);
    }
}
