package be.kuleuven.pccustomizer.controller.Beheer;

import be.kuleuven.pccustomizer.controller.Objects.Case;
import be.kuleuven.pccustomizer.controller.Objects.Storage;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class beheerStorage extends _BeheerCommon {
    Storage modifiedStorage;

    //table
    @FXML
    private TableView<Storage> tableView;
    //input text fields
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

    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Storage, String>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Storage, String>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Storage, Integer>("price"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<Storage, Integer>("size"));
        readSpeedColumn.setCellValueFactory(new PropertyValueFactory<Storage, Integer>("readSpeed"));
        writeSpeedColumn.setCellValueFactory(new PropertyValueFactory<Storage, Integer>("writeSpeed"));

        Storage storage1 = new Storage("storage1", "HDD", 50, 5,300,100);
        Storage storage2 = new Storage("storage2", "SSD", 100, 1 , 800, 300);
        Storage storage3 = new Storage("storage3", "SSD", 350, 3, 800, 300);
        ObservableList<Storage> storageList = tableView.getItems();
        storageList.add(storage1);
        storageList.add(storage2);
        storageList.add(storage3);
        tableView.setItems(storageList);

    }

    public void deleteCurrentRow() {
        ObservableList<Storage> storageList = tableView.getItems();
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        jdbi.useHandle(handle -> {
            handle.execute("DELETE FROM Storage WHERE Name = ?", storageList.get(0).getName());
        });
        tableView.getItems().remove(selectedRow);
    }

    public void addNewRow() {
        Storage storage = new Storage(addName.getText(), addType.getText(), Integer.parseInt(addPrice.getText()),
                Integer.parseInt(addSize.getText()), Integer.parseInt(addReadSpeed.getText()), Integer.parseInt(addWriteSpeed.getText()));
        jdbi.useHandle(handle -> { handle.execute("insert into Storage (Name, Type, Price, Size, Read_speed, Write_speed) values (?, ?, ?, ?, ?, ?)",
                storage.getName(),storage.getType(),storage.getPrice(),storage.getSize(), storage.getReadSpeed(), storage.getWriteSpeed()); });

        ObservableList<Storage> storageList = tableView.getItems();
        storageList.add(storage);
        tableView.setItems(storageList);


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
            modifiedStorage = new Storage(storage.getName(), storage.getType(), storage.getPrice(), storage.getSize(),
                    storage.getReadSpeed(), storage.getWriteSpeed());
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
        jdbi.useHandle(handle -> {
            handle.execute("UPDATE Storage SET Name = ? ,Type = ?, Price = ? , Size = ? , Read_speed = ? , Write_speed = ? WHERE Name = ?",
                    modifiedStorage.getName(), modifiedStorage.getType(), modifiedStorage.getPrice(),
                    modifiedStorage.getSize(), modifiedStorage.getWriteSpeed() , modifiedStorage.getReadSpeed());
        });

        ObservableList<Storage> storageList = tableView.getItems();
        storageList.set(selectedRow, modifiedStorage);
        tableView.setItems(storageList);
    }
}
