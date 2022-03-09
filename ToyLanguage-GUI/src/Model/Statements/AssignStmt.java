package Model.Statements;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.InterpreterException;
import Model.Exceptions.StmtExecException;
import Model.Exceptions.TypeException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class AssignStmt implements IStmt{

    String name;
    Exp exp;

    public AssignStmt(String n, Exp e){
        name=n;
        exp=e;
    }

    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();

        if (symTable.isDefined(name)) {
            Value v1 = exp.eval(symTable, heap);
            if (v1.getType().equals(symTable.lookup(name).getType()))
                symTable.update(name, v1);
            else throw new StmtExecException("Type of expression and type of variable do not match");
        }
        else throw new StmtExecException("Variable is not declared");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(name, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> table) throws InterpreterException {
        Type t_var = table.lookup(name);
        Type t_exp = exp.typecheck(table);
        if (t_var.equals(t_exp)){
            return table;
        }
        else{
            throw new TypeException("Assignment: right hand side and left hand side have different types.");
        }
    }

    @Override
    public String toString() {
        return name+"="+exp.toString();
    }

}
