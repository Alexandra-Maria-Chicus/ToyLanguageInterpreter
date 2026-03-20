package model.state;

import exception.StatementExecutionException;
import model.statement.IStmt;
import model.value.Value;

import java.util.EmptyStackException;

public class PrgState {
    private final MyIStack<IStmt> exeStack;
    private final MyIDictionary<String, Value> symTable;
    private final MyIList<String> out;
    private final FileTable fileTable;
    private final IHeap<Value> heap;
    private static int nextProgramId = 1;
    private final int programId;

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Value> symTable, MyIList<String> out, IStmt program, FileTable fileTable, IHeap<Value> heap,int programId) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.programId = programId;

        exeStack.push(program);
    }

    public PrgState(IStmt originalProgram) {
        int newId = getNewProgramId();
        this(new MyStack<>(), new MyDictionary<>(), new MyList<>(), originalProgram, new MapFileTable(), new MyHeap<>(), newId);
    }

    public static synchronized int getNewProgramId() {
        return nextProgramId++;
    }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    @Override
    public String toString() {
        return "--------------------------------------------------------\n" +
                "Program State ID: " + programId + "\n" +
                exeStack.toString() + "\n" +
                symTable.toString() + "\n" +
                out.toString() + "\n" +
                fileTable.toString() + "\n" +
                heap.toString() + "\n" +
                "--------------------------------------------------------";
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public IHeap<Value> getHeap() {return heap;}

    public MyIList<String> getOut() {return out;}

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public FileTable getFileTable() {return fileTable;}

    public int getProgramId() {return programId;}

    public PrgState oneStep() throws EmptyStackException, StatementExecutionException {
        if(exeStack.isEmpty())
            throw new EmptyStackException();
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }
}
