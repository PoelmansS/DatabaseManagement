package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.CPU;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class beheerCPU extends _BeheerCommon {
    CPU modifiedCPU;

    //table
    @FXML
    private TableView<CPU> tableView;
    //input text fields
    @FXML
    private TextField addName;
    @FXML
    private TextField addPrice;
    @FXML
    private TextField addThreads;
    @FXML
    private TextField addCores;
    @FXML
    private TextField addClockSpeed;
    @FXML
    private TextField addPowerUsage;

    @FXML
    private TableColumn<CPU, String> nameColumn;
    @FXML
    private TableColumn<CPU, Integer> priceColumn;
    @FXML
    private TableColumn<CPU, Integer> threadsColumn;
    @FXML
    private TableColumn<CPU, Integer> coresColumn;
    @FXML
    private TableColumn<CPU, Integer> clockSpeedColumn;
    @FXML
    private TableColumn<CPU, Integer> powerUsageColumn;

    public void initialize() {
        initTable();
        btnAdd.setOnAction(e -> {
            verifyInput();
        });
        btnModify.setOnAction(e -> {
            verifyOneRowSelected(tableView);
            verifyModifyInput();
        });
        btnDelete.setOnAction(e -> {
            verifyOneRowSelected(tableView);
            deleteCurrentRow(tableView);
        });
        btnLoad.setOnAction(e -> {
            LoadCurrentRow();
        });
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }
    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<CPU, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<CPU, Integer>("price"));
        threadsColumn.setCellValueFactory(new PropertyValueFactory<CPU, Integer>("threads"));
        coresColumn.setCellValueFactory(new PropertyValueFactory<CPU, Integer>("cores"));
        clockSpeedColumn.setCellValueFactory(new PropertyValueFactory<CPU, Integer>("clockSpeed"));
        powerUsageColumn.setCellValueFactory(new PropertyValueFactory<CPU, Integer>("powerUsage"));



        CPU CPU1 = new CPU("i5",200,6,4,6,100);
        CPU CPU2 = new CPU("i7",300,8,6,6,200);
        CPU CPU3 = new CPU("i9",400,12,8,6,300);
        ObservableList<CPU> CPUList = tableView.getItems();
        CPUList.add(CPU1);
        CPUList.add(CPU2);
        CPUList.add(CPU3);
        tableView.setItems(CPUList);
    }
    public void addNewRow() {
            CPU cpu = new CPU(addName.getText(), Integer.parseInt(addPrice.getText()), Integer.parseInt(addThreads.getText()),
                    Integer.parseInt(addCores.getText()), Integer.parseInt(addClockSpeed.getText()), Integer.parseInt(addPowerUsage.getText()));
            ObservableList<CPU> CPUList = tableView.getItems();
            CPUList.add(cpu);
            tableView.setItems(CPUList);
    }


    public void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            CPU cpu = tableView.getSelectionModel().getSelectedItem();
            addName.setText(cpu.getName());
            addPrice.setText(String.valueOf(cpu.getPrice()));
            addThreads.setText(String.valueOf(cpu.getThreads()));
            addCores.setText(String.valueOf(cpu.getCores()));
            addClockSpeed.setText(String.valueOf(cpu.getClockSpeed()));
            addPowerUsage.setText(String.valueOf(cpu.getPowerUsage()));
            modifiedCPU = new CPU(cpu.getName(),cpu.getPrice(),cpu.getThreads(),cpu.getCores(),cpu.getClockSpeed(),cpu.getPowerUsage());
        }
    }
    public void modifyCurrentRow(){
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedCPU.setName(addName.getText());
        modifiedCPU.setPrice(Integer.parseInt(addPrice.getText()));
        modifiedCPU.setThreads(Integer.parseInt(addThreads.getText()));
        modifiedCPU.setCores(Integer.parseInt(addCores.getText()));
        modifiedCPU.setClockSpeed(Integer.parseInt(addClockSpeed.getText()));
        modifiedCPU.setPowerUsage(Integer.parseInt(addPowerUsage.getText()));

        ObservableList<CPU> CPUList = tableView.getItems();
        CPUList.set(selectedRow,modifiedCPU);
        tableView.setItems(CPUList);
    }






}
