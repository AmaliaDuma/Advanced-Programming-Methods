package Gui;

import Model.ADTs.MyDictionary;
import Model.Exceptions.InterpreterException;
import Model.Statements.IStmt;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collectors;

public class PListController {

    @FXML
    private ListView<String> prgListLV;
    @FXML
    private Button exec_bt;

    private final List<IStmt> programs = new ArrayList<>();
    private Stage stage;

    private ObservableList<String> get_programs(){
        return FXCollections.observableList(programs.stream()
                .map(Object::toString).collect(Collectors.toList()));
    }

    @FXML
    private void initialize(){
        prgListLV.setItems(get_programs());
    }

    public void addProgram(IStmt s){
        programs.add(s);
        prgListLV.setItems(get_programs());
    }

    public void setStage(Stage s){
        stage=s;
    }

    private void raise_alert(String s){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(s);
        alert.show();
    }

    public void execute_prg(){
        try {
            int index = prgListLV.getSelectionModel().getSelectedIndex();

            if (index == -1) {
                raise_alert("Select a program.");
                return;
            }
            launch_window(index);
        } catch (Exception e) {
            raise_alert(e.getMessage());
            e.printStackTrace();
        }
    }

    private void launch_window(int index) throws IOException {
        IStmt stmt = programs.get(index);
        try {
            stmt.typecheck(new MyDictionary<>());
        }
        catch (Exception e){
            raise_alert(e.getMessage());
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainp.fxml"));
        AnchorPane sec_layout = (AnchorPane) loader.load();
        ProgramController pcont = loader.getController();
        pcont.set_state(stmt, index);
        Scene nscene = new Scene(sec_layout);

        Stage newStage = new Stage();
        newStage.setTitle("Program: "+Integer.toString(index));
        newStage.setScene(nscene);
        newStage.show();

    }

}

