package model.statement;

import exception.StatementExecutionException;
import model.expression.Expression;
import model.state.MyIDictionary;
import model.state.PrgState;
import model.value.Value;

public record AssignStmt(Expression expression, String variableName) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException {
        MyIDictionary<String, Value> symbolTable=state.getSymTable();
        if(!symbolTable.isDefined(variableName)){
            throw new StatementExecutionException("Variable name is not defined!");
        }
        Value value= expression.evaluate(symbolTable);
        if(!value.getType().equals(symbolTable.getType(variableName))){
            throw new StatementExecutionException("Value is not of type '"+symbolTable.getType(variableName)+"'!");
        }
        symbolTable.setValue(variableName,value);
        return state;
    }

    @Override
    public String toString(){ return variableName+"="+ expression.toString();}

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(expression,variableName);
    }

}
