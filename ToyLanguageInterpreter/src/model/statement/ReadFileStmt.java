package model.statement;

import exception.StatementExecutionException;
import model.expression.Expression;
import model.state.PrgState;
import model.type.IntType;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public record ReadFileStmt(Expression exp, String var_name) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException {

        if(!state.getSymTable().isDefined(var_name)){
            throw new StatementExecutionException("Variable "+var_name+" is not defined");
        }
        if(!state.getSymTable().getType(var_name).equals(new IntType()))
            throw new StatementExecutionException("Variable "+var_name+" is not IntType");
        Value val=exp.evaluate(state.getSymTable());

        if(!(val instanceof StringValue)){
            throw new StatementExecutionException("Value "+val+" is not StringValue");
        }
        StringValue valStr=(StringValue) val;
        BufferedReader br=state.getFileTable().getFile(valStr);
        if(br==null)
            throw new StatementExecutionException("File "+valStr+" not found");

        IntValue readIntValue;
        try{
            String line=br.readLine();
            if(line==null)
                readIntValue=new IntValue(0);
            else{
                try{
                    readIntValue=new IntValue(Integer.parseInt(line));
                } catch(NumberFormatException e){
                    throw new StatementExecutionException("Cannot read integer");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        state.getSymTable().setValue(var_name, readIntValue);
        return state;
    }
    @Override

    public String toString() {
        return "readFile(" + exp.toString() + ", " + var_name + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStmt(exp.deepCopy(), var_name);
    }
}
