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

import java.sql.Ref;

public class HWritingStmt implements IStmt{
    private String var_name;
    private Exp exp;

    public HWritingStmt(String n, Exp e){
        var_name = n;
        exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();

        if (symTable.isDefined(var_name)){
            Value v1 = symTable.lookup(var_name);
            if (v1.getType() instanceof RefType){
                RefValue rv = (RefValue) v1;
                if (heap.isDefined(rv.getAddr())){
                    Value v2 = exp.eval(symTable, heap);
                    if (v2.getType().equals(heap.lookup(rv.getAddr()).getType())){
                        heap.update(rv.getAddr(), v2);
                    }
                    else throw new StmtExecException("Exp not of variable type");
                }
                else throw new StmtExecException("Addr not a key in heap.");
            }
            else throw new StmtExecException("Var is not a ref type.");
        }
        else throw new StmtExecException("Var name not defined.");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new HWritingStmt(var_name, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> table) throws InterpreterException {
        Type t_var = table.lookup(var_name);
        Type t_exp = exp.typecheck(table);
        if (t_var instanceof RefType){
            RefType rtype = (RefType) t_var;
            if (t_exp.equals(rtype.getInner())){
                return table;
            }
            else {
                throw new TypeException("Not the same type on heap modification");
            }
        }
        else{
            throw new TypeException("Variable not of reference type");
        }
    }

    @Override
    public String toString() {
        return "wH(" + var_name + ","+ exp.toString()+")";
    }
}
