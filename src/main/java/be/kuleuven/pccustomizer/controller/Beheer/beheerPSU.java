package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.CPU;
import be.kuleuven.pccustomizer.controller.Objects.Extra;
import be.kuleuven.pccustomizer.controller.Objects.PSU;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class beheerPSU extends _BeheerCommon {
    PSU modifiedPSU;
    List<PSU> psus = new ArrayList<PSU>();
    //table
    @FXML
    private TableView<PSU> tableView;
    //input text fields
    @FXML
    private TextField addName;
    @FXML
    private TextField addPrice;
    @FXML
    private TextField addWattage;


    @FXML
    private TableColumn<PSU, String> nameColumn;
    @FXML
    private TableColumn<PSU, Integer> wattageColumn;
    @FXML
    private TableColumn<PSU, Integer> priceColumn;

    public void initialize() {
        ReadFromDB();
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
            deleteCurrentRow();
        });
        btnLoad.setOnAction(e -> {
            LoadCurrentRow();
        });
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }

    public void ReadFromDB(){
        List<String> names = readDBstring("Power_supply","Name");
        List<Integer> prices =  readDBint("Power_supply","Price");
        List<Integer> wattages =  readDBint("Power_supply","Wattage");
        for(int i = 0; i < names.size(); i++){
            psus.add(new PSU(names.get(i), prices.get(i), wattages.get(i)));
        }
    }

    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<PSU, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<PSU, Integer>("price"));
        wattageColumn.setCellValueFactory(new PropertyValueFactory<PSU, Integer>("wattage"));

        ObservableList<PSU> PSUList = tableView.getItems();
        PSUList.addAll(psus);
        tableView.setItems(PSUList);

    }

    public void deleteCurrentRow() {
        ObservableList<PSU> PSUList = tableView.getItems();
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        jdbi.useHandle(handle -> {
            handle.execute("DELETE FROM Power_supply WHERE Name = ?", PSUList.get(0).getName());
        });
        tableView.getItems().remove(selectedRow);
    }

    public void addNewRow() {
        PSU psu = new PSU(addName.getText(), Integer.parseInt(addPrice.getText()), Integer.parseInt(addWattage.getText()));
        ObservableList<PSU> PSUList = tableView.getItems();
        PSUList.add(psu);
        tableView.setItems(PSUList);
    }

    public void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            PSU psu = tableView.getSelectionModel().getSelectedItem();
            addName.setText(psu.getName());
            addPrice.setText(String.valueOf(psu.getPrice()));
            addWattage.setText(String.valueOf(psu.getWattage()));

            modifiedPSU = new PSU(psu.getName(), psu.getPrice(),psu.getWattage());
        }
    }
    public void modifyCurrentRow(){
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedPSU.setName(addName.getText());
        modifiedPSU.setPrice(Integer.parseInt(addPrice.getText()));
        modifiedPSU.setWattage(Integer.parseInt(addWattage.getText()));

        ObservableList<PSU> PSUList = tableView.getItems();
        PSUList.set(selectedRow,modifiedPSU);
        tableView.setItems(PSUList);
    }

}
