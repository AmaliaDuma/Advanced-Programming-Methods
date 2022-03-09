
import Gui.PListController;
import Model.Expressions.*;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
    private PListController programs;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Gui/plist.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            programs = loader.getController();
            addPrograms_controller();
            programs.setStage(primaryStage);

            Scene scene = new Scene(root, 650, 450);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Toy Language");
            primaryStage.setX(0);
            primaryStage.setY(0);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void addPrograms_controller(){
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        programs.addProgram(ex1);

        IStmt ex2 = new CompStmt(new CompStmt(new VarDeclStmt("a", new IntType()), new VarDeclStmt("b", new IntType())),
                new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)),
                        new ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                        new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"),
                                new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b")))));
        programs.addProgram(ex2);

        IStmt ex3 = new CompStmt(new CompStmt(new CompStmt(new VarDeclStmt("a", new BoolType()), new VarDeclStmt("v",
                new IntType())), new AssignStmt("a", new ValueExp(new BoolValue(true)))), new CompStmt(
                new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))));
        programs.addProgram(ex3);

        IStmt ex4 = new CompStmt(new CompStmt(new CompStmt(new VarDeclStmt("varf", new StringType()),
                new AssignStmt("varf", new ValueExp(new StringValue("test.in")))),
                new CompStmt(new OpenRFileStmt(new VarExp("varf")), new VarDeclStmt("varc",
                        new IntType()))), new CompStmt(new CompStmt(
                new ReadFileStmt(new VarExp("varf"), "varc"),
                new PrintStmt(new VarExp("varc"))), new CompStmt(new CompStmt(
                new ReadFileStmt(new VarExp("varf"), "varc"),
                new PrintStmt(new VarExp("varc"))), new CloseRFileStmt(new VarExp("varf")))));
        programs.addProgram(ex4);

        IStmt ex5 = new CompStmt(new CompStmt(new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),
                new AllocateStmt("v", new ValueExp(new IntValue(20)))), new CompStmt(new VarDeclStmt("a",
                new RefType(new RefType(new IntType()))), new AllocateStmt("a", new VarExp("v")))),
                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a"))));
        programs.addProgram(ex5);

        IStmt ex6 = new CompStmt(new CompStmt(new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),
                new AllocateStmt("v", new ValueExp(new IntValue(20)))), new CompStmt(new VarDeclStmt("a",
                new RefType(new RefType(new IntType()))), new AllocateStmt("a", new VarExp("v")))),
                new CompStmt(new PrintStmt(new HReadExp(new VarExp("v"))), new PrintStmt(new
                        ArithExp('+',new HReadExp(new HReadExp(new VarExp("a"))),new ValueExp(new IntValue(5))))));
        programs.addProgram(ex6);

        IStmt ex7 = new CompStmt(new CompStmt(new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),
                new AllocateStmt("v", new ValueExp(new IntValue(20)))), new CompStmt(new PrintStmt(new
                HReadExp(new VarExp("v"))), new HWritingStmt("v",new ValueExp(new IntValue(30))))),
                new PrintStmt(new ArithExp('+',new HReadExp(new VarExp("v")),new ValueExp(new IntValue(5)))));
        programs.addProgram(ex7);

        IStmt ex8 = new CompStmt(new CompStmt(new VarDeclStmt("v", new IntType()), new AssignStmt("v",
                new ValueExp(new IntValue(4)))), new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"),
                new ValueExp(new IntValue(0)),">"), new CompStmt(new PrintStmt(new VarExp("v")),
                new AssignStmt("v", new ArithExp('-',new VarExp("v"), new ValueExp(new IntValue(1)))))),
                new PrintStmt(new VarExp("v"))));
        programs.addProgram(ex8);

        IStmt ex9 = new CompStmt(new CompStmt(new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),
                new AllocateStmt("v", new ValueExp(new IntValue(20)))), new CompStmt(new VarDeclStmt("a",
                new RefType(new RefType(new IntType()))), new AllocateStmt("a", new VarExp("v")))),
                new CompStmt(new AllocateStmt("v",new ValueExp(new IntValue(30))),new PrintStmt(
                        new HReadExp(new HReadExp(new VarExp("a"))))));
        programs.addProgram(ex9);

        IStmt ex10= new CompStmt(new CompStmt(new CompStmt(new VarDeclStmt("v",new IntType()),new VarDeclStmt("a",
                new RefType(new IntType()))),new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(10))),
                new AllocateStmt("a",new ValueExp(new IntValue(22))))),new CompStmt(new ForkStmt(new CompStmt(new CompStmt(new
                HWritingStmt("a",new ValueExp(new IntValue(30))),new AssignStmt("v",new ValueExp(new IntValue(32)))),
                new CompStmt(new PrintStmt(new VarExp("v")),new PrintStmt(new HReadExp(new VarExp("a")))))),
                new CompStmt(new PrintStmt(new VarExp("v")),new CompStmt(new NopStmt(),new PrintStmt(new HReadExp(new VarExp("a")))))));
        programs.addProgram(ex10);

    }

    public static void main(String[] args) {
        launch(args);
    }
}