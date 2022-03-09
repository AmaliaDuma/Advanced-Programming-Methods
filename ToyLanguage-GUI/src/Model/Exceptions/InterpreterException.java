package Model.Exceptions;

public class InterpreterException extends Exception{

    public InterpreterException(String message) {
        super(message);
    }

    public String getMessage(){
        return super.getMessage();
    }
}
