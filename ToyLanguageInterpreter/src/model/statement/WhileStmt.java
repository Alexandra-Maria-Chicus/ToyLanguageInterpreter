package model.statement;

import exception.StatementExecutionException;
import model.expression.Expression;
import model.state.MyIDictionary;
import model.state.PrgState;
import model.type.BooleanType;
import model.type.Type;
import model.value.BoolValue;

public record WhileStmt(Expression condition, IStmt stmt) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException {
        if(condition.evaluate(state.getSymTable(),state.getHeap()) instanceof BoolValue boolValue){
            if(boolValue.val()){
                state.getExeStack().push(this);
                state.getExeStack().push(stmt);
            }
        } else
            throw new StatementExecutionException("Condition evaluation not boolean");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(condition, stmt);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) {
        Type typexp=condition.typecheck(typeEnv);
        if (typexp.equals(new BooleanType())) {
            stmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new StatementExecutionException("The condition of WHILE has not the type bool");
    }

    @Override
    public String toString() {
        return "While(" + condition.toString() + "){" + stmt.toString() + "};";
    }
}
