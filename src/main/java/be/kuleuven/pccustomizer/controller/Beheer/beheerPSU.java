package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class beheerPSU extends _BeheerCommon {
    private PSU modifiedPSU;
    private final List<PSU> psus = new ArrayList<PSU>();

    @FXML
    private TableView<PSU> tableView;
    @FXML
    private TextField addName;
    @FXML
    private TextField addPrice;
    @FXML
    private TextField addWattage;
    @FXML
    private TextField addAantal;
    @FXML
    private TableColumn<PSU, String> nameColumn;
    @FXML
    private TableColumn<PSU, Integer> wattageColumn;
    @FXML
    private TableColumn<PSU, Integer> priceColumn;
    @FXML
    private TableColumn<PSU, Integer> aantalColumn;

    public void initialize() {
        init(tableView);
    }

    public void ReadFromDB(){
        List<String> names = readDBstring("Power_supply","Name");
        List<Integer> prices =  readDBint("Power_supply","Price");
        List<Integer> wattages =  readDBint("Power_supply","Wattage");
        List<Integer> aantallen =  readDBint("Power_supply","Aantal");
        for(int i = 0; i < names.size(); i++){
            psus.add(new PSU(names.get(i), prices.get(i), wattages.get(i), aantallen.get(i)));
        }
    }

    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<PSU, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<PSU, Integer>("price"));
        wattageColumn.setCellValueFactory(new PropertyValueFactory<PSU, Integer>("wattage"));
        aantalColumn.setCellValueFactory(new PropertyValueFactory<PSU, Integer>("aantal"));

        ObservableList<PSU> PSUList = tableView.getItems();
        PSUList.addAll(psus);
        tableView.setItems(PSUList);

    }

    public void deleteCurrentRow() {
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            PSU psu = tableView.getSelectionModel().getSelectedItem();
            jdbi.useHandle(handle -> {
                handle.execute("DELETE FROM PSU WHERE Name = ?", psu.getName());
            });
            tableView.getItems().remove(selectedRow);
        }
    }

    public void addNewRow() {
        if(!doubles("Power_supply", "Name", addName.getText())) {
            PSU psu = new PSU(addName.getText(), Integer.parseInt(addPrice.getText()), Integer.parseInt(addWattage.getText()), Integer.parseInt(addAantal.getText()));
            jdbi.useHandle(handle -> {
                handle.execute("insert into Power_supply (Name ,Price, Wattage) values (?, ?, ?)",
                        psu.getName(), psu.getPrice(), psu.getWattage());
            });

            ObservableList<PSU> PSUList = tableView.getItems();
            PSUList.add(psu);
            tableView.setItems(PSUList);
        }
        else{
            showAlert("Unseported Entry","Dit component bestaat al in de db");
        }
    }

    public void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            PSU psu = tableView.getSelectionModel().getSelectedItem();
            addName.setText(psu.getName());
            addPrice.setText(String.valueOf(psu.getPrice()));
            addWattage.setText(String.valueOf(psu.getWattage()));
            addAantal.setText(String.valueOf(psu.getAantal()));

            modifiedPSU = new PSU(psu.getName(), psu.getPrice(), psu.getWattage(), psu.getAantal());
        }
    }

    public void modifyCurrentRow(){
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedPSU.setName(addName.getText());
        modifiedPSU.setPrice(Integer.parseInt(addPrice.getText()));
        modifiedPSU.setWattage(Integer.parseInt(addWattage.getText()));

        jdbi.useHandle(handle -> {
            handle.execute("UPDATE Power_supply SET Name = ? , Price = ?, Wattage = ? WHERE Name = ?",
                    modifiedPSU.getName(), modifiedPSU.getPrice(), modifiedPSU.getWattage(), modifiedPSU.getName());
        });

        ObservableList<PSU> PSUList = tableView.getItems();
        PSUList.set(selectedRow,modifiedPSU);
        tableView.setItems(PSUList);
    }
}
