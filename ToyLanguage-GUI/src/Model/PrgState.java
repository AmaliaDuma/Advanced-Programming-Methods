package Model;

import Model.ADTs.*;
import Model.Exceptions.InterpreterException;
import Model.Statements.IStmt;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;

public class PrgState {
    private MyIStack<IStmt> execStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap<Value> heap;
    private IStmt oprogram;
    private Integer id;
    private static Integer lastId=1;

    public PrgState(MyIStack<IStmt> e, MyIDictionary<String, Value> s, MyIList<Value> o, IStmt op){
        execStack = e;
        symTable = s;
        out = o;
        oprogram = op;
        execStack.push(oprogram);
    }

    public PrgState(MyIStack<IStmt> e, MyIDictionary<String, Value> s, MyIList<Value> o, MyIDictionary<StringValue, BufferedReader> f,IStmt op){
        execStack = e;
        symTable = s;
        out = o;
        fileTable = f;
        oprogram = op;
        execStack.push(oprogram);
        id = 1;
    }

    public PrgState(MyIStack<IStmt> e, MyIDictionary<String, Value> s, MyIList<Value> o, MyIDictionary<StringValue, BufferedReader> f, MyIHeap<Value> h,IStmt op){
        execStack = e;
        symTable = s;
        out = o;
        fileTable = f;
        heap = h;
        oprogram = op;
        execStack.push(oprogram);
        id = 1;
    }

    public PrgState(MyIStack<IStmt> e, MyIDictionary<String, Value> s, MyIList<Value> o, MyIDictionary<StringValue, BufferedReader> f, MyIHeap<Value> h){
        execStack = e;
        symTable = s;
        out = o;
        fileTable = f;
        heap = h;
    }

    public synchronized void setId(){
        lastId++;
        id = lastId;
    }

    public int getId(){
        return id;
    }

    public MyIStack<IStmt> getExecStack() {
        return execStack;
    }

    public void setExecStack(MyIStack<IStmt> execStack) {
        this.execStack = execStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() { return fileTable;}

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable) { this.fileTable = fileTable;}

    public MyIHeap<Value> getHeap() {
        return heap;
    }

    public void setHeap(MyIHeap<Value> heap) {
        this.heap = heap;
    }

    public IStmt getOprogram() {
        return oprogram;
    }

    public void setOprogram(IStmt oprogram) {
        this.oprogram = oprogram;
    }

    public boolean isCompleted(){
        return execStack.isEmpty();
    }

    public boolean isNotCompleted() {return !execStack.isEmpty();}

    public PrgState oneStep() throws InterpreterException{
        if (execStack.isEmpty())
            throw new InterpreterException("PrgState stack is empty");
        IStmt crtStmt = execStack.pop();
        return crtStmt.execute(this);
    }

    public void typecheck() throws InterpreterException{
        oprogram.typecheck(new MyDictionary<>());
    }

    @Override
    public String toString() {
        return "ProgramID: --------- " + id.toString() + " ---------\n" +
                "*****ExecutionStack*****\n" +
                execStack.toString() + "\n" +
                "*****SymbolTable*****\n" +
                symTable.toString() + "\n" +
                "*****OutputList*****\n" +
                out.toString() + "\n" +
                "*****FileTable*****\n" +
                fileTable.toString() + "\n" +
                "*****HeapTable*****\n" +
                heap.toString() + "\n" +
                "------------------------------------------------------\n\n\n";
    }
}
