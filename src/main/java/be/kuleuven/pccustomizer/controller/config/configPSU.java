package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.controller.Objects.Component;
import be.kuleuven.pccustomizer.controller.Objects.PSU;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class configPSU extends _ConfigCommon {
    List<PSU> psus = new ArrayList<PSU>();
    //table
    @FXML
    private TableView<PSU> tableView;

    @FXML
    private TableColumn<PSU, String> nameColumn;
    @FXML
    private TableColumn<PSU, Integer> wattageColumn;
    @FXML
    private TableColumn<PSU, Integer> priceColumn;


    public void initialize() {
        init(tableView, "Case");
    }


    public void ReadFromDB(){
        List<String> names = readDBstring("Power_supply","Name");
        List<Integer> prices =  readDBint("Power_supply","Price");
        List<Integer> wattages =  readDBint("Power_supply","Wattage");
        for(int j = 0; j < wattages.size(); j++){
            if(wattages.get(j) < totalW){
                names.remove(j);
                prices.remove(j);
                wattages.remove(j);
            }
        }
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

    public void addComponent(){
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            PSU psu = tableView.getSelectionModel().getSelectedItem();
            component.setName(psu.getName());
            componenten.add(component);
        }
    }

}
