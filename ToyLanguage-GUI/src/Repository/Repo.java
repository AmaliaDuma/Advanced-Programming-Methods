package Repository;

import Model.Exceptions.InterpreterException;
import Model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repo implements IRepo{

    private List<PrgState> list;
    private String logFilePath;

    public Repo() {list = new ArrayList<PrgState>();}
    public Repo(String log) {
        list = new ArrayList<PrgState>();
        logFilePath = log;
    }

    @Override
    public PrgState getCrtPrg() {
        return list.get(list.size()-1);
    }

    @Override
    public void addPrg(PrgState p) {
        list.add(p);
    }

    @Override
    public List<PrgState> getPrgList() {
        return list;
    }

    @Override
    public void setPrgList(List<PrgState> l) {
        list = l;
    }

    @Override
    public void logPrgStateExec(PrgState p) throws InterpreterException {
        PrintWriter logFile;
        try{
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        } catch (IOException e){
            throw new InterpreterException(e.getMessage());
        }
        logFile.write(p.toString());
        logFile.flush();
        logFile.close();
    }
}
