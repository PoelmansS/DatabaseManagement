package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.controller.Objects.GPU;
import be.kuleuven.pccustomizer.controller.Objects.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class configGPU extends _ConfigCommon {
    private List<GPU> gpus = new ArrayList<GPU>();
    //table
    @FXML
    private TableView<GPU> tableView;
    @FXML
    private Button btnSkip;
    @FXML
    private TableColumn<GPU, String> nameColumn;
    @FXML
    private TableColumn<GPU, Integer> priceColumn;
    @FXML
    private TableColumn<GPU, Integer> VRAMColumn;
    @FXML
    private TableColumn<GPU, Integer> powerUsageColumn;
    @FXML
    private TableColumn<GPU, Integer> NRofSlotsColumn;

    public void initialize() {
        init(tableView, "Ram");
        btnSkip.setOnAction(e -> {
            skipComponent();
            showBeheerScherm("Ram");
        });
    }

    public void ReadFromDB(){
        List<String> names = readDBstring("GPU","Name");
        List<Integer> prices =  readDBint("GPU","Price");
        List<Integer> vrams =  readDBint("GPU","Vram_size");
        List<Integer> powerUsages =  readDBint("GPU","Power_Usage");
        List<Integer> NRofSlots =  readDBint("GPU","Number_of_slots");
        List<Integer> aantallen =  readDBint("GPU","Aantal");
        for(int i = 0; i < names.size(); i++){
            gpus.add(new GPU(names.get(i), prices.get(i), vrams.get(i), powerUsages.get(i), NRofSlots.get(i), aantallen.get(i)));
        }
    }

    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<GPU, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<GPU, Integer>("price"));
        VRAMColumn.setCellValueFactory(new PropertyValueFactory<GPU, Integer>("VRAM"));
        powerUsageColumn.setCellValueFactory(new PropertyValueFactory<GPU, Integer>("powerUsage"));
        NRofSlotsColumn.setCellValueFactory(new PropertyValueFactory<GPU, Integer>("NRofSlots"));
        ObservableList<GPU> GPUList = tableView.getItems();
        GPUList.addAll(gpus);
        tableView.setItems(GPUList);
    }

    public void addComponent(){
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            GPU gpu = tableView.getSelectionModel().getSelectedItem();
            component.setName(gpu.getName());
            componenten.add(component);
            totalW += gpu.getPowerUsage();
            benodigdePci += gpu.getNRofSlots();
        }
    }
}
