package be.kuleuven.pccustomizer.controller.Beheer;

import be.kuleuven.pccustomizer.controller.Objects.Bestelling;
import be.kuleuven.pccustomizer.controller.Objects.CustomPC;
import be.kuleuven.pccustomizer.controller.config._beheerConfig;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class BeheerComputerConfigs extends _BeheerCommon {
    CustomPC modefiedCustomPc;
    List<CustomPC> customPCs = new ArrayList<CustomPC>();
    String comp;


    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClose;

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn<CustomPC, String> nameColumn;
    @FXML
    private TableColumn<CustomPC, String> typeColumn;
    @FXML
    private TableColumn<CustomPC, Integer> priceColumn;
    @FXML
    private TableColumn<CustomPC, String> motherBoardColumn;
    @FXML
    private TableColumn<CustomPC, String> cpuColumn;
    @FXML
    private TableColumn<CustomPC, String> gpuColumn;
    @FXML
    private TableColumn<CustomPC, String> ramColumn;
    @FXML
    private TableColumn<CustomPC, String> casesColumn;
    @FXML
    private TableColumn<CustomPC, String>  psuColumn;
    @FXML
    private TableColumn<CustomPC, String> storageColumn;
    @FXML
    private TableColumn<CustomPC, String> coolingColumn;
    @FXML
    private TableColumn<CustomPC, String> extraColumn;



    public void initialize() {
        ReadFromDB();
        initTable();
        btnDelete.setOnAction(e -> {
            verifyOneRowSelected(tableView);
            deleteCurrentRow();
        });
        
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }

    public void ReadFromDB(){
        List<String> names =  readDBstring("Computer","Name");
        List<String> types =  readDBstring("Computer","Type");
        List<Integer> prices =  readDBint("Bestelling","Price");
        List<String>  motherboards =  readDBstring("Computer","Motherbord");
        List<String> cpus =  readDBstring("Computer","CPU");
        List<String> gpus =  readDBstring("Computer","GPU");
        List<String> rams =  readDBstring("Computer","RAM");
        List<String> cases =  readDBstring("Computer","PcCase");
        List<String> psus =  readDBstring("Computer","Power_supply");
        List<String> storages =  readDBstring("Computer","Storage");
        List<String> coolings =  readDBstring("Computer","Cooling");
        List<String> extras =  readDBstring("Computer","Extra");



        for(int i = 0; i < names.size(); i++){
            customPCs.add(new CustomPC(names.get(i),types.get(i),prices.get(i), motherboards.get(i),cpus.get(i),
                    gpus.get(i), rams.get(i), cases.get(i), psus.get(i), storages.get(i),coolings.get(i), extras.get(i)));
        }
    }

    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, String>("Name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, String>("Type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, Integer>("Price"));
        motherBoardColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, String>("Motherbord"));
        cpuColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, String>("CPU"));
        gpuColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, String>("GPU"));
        ramColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, String>("RAM"));
        casesColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, String>("PcCase"));
        psuColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, String>("PSU"));
        storageColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, String>("Storage"));
        coolingColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, String>("Cooling"));
        extraColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, String>("Extra"));
        ObservableList<CustomPC> customPCList = tableView.getItems();
        customPCList.addAll(customPCs);
        tableView.setItems(customPCList);

    }



    public void deleteCurrentRow() {
        ObservableList<CustomPC> customPCList = tableView.getItems();
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        jdbi.useHandle(handle -> {
            handle.execute("DELETE FROM Computer WHERE Name = ?", customPCList.get(0).getName());
        });
        tableView.getItems().remove(selectedRow);
    }





}
