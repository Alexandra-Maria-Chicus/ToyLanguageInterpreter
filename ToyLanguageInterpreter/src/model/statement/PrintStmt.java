package model.statement;

import exception.StatementExecutionException;
import model.expression.Expression;
import model.state.PrgState;

public record PrintStmt(Expression expression) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException {
        state.getOut().add(expression.evaluate(state.getSymTable()).toString());
        return state;
    }
    @Override
    public String toString(){
        return "print(" +expression.toString()+")";
    }

    @Override
    public IStmt deepCopy() {
        return  new PrintStmt(expression.deepCopy());
    }

}
