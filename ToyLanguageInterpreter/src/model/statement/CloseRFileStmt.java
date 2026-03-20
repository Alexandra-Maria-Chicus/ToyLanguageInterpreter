package model.statement;

import exception.StatementExecutionException;
import model.expression.Expression;
import model.state.MyIDictionary;
import model.state.PrgState;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public record CloseRFileStmt(Expression expression) implements IStmt{
    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException {
        Value val= expression().evaluate(state.getSymTable(), state.getHeap());
        if(!(val instanceof StringValue))
            throw new StatementExecutionException("Wrong type of expression");
        StringValue fileName = (StringValue) val;
        BufferedReader br = state.getFileTable().getFile(fileName);
        if(br == null)
            throw new StatementExecutionException("File not found");

        try{
            br.close();
            state.getFileTable().getFile(fileName).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public String toString() {
        return "closeRFile(" + expression.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFileStmt(expression().deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) {
        expression.typecheck(typeEnv);
        return typeEnv;
    }
}
