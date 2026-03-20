package model.statement;

import exception.StatementExecutionException;
import model.expression.Expression;
import model.state.PrgState;
import model.value.BoolValue;
import model.value.Value;

public record IfStmt(Expression condition, IStmt thenStmt, IStmt elseStmt) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException {
        Value result= condition.evaluate(state.getSymTable());
        if(result instanceof BoolValue(boolean val)){
            if(val){
                state.getExeStack().push(thenStmt);
            }
            else{
                state.getExeStack().push(elseStmt);
            }
        }   else {
            throw new StatementExecutionException("Condition is not BOOLEAN value!");
        }
        return state;
    }

    @Override
    public String toString(){ return "(IF("+ condition.toString()+") THEN(" +thenStmt.toString()
            +")ELSE("+elseStmt.toString()+"))";}

    @Override
    public IStmt deepCopy() {
        return new IfStmt(condition, thenStmt.deepCopy(), elseStmt.deepCopy());
    }


}
