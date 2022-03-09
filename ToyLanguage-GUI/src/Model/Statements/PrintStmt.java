package Model.Statements;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.MyIList;
import Model.Exceptions.InterpreterException;
import Model.Exceptions.StmtExecException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class PrintStmt implements IStmt{

    Exp exp;

    public PrintStmt(Exp e) { exp=e;}

    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        MyIList<Value> out = state.getOut();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();

        out.append(exp.eval(symTable, heap));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> table) throws InterpreterException {
        exp.typecheck(table);
        return table;
    }

    @Override
    public String toString() {
        return "print("+exp.toString()+")";
    }
}
