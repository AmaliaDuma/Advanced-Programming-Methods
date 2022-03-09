package Gui;

import Controller.Controller;
import Model.ADTs.*;
import Model.PrgState;
import Model.Statements.IStmt;
import Model.Statements.IfStmt;
import Model.Values.Value;
import Repository.IRepo;
import Repository.Repo;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProgramController {

    @FXML
    public TableColumn<Pair<String, Value>, String> haddr;
    @FXML
    public TableColumn<Pair<String, Value>, Value> hval;
    @FXML
    public TableColumn<Pair<String, Value>, String> saddr;
    @FXML
    public TableColumn<Pair<String, Value>, Value> sval;
    @FXML
    private TableView<Pair<String, Value>> heapTV;
    @FXML
    private ListView<String> outLV;
    @FXML
    private ListView<String> prgIdLV;
    @FXML
    private ListView<String> fileLV;
    @FXML
    private TableView<Pair<String, Value>> symTV;
    @FXML
    private ListView<String> execLV;
    @FXML
    private Button runBt;
    @FXML
    private TextField nrStates_field;

    private Controller c;
    private PrgState crt_state;
    private int crt_id = 1;

    @FXML
    void initialize() {
        haddr.setCellValueFactory(new PairKeyFactory<>());
        hval.setCellValueFactory(new PairValueFactory<>());
        saddr.setCellValueFactory(new PairKeyFactory<>());
        sval.setCellValueFactory(new PairValueFactory<>());
        prgIdLV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    private void set_nr_states() {
        nrStates_field.setText(Integer.toString(c.getNr_prgStates()));
    }

    private void set_prgStates_id(List<PrgState> programs) {
        prgIdLV.setItems(FXCollections.observableList(programs.stream()
                .map(PrgState::getId).map(Object::toString).collect(Collectors.toList())));
    }

    private void set_exec_stack(PrgState state) {
        List<String> list = state.getExecStack().getStack().stream().toList().stream()
                .map(Object::toString).collect(Collectors.toList());
        Collections.reverse(list);
        execLV.setItems(FXCollections.observableList(list));

    }

    private void set_out(PrgState state) {
        outLV.setItems(FXCollections.observableList(state.getOut().getList().stream()
                .map(Object::toString).collect(Collectors.toList())));
    }

    private void set_file(PrgState state) {
        fileLV.setItems(FXCollections.observableList(state.getFileTable().getContent().keySet().stream()
                .map(Object::toString).collect(Collectors.toList())));
    }

    private void set_sym(PrgState state) {
        List<Pair<String, Value>> entries = new ArrayList<Pair<String, Value>>();
        for (Map.Entry<String, Value> item : state.getSymTable().getContent().entrySet()) {
            entries.add(new Pair<String, Value>(item.getKey(), item.getValue()));
        }
        symTV.setItems(FXCollections.observableList(entries));
    }

    private void set_heap(PrgState state) {
        List<Pair<String, Value>> entries = new ArrayList<Pair<String, Value>>();
        for (Map.Entry<Integer, Value> item : state.getHeap().getContent().entrySet()) {
            entries.add(new Pair<String, Value>(Integer.toString(item.getKey()), item.getValue()));
        }
        heapTV.setItems(FXCollections.observableList(entries));
    }

    private void set_components(PrgState state) {
        set_nr_states();
        set_prgStates_id(c.getRepo().getPrgList());
        set_heap(state);
        set_sym(state);
        set_file(state);
        set_exec_stack(state);
        set_out(state);
    }

    public void set_state(IStmt stmt, int index) {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), stmt);
        String fpath = "log" + Integer.toString(index+1) + ".txt";
        IRepo r = new Repo(fpath);
        r.addPrg(state);
        c = new Controller(r);
        c.open_executors();
        crt_state = state;

        set_components(state);
    }

    public void run_oneStep() {
        try {
            if (c.getNr_prgStates() > 0) {
                c.conservativeGarbageCollector(c.getRepo().getPrgList());
                c.oneStepForAllPrg(c.getRepo().getPrgList());
                set_components(get_byId(c.getRepo().getPrgList(), crt_id));
                c.getRepo().setPrgList(c.removeCompletedPrg(c.getRepo().getPrgList()));
            } else {
                raise_alert("Execution finished.");
                c.close_executors();
                return;
            }
        } catch (Exception e) {
            raise_alert(e.getMessage());
        }
    }

    @FXML
    public void switch_prgState(MouseEvent mouseEvent) {
        List<PrgState> programs = c.getRepo().getPrgList();
        String id = prgIdLV.getSelectionModel().getSelectedItem();
        PrgState selected_state = get_byId(programs, Integer.parseInt(id));
        crt_id = selected_state.getId();
        set_sym(selected_state);
        set_exec_stack(selected_state);
    }

    private PrgState get_byId(List<PrgState> list, int id){
        for (PrgState p : list){
            if (p.getId() == id)
                return p;
        }
        return null;
    }

    private void raise_alert(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(s);
        alert.show();
    }

    class PairKeyFactory<T, E> implements Callback<TableColumn.CellDataFeatures<Pair<T, E>, T>, ObservableValue<T>> {
        @Override
        public ObservableValue<T> call(TableColumn.CellDataFeatures<Pair<T, E>, T> data) {
            return new ReadOnlyObjectWrapper<>(data.getValue().getKey());
        }
    }

    class PairValueFactory<T, E> implements Callback<TableColumn.CellDataFeatures<Pair<T, E>, E>, ObservableValue<E>> {
        @Override
        public ObservableValue<E> call(TableColumn.CellDataFeatures<Pair<T, E>, E> data) {
            E value = data.getValue().getValue();
            if (value instanceof ObservableValue) {
                return (ObservableValue) value;
            } else {
                return new ReadOnlyObjectWrapper<>(value);
            }
        }
    }
}
