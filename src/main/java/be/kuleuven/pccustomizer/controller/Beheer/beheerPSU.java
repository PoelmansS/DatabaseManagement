package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.PSU;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class beheerPSU extends _BeheerCommon {
    PSU modifiedPSU;

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


    public void addNewRow() {
        PSU psu = new PSU(addName.getText(), Integer.parseInt(addPrice.getText()), Integer.parseInt(addWattage.getText()));
        ObservableList<PSU> PSUList = tableView.getItems();
        PSUList.add(psu);
        tableView.setItems(PSUList);
    }

    public void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            PSU psu = tableView.getSelectionModel().getSelectedItem();
            addName.setText(psu.getName());
            addPrice.setText(String.valueOf(psu.getPrice()));
            addWattage.setText(String.valueOf(psu.getWattage()));

            modifiedPSU = new PSU(psu.getName(), psu.getPrice(),psu.getWattage());
        }
    }
    public void modifyCurrentRow(){
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedPSU.setName(addName.getText());
        modifiedPSU.setPrice(Integer.parseInt(addPrice.getText()));
        modifiedPSU.setWattage(Integer.parseInt(addWattage.getText()));

        ObservableList<PSU> PSUList = tableView.getItems();
        PSUList.set(selectedRow,modifiedPSU);
        tableView.setItems(PSUList);
    }

}
