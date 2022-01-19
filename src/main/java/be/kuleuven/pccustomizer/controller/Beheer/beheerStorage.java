package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.RAM;
import be.kuleuven.pccustomizer.controller.Objects.Storage;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class beheerStorage extends _BeheerCommon {
    private Storage modifiedStorage;
    private final List<Storage> storages = new ArrayList<Storage>();

    @FXML
    private TableView<Storage> tableView;
    @FXML
    private TextField addName;
    @FXML
    private TextField addType;
    @FXML
    private TextField addPrice;
    @FXML
    private TextField addSize;
    @FXML
    private TextField addReadSpeed;
    @FXML
    private TextField addWriteSpeed;
    @FXML
    private TextField addAantal;
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
    @FXML
    private TableColumn<Storage, Integer> aantalColumn;

    public void initialize() {
        init(tableView);
    }

    public void ReadFromDB(){
        List<String> names = readDBstring("Storage","Name");
        List<String> types =  readDBstring("Storage","Type");
        List<Integer> prices =  readDBint("Storage","Price");
        List<Integer> sizes =  readDBint("Storage","Size");
        List<Integer> readSpeeds =  readDBint("Storage","Read_speed");
        List<Integer> writeSpeeds =  readDBint("Storage","Write_speed");
        List<Integer> aantallen =  readDBint("Storage","Aantal");

        for(int i = 0; i < names.size(); i++){
            storages.add(new Storage(names.get(i), types.get(i), prices.get(i), sizes.get(i), readSpeeds.get(i), writeSpeeds.get(i), aantallen.get(i)));
        }
    }

    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Storage, String>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Storage, String>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Storage, Integer>("price"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<Storage, Integer>("size"));
        readSpeedColumn.setCellValueFactory(new PropertyValueFactory<Storage, Integer>("readSpeed"));
        writeSpeedColumn.setCellValueFactory(new PropertyValueFactory<Storage, Integer>("writeSpeed"));
        aantalColumn.setCellValueFactory(new PropertyValueFactory<Storage, Integer>("aantal"));

        ObservableList<Storage> storageList = tableView.getItems();
        storageList.addAll(storages);
        tableView.setItems(storageList);
    }

    public void deleteCurrentRow() {
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Storage storage = tableView.getSelectionModel().getSelectedItem();
            jdbi.useHandle(handle -> {
                handle.execute("DELETE FROM Storage WHERE Name = ?", storage.getName());
            });
            tableView.getItems().remove(selectedRow);
        }
    }

    public void addNewRow() {
        if(!doubles("Storage", "name", addName.getText())) {
            Storage storage = new Storage(addName.getText(), addType.getText(), Integer.parseInt(addPrice.getText()),
                    Integer.parseInt(addSize.getText()), Integer.parseInt(addReadSpeed.getText()), Integer.parseInt(addWriteSpeed.getText()),
                    Integer.parseInt(addAantal.getText()));
            jdbi.useHandle(handle -> {
                handle.execute("insert into Storage (Name, Type, Price, Size, Read_speed, Write_speed) values (?, ?, ?, ?, ?, ?)",
                        storage.getName(), storage.getType(), storage.getPrice(), storage.getSize(), storage.getReadSpeed(), storage.getWriteSpeed());
            });

            ObservableList<Storage> storageList = tableView.getItems();
            storageList.add(storage);
            tableView.setItems(storageList);
        }
        else{
            Storage storage = new Storage(addName.getText(), addType.getText(), Integer.parseInt(addPrice.getText()),
                    Integer.parseInt(addSize.getText()), Integer.parseInt(addReadSpeed.getText()), Integer.parseInt(addWriteSpeed.getText()),
                    Integer.parseInt(addAantal.getText()));
            jdbi.useHandle(handle -> {
                handle.execute("UPDATE Storage SET Name = ?, Type = ?, Price = ?, Size = ?, Read_speed = ?, Write_speed = ?",
                        storage.getAantal(), storage.getName(), storage.getType(), storage.getPrice(), storage.getSize(), storage.getReadSpeed(), storage.getWriteSpeed());
            });
        }
    }

    public void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Storage storage = tableView.getSelectionModel().getSelectedItem();
            addName.setText(storage.getName());
            addType.setText(storage.getType());
            addPrice.setText(String.valueOf(storage.getPrice()));
            addSize.setText(String.valueOf(storage.getSize()));
            addReadSpeed.setText(String.valueOf(storage.getReadSpeed()));
            addWriteSpeed.setText(String.valueOf(storage.getWriteSpeed()));
            addAantal.setText(String.valueOf(storage.getAantal()));
            modifiedStorage = new Storage(storage.getName(), storage.getType(), storage.getPrice(), storage.getSize(),
                    storage.getReadSpeed(), storage.getWriteSpeed(), storage.getAantal());
        }
    }

    public void modifyCurrentRow() {
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedStorage.setName(addName.getText());
        modifiedStorage.setType(addType.getText());
        modifiedStorage.setPrice(Integer.parseInt(addPrice.getText()));
        modifiedStorage.setSize(Integer.parseInt(addSize.getText()));
        modifiedStorage.setReadSpeed(Integer.parseInt(addReadSpeed.getText()));
        modifiedStorage.setWriteSpeed(Integer.parseInt(addWriteSpeed.getText()));
        modifiedStorage.setAantal(Integer.parseInt(addAantal.getText()));

        jdbi.useHandle(handle -> {
            handle.execute("UPDATE Storage SET Name = ? ,Type = ?, Price = ? , Size = ? , Read_speed = ? , Write_speed = ?, Aantal = ? WHERE Name = ?",
                    modifiedStorage.getName(), modifiedStorage.getType(), modifiedStorage.getPrice(),
                    modifiedStorage.getSize(), modifiedStorage.getWriteSpeed(), modifiedStorage.getAantal(), modifiedStorage.getReadSpeed());
        });

        ObservableList<Storage> storageList = tableView.getItems();
        storageList.set(selectedRow, modifiedStorage);
        tableView.setItems(storageList);
    }
}
