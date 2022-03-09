package Model.Expressions;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.ExpEvalException;
import Model.Exceptions.InterpreterException;
import Model.Types.Type;
import Model.Values.Value;

public class ValueExp implements Exp{

    private Value v;

    public ValueExp(Value v) {this.v=v;}

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws ExpEvalException {
        return v;
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(v.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> table) throws InterpreterException {
        return v.getType();
    }

    @Override
    public String toString() {
        return v.toString();
    }

}
