package model.statement;

import exception.InvalidTypeException;
import exception.StatementExecutionException;
import model.expression.Expression;
import model.state.MyIDictionary;
import model.state.PrgState;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public record NewStmt(String varName, Expression expr) implements IStmt {
    @Override
    public PrgState execute(PrgState state) {
        Value v= state.getSymTable().getValue(varName);
        if (v == null) {
            throw new StatementExecutionException("Variable " + varName + " not found");
        }
        if (!(v.getType() instanceof RefType refType)) {
            throw new StatementExecutionException("Variable " + varName + " is not a RefType. Got: " + v.getType());
        }
        Value exprValue=expr.evaluate(state.getSymTable(),state.getHeap());
        Type locationType= refType.getInner();
        if(!exprValue.getType().equals(locationType))
            throw new StatementExecutionException("Invalid type operators");
        int newAddr= state.getHeap().put(exprValue);
        state.getSymTable().setValue(varName, new RefValue(newAddr, locationType));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(varName, expr);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) {
        Type typevar = typeEnv.getType(varName);
        Type typexp = expr.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new InvalidTypeException("NEW stmt: right hand side and left hand side have different types ");
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + expr + ")";
    }
}
