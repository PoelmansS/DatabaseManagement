package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.controller.Objects.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class configCheckout extends _ConfigCommon {
    private String name;
    private int totalPrice = 0;
    private List<Klant> klanten = new ArrayList<Klant>();

    @FXML
    public Button btnAdd;
    @FXML
    public Button btnAddKlant;
    @FXML
    public Button btnClose;
    @FXML
    public Button btnLoad;
    @FXML
    private TableView<Klant> tableView;
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
        initTableComponenten();
        ReadFromDB();
        initTable();
        calculatePriceFromDB();
        System.out.println(totalPrice);
        btnLoad.setOnAction(e ->{
            LoadCurrentRow();
        });
        btnAdd.setOnAction(e -> {
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                addComputer();
                addBestelling();
                addNewRow();
                var stage = (Stage) btnClose.getScene().getWindow();
                stage.close();
            }
        });
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
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

    private void calculatePriceFromDB(){
        totalPrice = 0;
        totalPrice = totalPrice + readAndCalculateDBint("CPU", "Price", componenten.get(0).getName());
        if(!componenten.get(1).getName().equals("")) totalPrice = totalPrice + readAndCalculateDBint("GPU", "Price", componenten.get(1).getName());
        totalPrice = totalPrice + readAndCalculateDBint("RAM", "Price", componenten.get(2).getName());
        totalPrice = totalPrice + readAndCalculateDBint("Motherbord", "Price", componenten.get(4).getName());
        totalPrice = totalPrice + readAndCalculateDBint("Cooling", "Price", componenten.get(5).getName());
        totalPrice = totalPrice + readAndCalculateDBint("Power_supply", "Price", componenten.get(6).getName());
        totalPrice = totalPrice + readAndCalculateDBint("PcCase", "Price", componenten.get(7).getName());
        if(!componenten.get(8).getName().equals("")) totalPrice = totalPrice + readAndCalculateDBint("Extra", "Price", componenten.get(8).getName());
        totalPrice = totalPrice + readAndCalculateDBint("Storage", "Price", componenten.get(3).getName());

    }

    private Integer countNumberOfCostums(){
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT Count(name) FROM Computer WHERE Type = :Type")
                        .bind("Type", "Custom")
                        .mapTo(Integer.class)
                        .one());
    }

    private void addComputer(){
        Integer addNumber = countNumberOfCostums() + 1;
        name = "Custom" + addNumber.toString();
        String cpu = componenten.get(0).getName();
        String gpu = componenten.get(1).getName();
        String ram = componenten.get(2).getName();
        String storage = componenten.get(3).getName();
        String motherboard = componenten.get(4).getName();
        String cooling = componenten.get(5).getName();
        String psu = componenten.get(6).getName();
        String cases = componenten.get(7).getName();
        String extra = componenten.get(8).getName();
        jdbi.useHandle(handle -> { handle.execute("insert into Computer (Name, CPU , Cooling, Extra, GPU, " +
                        "Motherbord, PcCase, Power_supply, RAM, Storage, Price, Type) values (?,?,?,?,?,?,?,?,?,?,?,?)",
                name, cpu, cooling, extra, gpu, motherboard, cases, psu, ram, storage, totalPrice, "Custom");});
    }

    private Integer countOfID(){
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT MAX(ID) FROM Bestelling")
                        .mapTo(Integer.class)
                        .one());
    }

    private void addBestelling(){
        Integer id = countOfID() + 1;
        Klant klant = tableView.getSelectionModel().getSelectedItem();
        jdbi.useHandle(handle -> { handle.execute("insert into Bestelling (ID, Klant , Computer, Price) values (?,?,?,?)",
                id, klant.getID(), name, totalPrice);});
    }

    private void initTableComponenten() {
        componentColumn.setCellValueFactory(new PropertyValueFactory<Component, String>("name"));
        ObservableList<Component> viewComponenten = FXCollections.observableArrayList();
        viewComponenten.addAll(componenten);
        componentView.setItems(viewComponenten);
    }

    private void addNewRow() {
        ObservableList<Klant> klantList = tableView.getItems();
        Klant klant = new Klant(Integer.parseInt(addID.getText()),addLastName.getText(),addFirstName.getText(),Integer.parseInt(addPostalCode.getText()),
                addStreet.getText(),addNR.getText(),addPhone.getText(),addMail.getText());

        boolean bestaandeKlant = false;
        List<Klant> list =klantList.stream().collect(Collectors.toList());

        if (!doubles("Klant", "ID", Integer.parseInt(addID.getText()))) {
            jdbi.useHandle(handle -> {
                handle.execute("insert into Klant (ID, LastName,FirstName,PostalCode,Street,NR ,Phone,Mail ) values (?, ?, ?, ?, ?, ?,?,?)",
                        klant.getID(), klant.getLastName(), klant.getFirstName(), klant.getPostalCode(),
                        klant.getStreet(), klant.getNumber(), klant.getPhone(), klant.getMail());
            });
            klantList.add(klant);
        }
        tableView.setItems(klantList);
    }

    private void LoadCurrentRow() {
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
        }
    }
}