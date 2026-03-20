package controller;

import exception.StatementExecutionException;
import model.state.MyIStack;
import model.state.PrgState;
import model.statement.IStmt;
import repository.IRepository;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

public class Controller implements IController {
    private IRepository repository;
    private boolean flag=true;
    public Controller(IRepository repository){
        this.repository = repository;
    }

    public PrgState oneStep(PrgState state) throws EmptyStackException, StatementExecutionException{
        MyIStack<IStmt> stk=state.getExeStack();
        if(stk.isEmpty())
            throw new EmptyStackException();
        IStmt crtStmt=stk.pop();
        PrgState returnedState = crtStmt.execute(state);
        if(flag){
            repository.logPrgStateExec(returnedState);
        }
        return returnedState;
    }

    public void allStep(){
        PrgState prg;
        try {
            prg = repository.getCurrProg();
        } catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
            return;
        }
        try{
            while(!prg.getExeStack().isEmpty()){
                try {
                    prg = oneStep(prg);
                } catch (EmptyStackException e) {
                    System.out.println("Program finished execution successfully.");
                    break;
                } catch (StatementExecutionException e) {
                    throw e;
                } catch (Exception e) {
                    throw new StatementExecutionException("Unexpected runtime error during execution: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("An unexpected program error occurred: " + e.getMessage());
        }
    }

    public void displayPrgState(){
        System.out.println(repository.getCurrProg().toString());
    }
}
