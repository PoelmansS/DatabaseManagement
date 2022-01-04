package be.kuleuven.pccustomizer.controller.Beheer;

import be.kuleuven.pccustomizer.controller.Objects.Klant;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Beheerklanten extends _BeheerCommon {
    Klant modifiedKlant;

    //table
    @FXML
    private TableView<Klant> tableView;
    //input text fields
    @FXML
    private TextField addID;
    @FXML
    private TextField addLastName;
    @FXML
    private TextField addFirstName;
    @FXML
    private TextField addPostalCode;
    @FXML
    private TextField addStreet;
    @FXML
    private TextField addNR;
    @FXML
    private TextField addPhone;
    @FXML
    private TextField addMail;

    @FXML
    private TableColumn<Klant, Integer> IDColumn;
    @FXML
    private TableColumn<Klant, String> lastNameColumn;
    @FXML
    private TableColumn<Klant, String> firstNameColumn;
    @FXML
    private TableColumn<Klant, Integer> postalCodeColumn;
    @FXML
    private TableColumn<Klant, String> streetColumn;
    @FXML
    private TableColumn<Klant, String> NRColumn;
    @FXML
    private TableColumn<Klant, String> phoneColumn;
    @FXML
    private TableColumn<Klant, String> mailColumn;

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
        IDColumn.setCellValueFactory(new PropertyValueFactory<Klant, Integer>("ID"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Klant, String>("lastName"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Klant, String>("firstName"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<Klant, Integer>("postalCode"));
        streetColumn.setCellValueFactory(new PropertyValueFactory<Klant, String>("street"));
        NRColumn.setCellValueFactory(new PropertyValueFactory<Klant, String>("number"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Klant, String>("phone"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<Klant, String>("mail"));

        Klant klant1 = new Klant(1, "Baker","bob", 3600,"lange straat","245","+32 658 486 259" , "BB@gmail.com");
        Klant klant2 = new Klant(2, "Doe","John", 3700,"kortere straat","24","+32 658 457 542" , "JD@gmail.com");
        Klant klant3 = new Klant(3, "Dover","Ben", 2400,"korte straat","2","+32 659 629 411" , "BD@gmail.com");
        ObservableList<Klant> klantList = tableView.getItems();
        klantList.add(klant1);
        klantList.add(klant3);
        klantList.add(klant3);
        tableView.setItems(klantList);
    }
    public void addNewRow() {
        Klant cpu = new Klant(Integer.parseInt(addID.getText()),addLastName.getText(),addFirstName.getText(),Integer.parseInt(addPostalCode.getText()),
                addStreet.getText(),addNR.getText(),addPhone.getText(),addMail.getText());
        ObservableList<Klant> klantList = tableView.getItems();
        klantList.add(cpu);
        tableView.setItems(klantList);
    }

    public void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Klant klant = tableView.getSelectionModel().getSelectedItem();

            addID.setText(String.valueOf(klant.getID()));
            addLastName.setText(klant.getLastName());
            addFirstName.setText(klant.getFirstName());
            addPostalCode.setText(String.valueOf(klant.getPostalCode()));
            addStreet.setText(klant.getStreet());
            addNR.setText(klant.getNumber());
            addPhone.setText(klant.getPhone());
            addMail.setText(klant.getMail());

            modifiedKlant = new Klant(klant.getID(),klant.getLastName(),klant.getFirstName(),klant.getPostalCode(),
                    klant.getStreet(),klant.getNumber(),klant.getPhone(),klant.getMail());
        }
    }
    public void modifyCurrentRow(){
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedKlant.setID(Integer.parseInt(addID.getText()));
        modifiedKlant.setLastName(addLastName.getText());
        modifiedKlant.setFirstName(addFirstName.getText());
        modifiedKlant.setPostalCode(Integer.parseInt(addPostalCode.getText()));
        modifiedKlant.setStreet(addStreet.getText());
        modifiedKlant.setNumber(addNR.getText());
        modifiedKlant.setPhone(addPhone.getText());
        modifiedKlant.setMail(addMail.getText());

        ObservableList<Klant> klantList = tableView.getItems();
        klantList.set(selectedRow,modifiedKlant);
        tableView.setItems(klantList);
    }


}
