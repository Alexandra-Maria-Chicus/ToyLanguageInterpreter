package controller;

import exception.StatementExecutionException;
import model.state.MyIStack;
import model.state.PrgState;
import model.statement.IStmt;
import model.value.RefValue;
import model.value.Value;
import repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Locale.filter;

public class Controller implements IController {
    private IRepository repository;
    private boolean flag=true;
    private ExecutorService executor;

    public Controller(IRepository repository){
        this.repository = repository;
        this.executor = java.util.concurrent.Executors.newFixedThreadPool(2);
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        prgList.forEach(prg ->repository.logPrgStateExec(prg));
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());
        List<PrgState> newPrgList = executor.invokeAll(callList). stream()
                . map(future -> { try { return future.get();}
                catch (InterruptedException e) {
                        throw new RuntimeException(e);}
                catch (EmptyStackException e) {
                        System.out.println("Program finished execution successfully.");
                        return null;}
                catch (StatementExecutionException e) {
                        throw e;}
                catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    if (cause instanceof EmptyStackException) {
                        return null;
                    }
                    if (cause instanceof StatementExecutionException) {
                        throw new RuntimeException("Execution Error in thread: " + cause.getMessage(), cause);
                    }
                    throw new RuntimeException("Unexpected thread error: " + cause.getMessage(), cause);
                }})
                    .filter(p -> p!=null)
                     .collect(Collectors.toList());
        prgList.addAll(newPrgList);
        prgList.forEach(prg ->repository.logPrgStateExec(prg));
        repository.setPrgList(prgList);
    };

    public void allStep() {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repository.getPrgStates());
        while (prgList.size() > 0) {
            conservativeGarbageCollector(prgList);
            try {
                oneStepForAllPrg(prgList);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            prgList = removeCompletedPrg(repository.getPrgStates());
        }
        executor.shutdownNow();
        repository.setPrgList(prgList);
    }

    private List<Integer> getAllSymTableAddresses(List<PrgState> prgList) {
        List<Collection<Value>> allSymTableValues = prgList.stream()
                .map(p -> p.getSymTable().getContent().values())
                .collect(Collectors.toList());

        List<Integer> allAddresses = new ArrayList<>();

        for (Collection<Value> values : allSymTableValues) {
            allAddresses.addAll(getAddrFromSymTable(values));
        }

        return allAddresses;
    };

    public void conservativeGarbageCollector(List<PrgState> programStates) {
        List<Integer> allRoots = getAllSymTableAddresses(programStates);
        if (programStates.isEmpty()) {
            return;
        }
        Map<Integer, Value> currentHeap = programStates.get(0).getHeap().getContent();
        Map<Integer, Value> cleanedHeap = safeGarbageCollector(allRoots, currentHeap);
        programStates.get(0).getHeap().setContent(cleanedHeap);
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap){
        List<Integer> liveAddresses = new ArrayList<>(symTableAddr);
        List<Integer> nestedAddresses = getAddressesFromHeap(heap.entrySet().stream()
                        .filter(e -> symTableAddr.contains(e.getKey())) // Only look at values pointed to by the roots
                        .map(Map.Entry::getValue)
                        .collect(Collectors.toList())
        );
        liveAddresses.addAll(nestedAddresses);
        Set<Integer> uniqueLiveAddresses = new HashSet<>(liveAddresses);
        return heap.entrySet().stream()
                .filter(e -> uniqueLiveAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }
    public List<Integer> getAddressesFromHeap(Collection<Value> heapValues) {
        return heapValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    @Override
    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    @Override
    public IRepository getRepository() {
        return repository;
    }


}
