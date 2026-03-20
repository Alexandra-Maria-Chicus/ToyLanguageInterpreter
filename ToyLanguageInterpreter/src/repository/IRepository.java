package repository;

import model.state.PrgState;

import java.util.NoSuchElementException;

public interface IRepository {
    PrgState getCurrProg() throws NoSuchElementException;
    void add(PrgState currState);
    void logPrgStateExec(PrgState p) ;
}
