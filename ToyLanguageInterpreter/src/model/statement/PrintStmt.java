package model.statement;

import exception.StatementExecutionException;
import model.expression.Expression;
import model.state.MyIDictionary;
import model.state.PrgState;
import model.type.Type;

public record PrintStmt(Expression expression) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException {
        state.getOut().add(expression.evaluate(state.getSymTable(), state.getHeap()).toString());
        return null;
    }
    @Override
    public String toString(){
        return "print(" +expression.toString()+")";
    }

    @Override
    public IStmt deepCopy() {
        return  new PrintStmt(expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) {
        expression.typecheck(typeEnv);
        return typeEnv;
    }

}
