package Model.Statements;

import Model.ADTs.MyIDictionary;
import Model.Exceptions.InterpreterException;
import Model.PrgState;
import Model.Types.Type;

public class NopStmt implements IStmt{

    public NopStmt() {};

    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> table) throws InterpreterException {
        return table;
    }

    @Override
    public String toString() {
        return "Nop";
    }
}
