package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.Klant;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Beheerklanten extends _BeheerCommon {
    private Klant modifiedKlant;
    private final List<Klant> klanten = new ArrayList<Klant>();

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
        init(tableView);
    }

    public void ReadFromDB(){
        List<Integer> ids = readDBint("Klant","ID");
        List<String> lastNames =  readDBstring("Klant","LastName");
        List<String> firstNames =  readDBstring("Klant","FirstName");
        List<Integer> postalCodes =  readDBint("Klant","PostalCode");
        List<String> streets =  readDBstring("Klant","Street");
        List<String> numbers =  readDBstring("Klant","NR");
        List<String> phones =  readDBstring("Klant","Phone");
        List<String> mails =  readDBstring("Klant","Mail");

        for(int i = 0; i < ids.size(); i++){
            klanten.add(new Klant(ids.get(i), lastNames.get(i), firstNames.get(i), postalCodes.get(i), streets.get(i),
                    numbers.get(i), phones.get(i), mails.get(i)));
        }
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


        ObservableList<Klant> klantList = tableView.getItems();
        klantList.addAll(klanten);
        tableView.setItems(klantList);
    }

    public void deleteCurrentRow() {
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Klant klant = tableView.getSelectionModel().getSelectedItem();
            jdbi.useHandle(handle -> {
                handle.execute("DELETE FROM Klant WHERE ID = ?", klant.getID());
            });
            tableView.getItems().remove(selectedRow);
        }
    }

    public Integer countOfID(){
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT MAX(ID) FROM Klant")
                        .mapTo(Integer.class)
                        .one());
    }

    public void addNewRow() {
        int id = countOfID() +1;
        Klant klant = new Klant(id, addLastName.getText(),addFirstName.getText(),Integer.parseInt(addPostalCode.getText()),
                addStreet.getText(),addNR.getText(),addPhone.getText(),addMail.getText());
        jdbi.useHandle(handle -> { handle.execute("insert into Klant (ID, LastName,FirstName,PostalCode,Street,NR ,Phone,Mail ) values (?, ?, ?, ?, ?, ?,?,?)",
                id, klant.getLastName(),klant.getFirstName(),klant.getPostalCode(),
                klant.getStreet(),klant.getNumber(),klant.getPhone(),klant.getMail()); });
        ObservableList<Klant> klantList = tableView.getItems();
        klantList.add(klant);
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

        jdbi.useHandle(handle -> {
            handle.execute("UPDATE Klant SET ID = ?,LastName = ?,FirstName = ?,PostalCode = ?,Street = ?,NR = ?,Phone = ?,Mail =? WHERE ID = ?",
                    modifiedKlant.getID(),modifiedKlant.getLastName(),modifiedKlant.getFirstName(),
                    modifiedKlant.getPostalCode(),modifiedKlant.getStreet(),modifiedKlant.getNumber(),
                    modifiedKlant.getPhone(),modifiedKlant.getMail(),modifiedKlant.getID());
        });

        ObservableList<Klant> klantList = tableView.getItems();
        klantList.set(selectedRow,modifiedKlant);
        tableView.setItems(klantList);
    }
}
