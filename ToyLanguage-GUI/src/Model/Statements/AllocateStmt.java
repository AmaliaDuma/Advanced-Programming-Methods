package Model.Statements;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.InterpreterException;
import Model.Exceptions.StmtExecException;
import Model.Exceptions.TypeException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class AllocateStmt implements  IStmt{
    private String var_name;
    private Exp exp;

    public AllocateStmt(String n, Exp e){
        var_name = n;
        exp = e;
    }
    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();

        if (symTable.isDefined(var_name)){
            if (symTable.lookup(var_name).getType() instanceof RefType){
                Value v1 = exp.eval(symTable, heap);
                Value v2 = symTable.lookup(var_name);
                if (v1.getType().equals(((RefType)(v2.getType())).getInner())){
                    int addr = heap.allocate(v1);
                    symTable.update(var_name, new RefValue(addr, v1.getType()));
                }
                else throw new StmtExecException("Types don't match.");
            }
            else throw new StmtExecException("Not a reference type.");
        }
        else throw new StmtExecException("Variable not defined.");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AllocateStmt(var_name, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> table) throws InterpreterException {
        Type t_var = table.lookup(var_name);
        Type t_exp = exp.typecheck(table);
        if (t_var.equals(new RefType(t_exp))){
            return table;
        }
        else{
            throw new TypeException("NEW stmt: right hand side and left hand side have different types.");
        }
    }

    @Override
    public String toString() {
        return "new(" + var_name + "," + exp.toString() + ")";
    }
}
