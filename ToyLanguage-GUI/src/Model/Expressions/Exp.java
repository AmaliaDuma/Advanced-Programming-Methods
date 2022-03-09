package Model.Expressions;
import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.ExpEvalException;
import Model.Exceptions.InterpreterException;
import Model.Types.Type;
import Model.Values.Value;

public interface Exp {
    Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws ExpEvalException;
    Exp deepCopy();
    Type typecheck(MyIDictionary<String,Type> table) throws InterpreterException;
}
