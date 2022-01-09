package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.controller.Objects.Component;
import be.kuleuven.pccustomizer.controller.Objects.Storage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class configStorage extends _ConfigCommon {
    List<Storage> storages = new ArrayList<Storage>();
    //table
    @FXML
    private TableView<Storage> tableView;

    @FXML
    private TableColumn<Storage, String> nameColumn;
    @FXML
    private TableColumn<Storage, String> typeColumn;
    @FXML
    private TableColumn<Storage, Integer> priceColumn;
    @FXML
    private TableColumn<Storage, Integer> sizeColumn;
    @FXML
    private TableColumn<Storage, Integer> readSpeedColumn;
    @FXML
    private TableColumn<Storage, Integer> writeSpeedColumn;

    public void initialize() {
        init(tableView, "Motherboard");
    }

    public void ReadFromDB(){
        List<String> names = readDBstring("Storage","Name");
        List<String> types =  readDBstring("Storage","Type");
        List<Integer> prices =  readDBint("Storage","Price");
        List<Integer> sizes =  readDBint("Storage","Size");
        List<Integer> readSpeeds =  readDBint("Storage","Read_speed");
        List<Integer> writeSpeeds =  readDBint("Storage","Write_speed");

        for(int i = 0; i < names.size(); i++){
            storages.add(new Storage(names.get(i), types.get(i), prices.get(i), sizes.get(i), readSpeeds.get(i), writeSpeeds.get(i)));
        }
    }
    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Storage, String>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Storage, String>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Storage, Integer>("price"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<Storage, Integer>("size"));
        readSpeedColumn.setCellValueFactory(new PropertyValueFactory<Storage, Integer>("readSpeed"));
        writeSpeedColumn.setCellValueFactory(new PropertyValueFactory<Storage, Integer>("writeSpeed"));

        ObservableList<Storage> storageList = tableView.getItems();
        storageList.addAll(storages);
        tableView.setItems(storageList);
    }


}
