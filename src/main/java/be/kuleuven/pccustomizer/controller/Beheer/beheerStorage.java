package be.kuleuven.pccustomizer.controller.Beheer;

import be.kuleuven.pccustomizer.controller.Objects.Storage;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static java.lang.Integer.valueOf;

public class beheerStorage {
    //buttons
    Storage modifiedCase;
    int selectedRow;

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnLoad;
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
            addNewRow();
        });
        btnModify.setOnAction(e -> {
            verifyOneRowSelected();
            modifyCurrentRow();
        });
        btnDelete.setOnAction(e -> {
            verifyOneRowSelected();
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

    private void initTable() {
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


    private void addNewRow() {
        Storage cases = new Storage(addName.getText(), addType.getText(), Integer.parseInt(addPrice.getText()),
                Integer.parseInt(addSize.getText()), Integer.parseInt(addReadSpeed.getText()), Integer.parseInt(addWriteSpeed.getText()));
        ObservableList<Storage> storageList = tableView.getItems();
        storageList.add(cases);
        tableView.setItems(storageList);


    }

    private void deleteCurrentRow() {
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        tableView.getItems().remove(selectedRow);
    }

    private void LoadCurrentRow() {
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

    private void modifyCurrentRow() {
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

    public void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void verifyOneRowSelected() {
        if (tableView.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Eerst een record selecteren he.");
        }
    }

    private void verifyInput() {

    }
}
