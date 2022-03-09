package Model.Statements;

import Model.ADTs.MyIDictionary;
import Model.Exceptions.InterpreterException;
import Model.PrgState;
import Model.Types.Type;

public interface IStmt {
    PrgState execute(PrgState state) throws InterpreterException;
    IStmt deepCopy();
    MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> table) throws InterpreterException;
}
