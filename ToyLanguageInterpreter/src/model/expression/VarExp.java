package model.expression;

import exception.VariableNotDefinedExpression;
import model.state.MyIDictionary;
import model.value.Value;

public record VarExp(String varName) implements Expression {

    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable) {
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
}
