package Model.Statements;

import Model.ADTs.MyIDictionary;
import Model.Exceptions.InterpreterException;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class VarDeclStmt implements IStmt{

    String name;
    Type type;

    public VarDeclStmt(String n, Type t) {
        name=n;
        type=t;
    }

    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        Value v = type.defValue();
        symTable.add(name, v);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name, type.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> table) throws InterpreterException {
        table.add(name, type);
        return table;
    }

    @Override
    public String toString() {
        return type.toString()+" "+name;
    }
}
