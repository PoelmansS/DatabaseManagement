package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.GPU;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class beheerGPU extends _BeheerCommon {
    GPU modifiedGPU;

    //table
    @FXML
    private TableView<GPU> tableView;
    //input text fields
    @FXML
    private TextField addName;
    @FXML
    private TextField addPrice;
    @FXML
    private TextField addVRAM;
    @FXML
    private TextField addPowerUsage;

    @FXML
    private TableColumn<GPU, String> nameColumn;
    @FXML
    private TableColumn<GPU, Integer> priceColumn;
    @FXML
    private TableColumn<GPU, Integer> VRAMColumn;
    @FXML
    private TableColumn<GPU, Integer> powerUsageColumn;

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
        nameColumn.setCellValueFactory(new PropertyValueFactory<GPU, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<GPU, Integer>("price"));
        VRAMColumn.setCellValueFactory(new PropertyValueFactory<GPU, Integer>("VRAM"));
        powerUsageColumn.setCellValueFactory(new PropertyValueFactory<GPU, Integer>("powerUsage"));

        GPU GPU1 = new GPU("3060",600,8,200);
        GPU GPU2 = new GPU("3070",1000,16,400);
        GPU GPU3 = new GPU("3080",1500,24,600);
        ObservableList<GPU> GPUList = tableView.getItems();
        GPUList.add(GPU1);
        GPUList.add(GPU2);
        GPUList.add(GPU3);
        tableView.setItems(GPUList);
    }
    public void addNewRow() {
        GPU gpu = new GPU(addName.getText(), Integer.parseInt(addPrice.getText()), Integer.parseInt(addVRAM.getText()),
                 Integer.parseInt(addPowerUsage.getText()));
        ObservableList<GPU> GPUList = tableView.getItems();
        GPUList.add(gpu);
        tableView.setItems(GPUList);
    }

    public void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            GPU gpu = tableView.getSelectionModel().getSelectedItem();
            addName.setText(gpu.getName());
            addPrice.setText(String.valueOf(gpu.getPrice()));
            addVRAM.setText(String.valueOf(gpu.getVRAM()));
            addPowerUsage.setText(String.valueOf(gpu.getPowerUsage()));
            modifiedGPU = new GPU(gpu.getName(),gpu.getPrice(),gpu.getVRAM(),gpu.getPowerUsage());
        }
    }
    public void modifyCurrentRow(){
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedGPU.setName(addName.getText());
        modifiedGPU.setPrice(Integer.parseInt(addPrice.getText()));
        modifiedGPU.setVRAM(Integer.parseInt(addVRAM.getText()));
        modifiedGPU.setPowerUsage(Integer.parseInt(addPowerUsage.getText()));

        ObservableList<GPU> GPUList = tableView.getItems();
        GPUList.set(selectedRow,modifiedGPU);
        tableView.setItems(GPUList);
    }



}
