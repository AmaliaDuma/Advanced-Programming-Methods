package View;

import Controller.Controller;
import Model.Exceptions.InterpreterException;

public class RunCommand implements Command{
    private String key, description;
    Controller c;

    public RunCommand(String k, String d, Controller c1) { key = k; description = d; c = c1;}

    public String getKey() {
        return key;
    }

    public String getDescription(){
        return description;
    }

    @Override
    public void execute() {
        try {
            c.typecheck();
            c.allSteps();
        } catch (InterpreterException e){
            System.out.println(e.getMessage());
        }
    }
}
