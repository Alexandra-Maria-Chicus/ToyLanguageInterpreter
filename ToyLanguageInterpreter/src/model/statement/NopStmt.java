package model.statement;

import exception.StatementExecutionException;
import model.state.MyIDictionary;
import model.state.PrgState;
import model.type.Type;

public class NopStmt implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException {
        return null;
    }

    @Override
    public String toString(){return "Nothing";}

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) {
        return typeEnv;
    }
}
