package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.controller.Objects.Component;
import be.kuleuven.pccustomizer.controller.Objects.RAM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class configRAM extends _ConfigCommon {
    Component component = new Component();
    List<RAM> rams = new ArrayList<RAM>();
    //table
    @FXML
    private TableView<RAM> tableView;

    @FXML
    private TableColumn<RAM, String> nameColumn;
    @FXML
    private TableColumn<RAM, String> typeColumn;
    @FXML
    private TableColumn<RAM, Integer> priceColumn;
    @FXML
    private TableColumn<RAM, Integer> sizeColumn;
    @FXML
    private TableColumn<RAM, Integer> NRofSlotsColumn;
    @FXML
    private TableColumn<RAM, Integer> aantalColumn;


    public void initialize() {
        init(tableView, "Storage");
    }


    public void ReadFromDB(){
        List<String> names = readDBstring("RAM","Name");
        List<String> types =  readDBstring("RAM","Type");
        List<Integer> prices =  readDBint("RAM","Price");
        List<Integer> sizes =  readDBint("RAM","Size");
        List<Integer> NRofSticks =  readDBint("RAM","Number_of_sticks");
        List<Integer> aantallen =  readDBint("RAM","Aantal");

        for(int i = 0; i < names.size(); i++){
            rams.add(new RAM(names.get(i), types.get(i), prices.get(i), sizes.get(i), NRofSticks.get(i), aantallen.get(i)));
        }
    }

    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<RAM, String>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<RAM, String>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<RAM, Integer>("price"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<RAM, Integer>("size"));
        NRofSlotsColumn.setCellValueFactory(new PropertyValueFactory<RAM, Integer>("NRofSticks"));
        aantalColumn.setCellValueFactory(new PropertyValueFactory<RAM, Integer>("aantal"));
        ObservableList<RAM> RAMList = tableView.getItems();
        RAMList.addAll(rams);
        tableView.setItems(RAMList);
    }

    public void addComponent(){
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            RAM ram = tableView.getSelectionModel().getSelectedItem();
            component.setName(ram.getName());
            componenten.add(component);
            benodigdeRamSlots = ram.getNRofSticks();
        }
    }

    private void initTableComponenten() {
        componentColumn.setCellValueFactory(new PropertyValueFactory<Component, String>("name"));
        ObservableList<Component> viewComponenten = FXCollections.observableArrayList();
        viewComponenten.addAll(componenten);
        componentView.setItems(viewComponenten);
    }
}
