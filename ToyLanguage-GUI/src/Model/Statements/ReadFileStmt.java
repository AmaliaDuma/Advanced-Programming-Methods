package Model.Statements;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.FileException;
import Model.Exceptions.InterpreterException;
import Model.Exceptions.StmtExecException;
import Model.Exceptions.TypeException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt{

    private Exp e;
    private String var_name;

    public ReadFileStmt(Exp e, String var_name) {
        this.e = e;
        this.var_name = var_name;
    }

    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Value> heap = state.getHeap();

        if (symTable.isDefined(var_name)){
            Value v = symTable.lookup(var_name);
            if (v.getType().equals(new IntType())){
                Value v1 = e.eval(symTable,heap);
                if (v1.getType().equals(new StringType())){
                    StringValue sval = (StringValue) v1;
                    if (fileTable.isDefined(sval)){
                        BufferedReader br = fileTable.lookup(sval);
                        try{
                            String line = br.readLine();
                            IntValue int_v;
                            if (line == null) int_v = new IntValue();
                            else int_v = new IntValue(Integer.parseInt(line));
                            symTable.update(var_name, int_v);
                        }catch (IOException e){
                            throw new FileException("Cannot read from file.");
                        }
                    }
                    else throw new StmtExecException("Expression not defined.");
                }
                else throw new StmtExecException("Expression not a String type.");
            }
            else throw new StmtExecException("Variable not an int type.");

        }
        else throw new StmtExecException("Variable not defined.");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStmt(e.deepCopy(), var_name);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> table) throws InterpreterException {
        Type t_var = table.lookup(var_name);
        Type t_exp = e.typecheck(table);
        if (t_exp.equals(new StringType())){
            if (t_var.equals(new IntType())){
                return table;
            }
            else throw new TypeException("Variable not of type int.");
        }
        else throw new TypeException("Expression not of type string.");
    }

    @Override
    public String toString() {
        return "readFile(" + e.toString() + ", " + var_name + ")";
    }
}
