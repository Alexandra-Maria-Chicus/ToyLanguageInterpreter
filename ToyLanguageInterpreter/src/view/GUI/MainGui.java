package view.GUI;

import controller.Controller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.expression.*;
import model.state.PrgState;
import model.statement.*;
import model.type.BooleanType;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;
import repository.IRepository;
import repository.MyRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainGui extends Application {
    private controller.Controller controller;
    private ListView<Integer> prgIdList;
    private TableView<TableEntry<Integer, String>> heapTable;
    private TableView<TableEntry<String, String>> symTable;
    private ListView<String> outList;
    private ListView<String> fileList;
    private ListView<String> exeStackList;
    private TextField prgStateCount;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Interpreter - Select Program");
        List<IStmt> programs = getProgramList();

        ListView<IStmt> listView = new ListView<>();
        ObservableList<IStmt> observableList = FXCollections.observableArrayList(programs);
        listView.setItems(observableList);

        Button runButton = new Button("Run Selected Program");
        runButton.setOnAction(e -> {
            IStmt selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                openExecutionWindow(selected);
            }
        });
        VBox root = new VBox(10, listView, runButton);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openExecutionWindow(IStmt selectedProgram) {
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Execution Progress");
        this.prgStateCount = new TextField("0");
        prgStateCount.setEditable(false);

        this.heapTable = new TableView<>();
        TableColumn<TableEntry<Integer, String>, Integer> addrCol = new TableColumn<>("Address");
        addrCol.setCellValueFactory(new PropertyValueFactory<>("key"));
        TableColumn<TableEntry<Integer, String>, String> heapValCol = new TableColumn<>("Value");
        heapValCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        heapTable.getColumns().addAll(addrCol, heapValCol);

        this.outList = new ListView<>();
        this.fileList = new ListView<>();
        this.prgIdList = new ListView<>();
        this.prgIdList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateSpecificDetails(newValue);
            }
        });

        this.symTable = new TableView<>();
        TableColumn<TableEntry<String, String>, String> varCol = new TableColumn<>("Variable");
        varCol.setCellValueFactory(new PropertyValueFactory<>("key"));
        TableColumn<TableEntry<String, String>, String> symValCol = new TableColumn<>("Value");
        symValCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        symTable.getColumns().addAll(varCol, symValCol);

        this.exeStackList = new ListView<>();

        Button stepButton = new Button("Run One Step");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.add(new Label("PrgStates:"), 0, 0); grid.add(prgStateCount, 1, 0);
        grid.add(new Label("Heap:"), 0, 1); grid.add(heapTable, 0, 2);
        grid.add(new Label("Out:"), 1, 1); grid.add(outList, 1, 2);
        grid.add(new Label("Files:"), 2, 1); grid.add(fileList, 2, 2);
        grid.add(new Label("IDs:"), 0, 3); grid.add(prgIdList, 0, 4);
        grid.add(new Label("SymTable:"), 1, 3); grid.add(symTable, 1, 4);
        grid.add(new Label("Stack:"), 2, 3); grid.add(exeStackList, 2, 4);
        grid.add(stepButton, 1, 5);

        Scene scene = new Scene(grid, 900, 600);
        secondaryStage.setScene(scene);
        secondaryStage.show();

        setupController(selectedProgram);
        updateUI();
        stepButton.setOnAction(e -> {
            try {
                List<PrgState> prgList = controller.removeCompletedPrg(controller.getRepository().getPrgStates());
                if (prgList.isEmpty()) {
                    new Alert(Alert.AlertType.INFORMATION, "Execution finished.").showAndWait();
                    return;
                }
                controller.oneStepForAllPrg(prgList);
                updateUI();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
            }
        });
    }

    private void updateSpecificDetails(Integer selectedId) {
        PrgState selectedPrg = controller.getRepository().getPrgStates().stream()
                .filter(p -> p.getProgramId() == selectedId)
                .findFirst()
                .orElse(null);

        if (selectedPrg != null) {
            List<TableEntry<String, String>> symEntries = selectedPrg.getSymTable().getContent().entrySet().stream()
                    .map(e -> new TableEntry<>(e.getKey(), e.getValue().toString()))
                    .collect(java.util.stream.Collectors.toList());
            symTable.setItems(FXCollections.observableArrayList(symEntries));

            List<String> stackList = selectedPrg.getExeStack().getStackAsList().stream()
                    .map(Object::toString)
                    .collect(java.util.stream.Collectors.toList());
            exeStackList.setItems(FXCollections.observableArrayList(stackList));
        }
    }

    private void setupController(IStmt selectedProgram) {
        PrgState initialPrgState = new PrgState(selectedProgram);
        IRepository repo = new MyRepository(new java.util.ArrayList<>(Arrays.asList(initialPrgState)), "log_gui.txt");
        this.controller = new Controller(repo);
    }
    private void updateUI() {
        List<PrgState> prgStates = controller.getRepository().getPrgStates();
        if(prgStates.isEmpty()) {
            prgIdList.getItems().clear();
            return;
        }

        prgStateCount.setText(String.valueOf(prgStates.size()));

        Map<Integer, Value> heapContent = prgStates.get(0).getHeap().getContent();
        List<TableEntry<Integer, String>> heapEntries = heapContent.entrySet().stream()
                .map(e -> new TableEntry<>(e.getKey(), e.getValue().toString()))
                .collect(java.util.stream.Collectors.toList());
        heapTable.setItems(FXCollections.observableArrayList(heapEntries));

        outList.setItems(FXCollections.observableArrayList(prgStates.get(0).getOut().toStringList()));

        fileList.setItems(FXCollections.observableArrayList(
                prgStates.get(0).getFileTable().getContent().keySet().stream()
                        .map(Object::toString).collect(java.util.stream.Collectors.toList())
        ));

        List<Integer> ids = prgStates.stream().map(PrgState::getProgramId).collect(java.util.stream.Collectors.toList());
        prgIdList.setItems(FXCollections.observableArrayList(ids));

        if (prgIdList.getSelectionModel().getSelectedItem() == null && !ids.isEmpty()) {
            prgIdList.getSelectionModel().select(0);
        }

        Integer selectedId = prgIdList.getSelectionModel().getSelectedItem();
        if (selectedId != null) {
            updateSpecificDetails(selectedId);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private List<IStmt> getProgramList() {
        IStmt ex1 = new CompStmt(new VarDeclStmt(new IntType(), "v"),
                new CompStmt(new AssignStmt(new ValueExp(new IntValue(2)), "v"), new PrintStmt(new
                        VarExp("v"))));
        IStmt ex2 = new CompStmt(new VarDeclStmt(new IntType(), "a"),
                new CompStmt(new VarDeclStmt(new IntType(), "b"),
                        new CompStmt(new AssignStmt(new ArithmeticExp(new ValueExp(new IntValue(2)), new
                                ArithmeticExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), "*"), "+"), "a"),
                                new CompStmt(new AssignStmt(new ArithmeticExp(new VarExp("a"), new ValueExp(new
                                        IntValue(1)), "+"), "b"), new PrintStmt(new VarExp("b"))))));
        IStmt ex3 = new CompStmt(new VarDeclStmt(new BooleanType(), "a"),
                new CompStmt(new VarDeclStmt(new IntType(), "v"),
                        new CompStmt(new AssignStmt(new ValueExp(new BoolValue(true)), "a"),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt(new ValueExp(new
                                        IntValue(2)), "v"), new AssignStmt(new ValueExp(new IntValue(3)), "v")), new PrintStmt(new
                                        VarExp("v"))))));
        IStmt ex1_varf = new VarDeclStmt(new StringType(), "varf");
        IStmt ex2_assign = new AssignStmt(new ValueExp(new StringValue("test.in")), "varf");
        IStmt ex3_open = new OpenRFileStmt(new VarExp("varf"));
        IStmt ex4_varc = new VarDeclStmt(new IntType(), "varc");
        IStmt ex5_read_print = new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"), new PrintStmt(new VarExp("varc")));
        IStmt ex6_read_print = ex5_read_print;
        IStmt ex7_close = new CloseRFileStmt(new VarExp("varf"));
        IStmt ex4 = new CompStmt(ex1_varf,
                new CompStmt(ex2_assign,
                        new CompStmt(ex3_open,
                                new CompStmt(ex4_varc,
                                        new CompStmt(ex5_read_print,
                                                new CompStmt(ex6_read_print, ex7_close))))));
        IStmt ex5 =new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt(new RefType(new RefType(new IntType())), "a"),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new VarExp("a")))))));
        IStmt ex6 = new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt(new RefType(new RefType(new IntType())), "a"),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
                                                new PrintStmt(new ArithmeticExp(
                                                        new rH(new rH(new VarExp("a"))), new ValueExp(new IntValue(5)),"+")))))));
        IStmt ex7 = new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
                                new CompStmt(new wH("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithmeticExp(new rH(new VarExp("v")),
                                                new ValueExp(new IntValue(5)),"+"))))));
        IStmt ex8 = new CompStmt(new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt(new RefType(new RefType(new IntType())), "a"),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new rH(new rH(new VarExp("a")))))))));
        IStmt ex9 = new CompStmt(new VarDeclStmt(new IntType(), "v"),
                new CompStmt(new AssignStmt( new ValueExp(new IntValue(4)),"v"),
                        new CompStmt(new WhileStmt(new RelationalExp( new VarExp("v"), new ValueExp(new IntValue(0)),">"),
                                new CompStmt(new PrintStmt(new VarExp("v")),
                                        new AssignStmt(new ArithmeticExp( new VarExp("v"), new ValueExp(new IntValue(1)),"-"),"v"))),
                                new PrintStmt(new VarExp("v")))));
        IStmt ex10 = new CompStmt(new VarDeclStmt(new IntType(), "v"),
                new CompStmt(new VarDeclStmt(new RefType(new IntType()), "a"),
                        new CompStmt(new AssignStmt(new ValueExp(new IntValue(10)), "v"),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new wH("a", new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt(new ValueExp(new IntValue(32)),"v"),
                                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                                new PrintStmt(new rH(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new rH(new VarExp("a")))))))));

        return Arrays.asList(ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9, ex10);
    }
}