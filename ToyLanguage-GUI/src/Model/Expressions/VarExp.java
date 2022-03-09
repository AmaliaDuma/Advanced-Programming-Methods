package Model.Expressions;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.ExpEvalException;
import Model.Exceptions.InterpreterException;
import Model.Types.Type;
import Model.Values.Value;

public class VarExp implements Exp{

    private final String key;

    public VarExp(String k) { key=k; }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws ExpEvalException {
        if (!table.isDefined(key))
            throw new ExpEvalException("Variable "+key+" not defined.");
        return table.lookup(key);
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(key);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> table) throws InterpreterException {
        return table.lookup(key);
    }

    @Override
    public String toString() {
        return key;
    }

}
