package model.statement;

import exception.StatementExecutionException;
import model.state.MyIDictionary;
import model.state.PrgState;
import model.type.Type;

public record CompStmt(IStmt first, IStmt second) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException {
        var stack=state.getExeStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public  String toString() {
        return "("+first.toString() + ";" + second.toString()+")";
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) {
        return second.typecheck(first.typecheck(typeEnv));
    }
}
