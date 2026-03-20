package model.statement;

import exception.StatementExecutionException;
import model.state.PrgState;

public class NopStmt implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException {
        return state;
    }

    @Override
    public String toString(){return "Nothing";}

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }
}
