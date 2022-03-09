package Model.Expressions;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.ExpEvalException;
import Model.Exceptions.InterpreterException;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;

public class RelationalExp implements Exp{

    private final Exp e1;
    private final Exp e2;
    String op;

    public RelationalExp(Exp e1, Exp e2, String op){
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws ExpEvalException {
        Value v1,v2;
        v1 = e1.eval(table, heap);
        if (v1.getType().equals(new IntType())){
            v2 = e2.eval(table, heap);
            if (v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;

                int n1,n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                return switch (op){
                    case "<" -> new BoolValue(n1 < n2);
                    case "<=" -> new BoolValue(n1 <= n2);
                    case "==" -> new BoolValue(n1 == n2);
                    case "!=" -> new BoolValue(n1 != n2);
                    case ">" -> new BoolValue(n1 > n2);
                    case ">=" -> new BoolValue(n1 >= n2);
                    default -> throw new ExpEvalException("Invalid operation.");
                };
            }
            else throw new ExpEvalException("Operand 2 is not an integer.");
        }
        else throw new ExpEvalException("Operand 1 is not an integer");
    }

    @Override
    public Exp deepCopy() {
        return new RelationalExp(e1.deepCopy(), e2.deepCopy(), op);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> table) throws InterpreterException {
        Type t1,t2;
        t1 = e1.typecheck(table);
        t2 = e2.typecheck(table);

        if (t1.equals(new IntType())){
            if (t2.equals(new IntType())){
                return new BoolType();
            }
            else{
                throw new ExpEvalException("Second operand is not an integer");
            }
        }
        else{
            throw new ExpEvalException("First operand is not an integer");
        }
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }

}
