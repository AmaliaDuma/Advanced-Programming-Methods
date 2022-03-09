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

public class IfStmt implements IStmt{

    Exp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp e, IStmt t, IStmt el){
        exp=e; thenS=t; elseS=el;
    }

    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIStack<IStmt> stack = state.getExecStack();
        MyIHeap<Value> heap = state.getHeap();

        Value condition = exp.eval(symTable, heap);
        if (!condition.getType().equals(new BoolType())){
            throw new StmtExecException("Condition is not of boolean type");
        }
        else {
            if (condition.equals(new BoolValue(true)))
                stack.push(thenS);
            else
                stack.push(elseS);
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(), thenS.deepCopy(), elseS.deepCopy());
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
            thenS.typecheck(clone(table));
            elseS.typecheck(clone(table));
            return table;
        }
        else {
            throw new TypeException("The condition of IF has not the type bool.");
        }
    }

    @Override
    public String toString() {
        return "IF("+exp.toString()+") THEN ("+thenS.toString()+") ELSE ("+elseS.toString()+")";
    }
}
