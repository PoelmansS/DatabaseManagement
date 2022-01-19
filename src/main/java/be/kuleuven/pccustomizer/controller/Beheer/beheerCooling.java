package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.Cooling;
import be.kuleuven.pccustomizer.controller.Objects.GPU;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class beheerCooling extends _BeheerCommon {
    private Cooling modifiedCooling;
    private final List<Cooling> coolings = new ArrayList<Cooling>();

    @FXML
    private TableView<Cooling> tableView;
    @FXML
    private TextField addName;
    @FXML
    private TextField addType;
    @FXML
    private TextField addPrice;
    @FXML
    private TextField addWattage;
    @FXML
    private TextField addAantal;
    @FXML
    private TableColumn<Cooling, String> nameColumn;
    @FXML
    private TableColumn<Cooling, String> typeColumn;
    @FXML
    private TableColumn<Cooling, Integer> priceColumn;
    @FXML
    private TableColumn<Cooling, Integer> wattageColumn;
    @FXML
    private TableColumn<Cooling, Integer> aantalColumn;

    public void initialize() {
        init(tableView);
    }

    public void ReadFromDB(){
        List<String> names = readDBstring("Cooling","Name");
        List<String> types =  readDBstring("Cooling","Type");
        List<Integer> prices =  readDBint("Cooling","Price");
        List<Integer> wattages =  readDBint("Cooling","Wattage");
        List<Integer> aantallen =  readDBint("Cooling","Aantal");

        for(int i = 0; i < names.size(); i++){
            coolings.add(new Cooling(names.get(i), types.get(i), prices.get(i), wattages.get(i), aantallen.get(i)));
        }
    }

    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Cooling, String>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Cooling, String>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Cooling, Integer>("price"));
        wattageColumn.setCellValueFactory(new PropertyValueFactory<Cooling, Integer>("wattage"));
        aantalColumn.setCellValueFactory(new PropertyValueFactory<Cooling, Integer>("aantal"));

        ObservableList<Cooling> coolingList = tableView.getItems();
        coolingList.addAll(coolings);
        tableView.setItems(coolingList);
    }

    public void deleteCurrentRow() {
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Cooling cooling = tableView.getSelectionModel().getSelectedItem();
            jdbi.useHandle(handle -> {
                handle.execute("DELETE FROM Cooling WHERE Name = ?", cooling.getName());
            });
            tableView.getItems().remove(selectedRow);
        }
    }

    public void addNewRow() {
        if(!doubles("Cooling", "Name", addName.getText())) {
            Cooling cooling = new Cooling(addName.getText(), addType.getText(), Integer.parseInt(addPrice.getText()), Integer.parseInt(addWattage.getText()), Integer.parseInt(addAantal.getText()));
            jdbi.useHandle(handle -> {
                handle.execute("insert into Cooling (Name, Type, Price, Wattage) values (?, ?, ?, ?)",
                        cooling.getName(), cooling.getType(), cooling.getPrice(), cooling.getWattage());
            });

            ObservableList<Cooling> coolingList = tableView.getItems();
            coolingList.add(cooling);
            tableView.setItems(coolingList);
        }
        else{
            Cooling cooling = new Cooling(addName.getText(), addType.getText(), Integer.parseInt(addPrice.getText()), Integer.parseInt(addWattage.getText()), Integer.parseInt(addAantal.getText()));
            jdbi.useHandle(handle -> {
                handle.execute("UPDATE Cooling SET Aantal = ? WHERE Name = ?, Type = ?, Price = ?, Wattage = ?",
                        cooling.getAantal(), cooling.getName(), cooling.getType(), cooling.getPrice(), cooling.getWattage());
            });
        }
    }

    public void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Cooling cooling  = tableView.getSelectionModel().getSelectedItem();
            addName.setText(cooling.getName());
            addType.setText(cooling.getType());
            addPrice.setText(String.valueOf(cooling.getPrice()));
            addWattage.setText(String.valueOf(cooling.getWattage()));
            addAantal.setText(String.valueOf(cooling.getAantal()));
            modifiedCooling = new Cooling(cooling.getName(), cooling.getType(), cooling.getPrice(), cooling.getWattage(), cooling.getAantal());
        }
    }

    public void modifyCurrentRow(){
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedCooling.setName(addName.getText());
        modifiedCooling.setType(addType.getText());
        modifiedCooling.setPrice(Integer.parseInt(addPrice.getText()));
        modifiedCooling.setWattage(Integer.parseInt(addWattage.getText()));
        modifiedCooling.setAantal(Integer.parseInt(addAantal.getText()));

        jdbi.useHandle(handle -> {
            handle.execute("UPDATE Cooling SET Name = ? ,Type = ?, Price = ? , Wattage = ?, Aantal = ? WHERE Name = ?",
                    modifiedCooling.getName(), modifiedCooling.getType(), modifiedCooling.getPrice(), modifiedCooling.getWattage(), modifiedCooling.getAantal(), modifiedCooling.getName());
        });
        ObservableList<Cooling> coolingsList = tableView.getItems();
        coolingsList.set(selectedRow,modifiedCooling);
        tableView.setItems(coolingsList);
    }
}
