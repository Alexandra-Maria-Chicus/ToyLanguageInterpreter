package repository;

import model.state.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MyRepository implements IRepository{
    private List<PrgState> repository;
    private String logFilePath;

    public MyRepository(List<PrgState> prgStates, String logFile) {
        this.repository = prgStates;
        this.logFilePath = logFile;
    }

    @Override
    public PrgState getCurrProg() throws NoSuchElementException {
        if (repository.isEmpty()) {
            throw new NoSuchElementException("The program repository is empty. Load an example first.");
        }
        return repository.getLast();
    }
    @Override
    public void add(PrgState currState) {
        repository.add(currState);
    }

    @Override
    public void logPrgStateExec(PrgState p)  {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
            logFile.println(p.toString());
            logFile.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
