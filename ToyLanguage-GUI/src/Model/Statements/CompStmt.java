package Model.Statements;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIStack;
import Model.Exceptions.InterpreterException;
import Model.Exceptions.StmtExecException;
import Model.PrgState;
import Model.Types.Type;

public class CompStmt implements IStmt{

    IStmt s1;
    IStmt s2;

    public CompStmt(IStmt s1, IStmt s2) {
        this.s1 = s1;
        this.s2 = s2;
    }
    @Override
    public PrgState execute(PrgState state) throws StmtExecException {
        MyIStack<IStmt> stack = state.getExecStack();
        stack.push(s2);
        stack.push(s1);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(s1.deepCopy(),s2.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> table) throws InterpreterException {
        return s2.typecheck(s1.typecheck(table));
    }

    @Override
    public String toString() {
        return "("+s1.toString()+";"+s2.toString()+")";
    }
}
