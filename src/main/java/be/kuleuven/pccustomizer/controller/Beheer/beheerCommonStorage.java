package be.kuleuven.pccustomizer.controller.Beheer;

import be.kuleuven.pccustomizer.controller.Objects.Storage;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class beheerCommonStorage extends _BeheerCommon {
    Storage modifiedCase;

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
            deleteCurrentRow(tableView);
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


    public void addNewRow() {
        Storage cases = new Storage(addName.getText(), addType.getText(), Integer.parseInt(addPrice.getText()),
                Integer.parseInt(addSize.getText()), Integer.parseInt(addReadSpeed.getText()), Integer.parseInt(addWriteSpeed.getText()));
        ObservableList<Storage> storageList = tableView.getItems();
        storageList.add(cases);
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
            modifiedCase = new Storage(storage.getName(), storage.getType(), storage.getPrice(), storage.getSize(),
                    storage.getReadSpeed(), storage.getWriteSpeed());
        }
    }

    public void modifyCurrentRow() {
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedCase.setName(addName.getText());
        modifiedCase.setType(addType.getText());
        modifiedCase.setPrice(Integer.parseInt(addPrice.getText()));
        modifiedCase.setSize(Integer.parseInt(addSize.getText()));
        modifiedCase.setReadSpeed(Integer.parseInt(addReadSpeed.getText()));
        modifiedCase.setWriteSpeed(Integer.parseInt(addWriteSpeed.getText()));


        ObservableList<Storage> storageList = tableView.getItems();
        storageList.set(selectedRow, modifiedCase);
        tableView.setItems(storageList);
    }
}
