package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.PSU;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static java.lang.Integer.valueOf;

public class beheerPSU {
    PSU modifiedPSU;
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
    private TableView<PSU> tableView;
    //input text fields
    @FXML
    private TextField addName;
    @FXML
    private TextField addPrice;
    @FXML
    private TextField addWattage;


    @FXML
    private TableColumn<PSU, String> nameColumn;
    @FXML
    private TableColumn<PSU, Integer> wattageColumn;
    @FXML
    private TableColumn<PSU, Integer> priceColumn;


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
        nameColumn.setCellValueFactory(new PropertyValueFactory<PSU, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<PSU, Integer>("price"));
        wattageColumn.setCellValueFactory(new PropertyValueFactory<PSU, Integer>("wattage"));

        PSU PSU1 = new PSU("PSU1",50,250);
        PSU PSU2 = new PSU("PSU2",60,300);
        PSU PSU3 = new PSU("PSU3",120,500);
        ObservableList<PSU> PSUList = tableView.getItems();
        PSUList.add(PSU1);
        PSUList.add(PSU2);
        PSUList.add(PSU3);
        tableView.setItems(PSUList);

    }


    private void addNewRow() {
        PSU psu = new PSU(addName.getText(), Integer.parseInt(addPrice.getText()), Integer.parseInt(addWattage.getText()));
        ObservableList<PSU> PSUList = tableView.getItems();
        PSUList.add(psu);
        tableView.setItems(PSUList);


    }

    private void deleteCurrentRow() {
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        tableView.getItems().remove(selectedRow);
    }

    private void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            PSU psu = tableView.getSelectionModel().getSelectedItem();
            addName.setText(psu.getName());
            addPrice.setText(String.valueOf(psu.getPrice()));
            addPrice.setText(String.valueOf(psu.getWattage()));

            modifiedPSU = new PSU(psu.getName(), psu.getPrice(),psu.getWattage());
        }
    }
    private void modifyCurrentRow(){
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedPSU.setName(addName.getText());
        modifiedPSU.setPrice(Integer.parseInt(addPrice.getText()));
        modifiedPSU.setPrice(Integer.parseInt(addWattage.getText()));

        ObservableList<PSU> PSUList = tableView.getItems();
        PSUList.set(selectedRow,modifiedPSU);
        tableView.setItems(PSUList);
    }

    public void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void verifyOneRowSelected() {
        if(tableView.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Eerst een record selecteren he.");
        }
    }
    private void verifyInput() {
    }
}
