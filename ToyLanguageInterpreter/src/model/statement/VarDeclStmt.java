package model.statement;

import exception.StatementExecutionException;
import model.state.PrgState;
import model.type.Type;

public record VarDeclStmt(Type type, String variableName) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException {
        var symbolTable = state.getSymTable();
        if (symbolTable.isDefined(variableName)) {
            throw new StatementExecutionException("Variable " + variableName + " is already defined");
        }
        symbolTable.declareVariable(variableName, type);
        return state;
    }

    @Override
    public String toString() {
        return type.getTypeName() + " " + variableName;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(type, variableName);
    }
}
