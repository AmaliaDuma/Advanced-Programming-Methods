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
import java.io.IOException;

public class CloseRFileStmt implements IStmt{

    private Exp e;

    public CloseRFileStmt(Exp e) {this.e = e;}

    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Value> heap = state.getHeap();

        Value v1 = e.eval(symTable, heap);
        if (v1.getType().equals(new StringType())){
            StringValue s_val = (StringValue) v1;
            if (fileTable.isDefined(s_val)){
                BufferedReader br = fileTable.lookup(s_val);
                try{
                    br.close();
                    fileTable.remove(s_val);
                }catch (IOException e){
                    throw new FileException("Error on closing the file.");
                }
            }
            else throw new StmtExecException("Expression not defined in fileTable.");
        }
        else throw new StmtExecException("Expression not a String type.");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFileStmt(e.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> table) throws InterpreterException {
        Type t = e.typecheck(table);
        if (t.equals(new StringType())){
            return table;
        }
        else{
            throw new TypeException("Expression not of type string.");
        }
    }

    @Override
    public String toString() {
        return "closeRFile(" + e.toString() + ")";
    }
}
