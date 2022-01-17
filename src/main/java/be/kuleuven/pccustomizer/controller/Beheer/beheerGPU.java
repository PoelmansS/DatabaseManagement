package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.GPU;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class beheerGPU extends _BeheerCommon {
    private GPU modifiedGPU;
    private final List<GPU> gpus = new ArrayList<GPU>();

    @FXML
    private TableView<GPU> tableView;

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
        init(tableView);
    }

    public void ReadFromDB(){
        List<String> names = readDBstring("GPU","Name");
        List<Integer> prices =  readDBint("GPU","Price");
        List<Integer> vrams =  readDBint("GPU","Vram_size");
        List<Integer> powerUsages =  readDBint("GPU","Power_Usage");

        for(int i = 0; i < names.size(); i++){
            gpus.add(new GPU(names.get(i), prices.get(i), vrams.get(i), powerUsages.get(i)));
        }
    }

    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<GPU, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<GPU, Integer>("price"));
        VRAMColumn.setCellValueFactory(new PropertyValueFactory<GPU, Integer>("VRAM"));
        powerUsageColumn.setCellValueFactory(new PropertyValueFactory<GPU, Integer>("powerUsage"));

        ObservableList<GPU> GPUList = tableView.getItems();
        GPUList.addAll(gpus);
        tableView.setItems(GPUList);
    }

    public void deleteCurrentRow() {
        ObservableList<GPU> GPUList = tableView.getItems();
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        jdbi.useHandle(handle -> {
            handle.execute("DELETE FROM GPU WHERE Name = ?", GPUList.get(0).getName());
        });
        tableView.getItems().remove(selectedRow);
    }

    public void addNewRow() {
        if(!doubles("GPU", "Name", addName.getText())) {
            GPU gpu = new GPU(addName.getText(), Integer.parseInt(addPrice.getText()), Integer.parseInt(addVRAM.getText()),
                    Integer.parseInt(addPowerUsage.getText()));
            jdbi.useHandle(handle -> {
                handle.execute("insert into GPU (Name,  Price, Vram_size, Power_Usage) values (?, ?, ?, ?)",
                        gpu.getName(), gpu.getPrice(), gpu.getVRAM(), gpu.getPowerUsage());
            });
            ObservableList<GPU> GPUList = tableView.getItems();
            GPUList.add(gpu);
            tableView.setItems(GPUList);
        }
        else{
            showAlert("Unseported Entry","Dit component bestaat al in de db");
        }
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

        jdbi.useHandle(handle -> {
            handle.execute("UPDATE GPU SET Name = ? ,Price = ?, Vram_size = ? , Power_usage = ? WHERE Name = ?",
                    modifiedGPU.getName(), modifiedGPU.getPrice(), modifiedGPU.getVRAM(),
                    modifiedGPU.getPowerUsage(), modifiedGPU.getName());
        });

        ObservableList<GPU> GPUList = tableView.getItems();
        GPUList.set(selectedRow,modifiedGPU);
        tableView.setItems(GPUList);
    }
}
