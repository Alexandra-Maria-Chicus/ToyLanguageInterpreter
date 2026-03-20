package repository;

import model.state.PrgState;

import java.util.List;
import java.util.NoSuchElementException;

public interface IRepository {
    void add(PrgState currState);
    void logPrgStateExec(PrgState p) ;
    List<PrgState> getPrgStates();
    void setPrgList(List<PrgState>  prgList) ;
}
