package model.statement;

import exception.StatementExecutionException;
import model.expression.Expression;
import model.state.PrgState;
import model.type.StringType;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public record OpenRFileStmt(Expression exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException {
        Value val=exp.evaluate(state.getSymTable());

        if(!val.getType().equals(new StringType())){
            throw new StatementExecutionException("Wrong type of expression");
        }

        StringValue fileValue=(StringValue)val;
        if(state.getFileTable().isOpened(fileValue)){
            throw new StatementExecutionException("File already opened");
        }
        BufferedReader br;
        try{
          br=new BufferedReader(new FileReader(fileValue.val()));
        } catch (IOException e){
            throw new StatementExecutionException("Error opening file");
        }
        state.getFileTable().add(fileValue, br);
        return state;
    }

    @Override
    public String toString() {
        return "openRFile(" + exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFileStmt(exp.deepCopy());
    }
}
