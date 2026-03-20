package model.statement;

import exception.StatementExecutionException;
import model.state.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws StatementExecutionException;
    String toString();
    IStmt deepCopy();
}
