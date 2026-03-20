package model.statement;

import exception.StatementExecutionException;
import model.expression.Expression;
import model.state.MyIDictionary;
import model.state.PrgState;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public record wH(String varName, Expression expression) implements IStmt{

    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException {
        if(!state.getSymTable().isDefined(varName)){
            throw new StatementExecutionException("Variable " + varName + " is not defined");
        }
        if(!(state.getSymTable().getType(varName) instanceof RefType )){
            throw new StatementExecutionException("Variable " + varName + " is not of type RefType");
        }
        Value v= state.getSymTable().getValue(varName);
        if(v instanceof RefValue rv) {
            if (state.getHeap().getValue(rv.getAddress()) == null)
                throw new StatementExecutionException("The memory is not allocated!");
            Value valExpr=expression.evaluate(state.getSymTable(), state.getHeap());
            if(valExpr.getType().equals(rv.getLocationType())){
                state.getHeap().update(rv.getAddress(),valExpr);
            }
            else  throw new StatementExecutionException("The memory is not allocated!");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new wH(varName, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) {
        Type typevar = typeEnv.getType(varName);
        Type typexp = expression.typecheck(typeEnv);
        if (typevar instanceof RefType refType) {
            if (refType.getInner().equals(typexp))
                return typeEnv;
            else
                throw new StatementExecutionException("wH stmt: right hand side and left hand side have different types ");
        } else
            throw new StatementExecutionException("wH stmt: variable is not of RefType ");
    }

    @Override
    public String toString() {
        return "writeHeap(" + varName + ", " + expression.toString() + ")";
    }
}
