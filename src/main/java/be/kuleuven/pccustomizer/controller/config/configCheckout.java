package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.ProjectMain;
import be.kuleuven.pccustomizer.controller.Objects.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class configCheckout extends _beheerConfig{
    private int totalPrice = 0;
    private List<Klant> klanten = new ArrayList<Klant>();

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
        //ReadFromDB();
        calculatePriceFromDB();
        System.out.println(totalPrice);
        /*
        btnAdd.setOnAction(e -> {
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                //addComponent();
                var stage = (Stage) btnClose.getScene().getWindow();
                stage.close();
            }});

         */
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }

    private void showBeheerScherm(String id) {
        var resourceName = "config" + id + ".fxml";
        try {
            var stageCur = (Stage) btnClose.getScene().getWindow();
            stageCur.close();
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource(resourceName));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Admin " + id);
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm " + resourceName + " niet vinden", e);
        }
    }

    private void ReadFromDB(){
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

    private void calculatePriceFromDB(){
        totalPrice = 0;
        totalPrice = totalPrice + readAndCalculateDBint("CPU", "Price", componenten.get(0).getName());
        totalPrice = totalPrice + readAndCalculateDBint("GPU", "Price", componenten.get(1).getName());
        totalPrice = totalPrice + readAndCalculateDBint("RAM", "Price", componenten.get(2).getName());
        totalPrice = totalPrice + readAndCalculateDBint("Storage", "Price", componenten.get(3).getName());
        totalPrice = totalPrice + readAndCalculateDBint("Motherboard", "Price", componenten.get(4).getName());
        totalPrice = totalPrice + readAndCalculateDBint("Cooling", "Price", componenten.get(5).getName());
        totalPrice = totalPrice + readAndCalculateDBint("PSU", "Price", componenten.get(6).getName());
        totalPrice = totalPrice + readAndCalculateDBint("Case", "Price", componenten.get(7).getName());
        totalPrice = totalPrice + readAndCalculateDBint("Extra", "Price", componenten.get(8).getName());
    }

    private void initTableComponenten() {
        componentColumn.setCellValueFactory(new PropertyValueFactory<Component, String>("name"));
        ObservableList<Component> viewComponenten = FXCollections.observableArrayList();
        viewComponenten.addAll(componenten);
        System.out.println(viewComponenten);
        componentView.setItems(viewComponenten);
    }
}
