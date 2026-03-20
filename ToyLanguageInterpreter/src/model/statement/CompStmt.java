package model.statement;

import exception.StatementExecutionException;
import model.state.PrgState;

public record CompStmt(IStmt first, IStmt second) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException {
        var stack=state.getExeStack();
        stack.push(second);
        stack.push(first);
        return state;
    }

    @Override
    public  String toString() {
        return "("+first.toString() + ";" + second.toString()+")";
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }
}
