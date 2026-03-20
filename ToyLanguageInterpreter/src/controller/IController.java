package controller;

import exception.StatementExecutionException;
import model.state.PrgState;

import java.util.EmptyStackException;

public interface IController {
    PrgState oneStep(PrgState state) throws EmptyStackException, StatementExecutionException;
    void allStep();
    void displayPrgState();
}
