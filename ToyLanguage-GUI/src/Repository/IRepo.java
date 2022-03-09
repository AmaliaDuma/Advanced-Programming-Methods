package Repository;

import Model.Exceptions.InterpreterException;
import Model.PrgState;

import java.util.List;

public interface IRepo {
    PrgState getCrtPrg(); //to remove
    void addPrg(PrgState p);
    void logPrgStateExec(PrgState p) throws InterpreterException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> l);

}
