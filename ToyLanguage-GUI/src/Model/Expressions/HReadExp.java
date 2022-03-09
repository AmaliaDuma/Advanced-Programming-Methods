package Model.Expressions;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.ExpEvalException;
import Model.Exceptions.InterpreterException;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

import java.sql.Ref;

public class HReadExp implements Exp{
    private Exp exp;

    public HReadExp(Exp e) {exp = e;}

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws ExpEvalException {
        Value v1 = exp.eval(table, heap);
        if (v1 instanceof RefValue){
            RefValue rv = (RefValue) v1;
            int addr = rv.getAddr();
            if (heap.isDefined(addr)){
                Value v2 = heap.lookup(addr);
                return v2;
            }
            else throw new ExpEvalException("Address not a key in heap.");
        }
        else throw new ExpEvalException("Exp doesn't evaluate to a ref value.");
    }

    @Override
    public Exp deepCopy() {
        return new HReadExp(exp.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> table) throws InterpreterException {
        Type t = exp.typecheck(table);
        if (t instanceof RefType){
            RefType rt = (RefType) t;
            return rt.getInner();
        }
        else{
            throw new ExpEvalException("The rH argument is not a Ref Type.");
        }
    }

    @Override
    public String toString() {
        return "rH(" + exp.toString() + ")";
    }
}
