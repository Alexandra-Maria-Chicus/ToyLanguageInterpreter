package model.statement;

import exception.InvalidTypeException;
import exception.StatementExecutionException;
import model.expression.Expression;
import model.state.MyIDictionary;
import model.state.PrgState;
import model.type.BooleanType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public record IfStmt(Expression condition, IStmt thenStmt, IStmt elseStmt) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException {
        Value result= condition.evaluate(state.getSymTable(), state.getHeap());
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
        return null;
    }

    @Override
    public String toString(){ return "(IF("+ condition.toString()+") THEN(" +thenStmt.toString()
            +")ELSE("+elseStmt.toString()+"))";}

    @Override
    public IStmt deepCopy() {
        return new IfStmt(condition, thenStmt.deepCopy(), elseStmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) {
        Type typexp=condition.typecheck(typeEnv);
        if (typexp.equals(new BooleanType())) {
            thenStmt.typecheck(typeEnv.deepCopy());
            elseStmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new InvalidTypeException("The condition of IF has not the type bool");
    }

}
