package Model.Statements;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.FileException;
import Model.Exceptions.InterpreterException;
import Model.Exceptions.StmtExecException;
import Model.Exceptions.TypeException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStmt implements IStmt{

    private Exp e1;

    public OpenRFileStmt(Exp e) {e1 = e;}

    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Value> heap = state.getHeap();

        Value v = e1.eval(symTable, heap);
        if (v.getType().equals(new StringType())){
            StringValue s = (StringValue) v;
            if (!(fileTable.isDefined(s))){
                try{
                    BufferedReader br = new BufferedReader(new FileReader(s.getVal()));
                    fileTable.add(s, br);
                } catch (IOException exception) {
                    throw new FileException("File cannot be opened " + exception.getMessage());
                }
            }
            else throw new StmtExecException("String already exists in fileTable.");
        }
        else throw new StmtExecException("Expression is not a String type.");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFileStmt(e1.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> table) throws InterpreterException {
        Type t = e1.typecheck(table);
        if (t.equals(new StringType())){
            return table;
        }
        else{
            throw new TypeException("Expression not of type string.");
        }
    }

    @Override
    public String toString() {
        return "openRFile(" + e1.toString() +")";
    }
}
