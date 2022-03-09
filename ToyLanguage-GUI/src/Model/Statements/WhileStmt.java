package Model.Statements;

import Model.ADTs.MyDictionary;
import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.MyIStack;
import Model.Exceptions.InterpreterException;
import Model.Exceptions.StmtExecException;
import Model.Exceptions.TypeException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

import java.util.Map;

public class WhileStmt implements IStmt{
    private Exp exp;
    private IStmt stmt;

    public WhileStmt(Exp e, IStmt s){
        exp = e;
        stmt = s;
    }

    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIStack<IStmt> stack = state.getExecStack();
        MyIHeap<Value> heap = state.getHeap();

        Value v1 = exp.eval(symTable, heap);
        if (v1.getType().equals(new BoolType())){
            BoolValue bool1 = (BoolValue) v1;
            if (bool1.getVal()){
                stack.push(new WhileStmt(exp.deepCopy(),stmt.deepCopy()));
                stack.push(stmt);
            }
        }
        else throw new StmtExecException("Expression not a bool value.");

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(), stmt.deepCopy());
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
        Type t_exp = exp.typecheck(table);
        if (t_exp.equals(new BoolType())){
            stmt.typecheck(clone(table));
            return table;
        }
        else{
            throw new TypeException("Condition not of type bool.");
        }
    }

    @Override
    public String toString() {
        return "(while (" + exp.toString() + ") " + stmt.toString() + ")";
    }
}
