package model.statement;

import exception.StatementExecutionException;
import model.state.MyIDictionary;
import model.state.PrgState;
import model.type.Type;

public interface IStmt {
    PrgState execute(PrgState state) throws StatementExecutionException;
    String toString();
    IStmt deepCopy();
    MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv);
}
