package Controller;

import Model.ADTs.MyIStack;
import Model.Exceptions.ControllerException;
import Model.Exceptions.InterpreterException;
import Model.PrgState;
import Model.Statements.IStmt;
import Model.Values.RefValue;
import Model.Values.Value;
import Repository.IRepo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {

    private IRepo repo;
    private static boolean displayFlag=false;
    private ExecutorService executor;

    public Controller(IRepo r) {repo = r;}
    public Controller(IRepo r, boolean displayF) {repo = r; displayFlag = displayF;}

    public PrgState oneStep(PrgState state) throws InterpreterException{
        MyIStack<IStmt> stack = state.getExecStack();
        if (stack.isEmpty())
            throw new ControllerException("PrgState stack is empty");
        IStmt crtStmt = stack.pop();
        return crtStmt.execute(state);
    }

//    public void allSteps() throws InterpreterException{
//        PrgState prg = repo.getCrtPrg();
//        if (displayFlag) System.out.println(prg.toString());
//        repo.logPrgStateExec(prg);
//        while (!prg.isCompleted()){
//            oneStep(prg);
//            if (displayFlag) System.out.println(prg.toString());
//            repo.logPrgStateExec(prg);
//            prg.getHeap().setContent(unsafeGarbageCollector(getAddrFromSymTable(prg.getSymTable().getContent().values(),
//                            prg.getHeap().getContent().values()), prg.getHeap().getContent()));
//            repo.logPrgStateExec(prg);
//        }
//    }

    public void open_executors(){
        executor = Executors.newFixedThreadPool(3);
    }

    public void close_executors(){
        executor.shutdownNow();
    }

    public void allSteps() throws InterpreterException{
        executor = Executors.newFixedThreadPool(2);

        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        while (prgList.size() > 0){
            conservativeGarbageCollector(prgList);
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);

    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws InterpreterException {
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (InterpreterException e) {
                e.printStackTrace();
            }
        });

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());

        try {
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            System.out.println(e.getMessage());
                        }
                        return null;
                    })
                    .filter(p -> p != null)
                    .collect(Collectors.toList());
            prgList.addAll(newPrgList);
        }catch (InterruptedException e){
            throw new InterpreterException(e.getMessage()); }

        prgList.forEach(p -> {
            try {
                repo.logPrgStateExec(p);
            } catch (InterpreterException e) {
                e.printStackTrace();
            }
        });

        repo.setPrgList(prgList);

    }

    public void setDisplayFlag(boolean b) {displayFlag = b;}

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap){
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void conservativeGarbageCollector(List<PrgState> prgList){
        List<Integer> addresses = Objects.requireNonNull(prgList.stream()
                .map(p -> getAddrFromSymTable(
                        p.getSymTable().getContent().values(),
                        p.getHeap().getContent().values()))
                .map(Collection::stream)
                .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());
        prgList.forEach(p -> {
            p.getHeap().setContent(safeGarbageCollector(addresses, prgList.get(0).getHeap().getContent()));
        });
    }

    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues, Collection<Value> heap){
        return Stream.concat(
                heap.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v -> {
                            RefValue v1 = (RefValue)v; return v1.getAddr();
                        }),
                        symTableValues.stream()
                                .filter(v -> v instanceof RefValue)
                                .map(v -> {
                                    RefValue v1 = (RefValue)v; return v1.getAddr();
                                })).collect(Collectors.toList());
    }

    public void typecheck() throws InterpreterException {
        repo.getPrgList().get(0).typecheck();
    }

    public int getNr_prgStates(){
        return repo.getPrgList().size();
    }

    public IRepo getRepo(){
        return repo;
    }
}
