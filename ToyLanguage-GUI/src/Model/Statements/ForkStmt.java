package Model.Statements;

import Model.ADTs.MyDictionary;
import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIStack;
import Model.ADTs.MyStack;
import Model.Exceptions.InterpreterException;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.Value;

import java.util.Map;

public class ForkStmt implements IStmt{

    private IStmt stmt;

    public ForkStmt(IStmt s) {stmt = s;}

    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        MyIStack<IStmt> execStackN = new MyStack<>();
        execStackN.push(stmt);

        MyIDictionary<String, Value> symTableN = new MyDictionary<>();
        for (Map.Entry<String,Value> entry: state.getSymTable().getContent().entrySet()){
            symTableN.add(entry.getKey(), entry.getValue().deepCopy());
        }

        PrgState newProgram = new PrgState(execStackN, symTableN, state.getOut(), state.getFileTable(), state.getHeap());
        newProgram.setId();
        return newProgram;
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(stmt.deepCopy());
    }

    private static MyIDictionary<String, Type> clone(MyIDictionary<String, Type> table) throws InterpreterException {
        MyIDictionary<String, Type> newSymbolTable = new MyDictionary<>();
        for (Map.Entry<String, Type> entry: table.getContent().entrySet()) {
            newSymbolTable.add(entry.getKey(), entry.getValue());
        }
        return newSymbolTable;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> table) throws InterpreterException {
        stmt.typecheck(clone(table));
        return table;
    }

    @Override
    public String toString() {
        return "fork(" + stmt.toString() + ")";
    }
}
