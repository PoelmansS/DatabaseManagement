package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.controller.Objects.Component;
import be.kuleuven.pccustomizer.controller.Objects.Cooling;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class configCooling extends _ConfigCommon {
    List<Cooling> coolings = new ArrayList<Cooling>();
    @FXML
    private TableView<Cooling> tableView;

    @FXML
    private TableColumn<Cooling, String> nameColumn;
    @FXML
    private TableColumn<Cooling, String> typeColumn;
    @FXML
    private TableColumn<Cooling, Integer> priceColumn;
    @FXML
    private TableColumn<Cooling, Integer> wattageColumn;


    public void initialize() {
        init(tableView, "PSU");
    }


    public void ReadFromDB(){
        List<String> names = readDBstring("Cooling","Name");
        List<String> types =  readDBstring("Cooling","Type");
        List<Integer> prices =  readDBint("Cooling","Price");
        List<Integer> wattages =  readDBint("Cooling","Wattage");

        for(int i = 0; i < names.size(); i++){
            coolings.add(new Cooling(names.get(i), types.get(i), prices.get(i), wattages.get(i)));
        }
    }
    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Cooling, String>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Cooling, String>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Cooling, Integer>("price"));
        wattageColumn.setCellValueFactory(new PropertyValueFactory<Cooling, Integer>("wattage"));

        ObservableList<Cooling> coolingList = tableView.getItems();
        coolingList.addAll(coolings);
        tableView.setItems(coolingList);
    }

    public void addComponent(){
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Cooling cooling = tableView.getSelectionModel().getSelectedItem();
            component.setName(cooling.getName());
            componenten.add(component);
        }
    }
}
