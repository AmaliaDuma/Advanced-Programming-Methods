package Model.Expressions;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.ExpEvalException;
import Model.Exceptions.InterpreterException;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class ArithExp implements Exp{

    private final Exp e1;
    private final Exp e2;
    private int op; //1-plus, 2-minus, 3-star, 4-divide
    private char op_char;

    public ArithExp(char operation, Exp e1, Exp e2){
        this.e1 = e1;
        this.e2 = e2;
        op_char = operation;
        switch (operation){
            case '+' -> this.op = 1;
            case '-' -> this.op = 2;
            case '*' -> this.op = 3;
            case '/' -> this.op = 4;
        }
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
                switch (this.op){
                    case 1 :
                        return new IntValue(n1+n2);
                    case 2:
                        return new IntValue(n1-n2);
                    case 3:
                        return new IntValue(n1*n2);
                    case 4:
                        if (n2==0) throw new ExpEvalException("Division by zero");
                        else return new IntValue(n1/n2);
                    default:
                        throw new ExpEvalException("Invalid operation.");
                }
            }
            else throw new ExpEvalException("Operand 2 is not an integer.");
        }
        else throw new ExpEvalException("Operand 1 is not an integer");
    }

    @Override
    public Exp deepCopy() {
        return new ArithExp(op_char, e1.deepCopy(), e2.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> table) throws InterpreterException {
        Type t1,t2;
        t1 = e1.typecheck(table);
        t2 = e2.typecheck(table);

        if (t1.equals(new IntType())){
            if (t2.equals(new IntType())){
                return new IntType();
            }
            else{
                throw new ExpEvalException("Second operand is not an integer.");
            }
        }
        else{
            throw new ExpEvalException("First operand is not an integer.");
        }
    }

    @Override
    public String toString() {
        return switch (this.op){
            case 1 -> e1.toString() + "+" + e2.toString();
            case 2 -> e1.toString() + "-" + e2.toString();
            case 3 -> e1.toString() + "*" + e2.toString();
            case 4 -> e1.toString() + "/" + e2.toString();
            default -> "";
        };
    }

}
