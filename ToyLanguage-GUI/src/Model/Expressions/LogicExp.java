package Model.Expressions;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.ExpEvalException;
import Model.Exceptions.InterpreterException;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class LogicExp implements Exp{

    private final Exp e1;
    private final Exp e2;
    private int op; //1-and; 2-or
    private char op_char;

    public LogicExp(char operation, Exp e1, Exp e2){
        this.e1 = e1;
        this.e2 = e2;
        op_char = operation;
        switch (operation){
            case '&' -> this.op = 1;
            case '|' -> this.op = 2;
        }
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws ExpEvalException {
        Value v1,v2;
        v1 = e1.eval(table, heap);
        if (v1.getType().equals(new BoolType())){
            v2 = e2.eval(table, heap);
            if (v2.getType().equals(new BoolType())){
                BoolValue b1 = (BoolValue) v1;
                BoolValue b2 = (BoolValue) v2;

                boolean bool1,bool2;
                bool1 = b1.getVal();
                bool2 = b2.getVal();
                switch (this.op){
                    case 1: new BoolValue(bool1 & bool2);
                    case 2: new BoolValue(bool1 | bool2);
                    default:
                        throw new ExpEvalException("Invalid operation.");
                }
            }
            else throw new ExpEvalException("Operand 2 is not a boolean.");
        }
        else throw new ExpEvalException("Operand 1 is not a boolean.");
    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(op_char, e1.deepCopy(), e2.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> table) throws InterpreterException {
        Type t1,t2;
        t1 = e1.typecheck(table);
        t2 = e2.typecheck(table);

        if (t1.equals(new BoolType())){
            if (t2.equals(new BoolType())){
                return new BoolType();
            }
            else{
                throw new ExpEvalException("Second operand is not a boolean.");
            }
        }
        else{
            throw new ExpEvalException("First operand is not a boolean.");
        }
    }

    @Override
    public String toString() {
        return switch (this.op) {
            case 1 -> e1.toString() + "&" + e2.toString();
            case 2 -> e1.toString() + "|" + e2.toString();
            default -> "";
        };
    }

}
