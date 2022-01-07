package be.kuleuven.pccustomizer.controller.Beheer;

import be.kuleuven.pccustomizer.controller.Beheer._BeheerCommon;
import be.kuleuven.pccustomizer.controller.Beheer._BeheerCommon.*;
import be.kuleuven.pccustomizer.controller.Objects.Bestelling;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class beheerBestelling extends _BeheerCommon {
    Bestelling modifiedBestelling;
    List<Bestelling> bestellingen = new ArrayList<Bestelling>();
    //table
    @FXML
    private TableView<Bestelling> tableView;
    //input text fields
    @FXML
    private TextField addID;
    @FXML
    private TextField addKlant;
    @FXML
    private TextField addComputer;
    @FXML
    private TextField addPrice;

    @FXML
    private TableColumn<Bestelling, Integer> IDColumn;
    @FXML
    private TableColumn<Bestelling, Integer> klantColumn;
    @FXML
    private TableColumn<Bestelling, String> computerColumn;
    @FXML
    private TableColumn<Bestelling, Integer> priceColumn;

    public void initialize() {
        ReadFromDB();
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

    public void ReadFromDB(){
        List<Integer> IDs = readDBint("Bestelling","ID");
        List<Integer> klanten =  readDBint("Bestelling","Klant");
        List<String> computers =  readDBstring("Bestelling","Computer");
        List<Integer> prices =  readDBint("Bestelling","Price");

        for(int i = 0; i < IDs.size(); i++){
            bestellingen.add(new Bestelling(IDs.get(i), klanten.get(i), computers.get(i), prices.get(i)));
        }
    }

    public void initTable() {
        IDColumn.setCellValueFactory(new PropertyValueFactory<Bestelling, Integer>("ID"));
        klantColumn.setCellValueFactory(new PropertyValueFactory<Bestelling, Integer>("Klant"));
        computerColumn.setCellValueFactory(new PropertyValueFactory<Bestelling, String>("Computer"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Bestelling, Integer>("Price"));

        ObservableList<Bestelling> bestellingList = tableView.getItems();
        bestellingList.addAll(bestellingen);
        tableView.setItems(bestellingList);

    }

    public void deleteCurrentRow() {
        ObservableList<Bestelling> bestellingList = tableView.getItems();
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        jdbi.useHandle(handle -> {
            handle.execute("DELETE FROM PcCase WHERE Name = ?", bestellingList.get(0).getID());
        });
        tableView.getItems().remove(selectedRow);
    }

    public void addNewRow() {
        Bestelling bestelling = new Bestelling(Integer.parseInt(addID.getText()),Integer.parseInt(addKlant.getText())
                , addComputer.getText(),Integer.parseInt(addPrice.getText()));
        jdbi.useHandle(handle -> { handle.execute("insert into Bestelling (ID,Klant,Computer,Price) values (?, ?, ?, ?)",
                bestelling.getID(),bestelling.getKlant(),bestelling.getComputer(),bestelling.getPrice()); });

        ObservableList<Bestelling> bestellingList = tableView.getItems();
        bestellingList.add(bestelling);
        tableView.setItems(bestellingList);
    }

    public void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Bestelling bestelling = tableView.getSelectionModel().getSelectedItem();
            addID.setText(String.valueOf(bestelling.getID()));
            addKlant.setText(String.valueOf(bestelling.getKlant()));
            addComputer.setText(bestelling.getComputer());
            addPrice.setText(String.valueOf(bestelling.getPrice()));
            modifiedBestelling = new Bestelling(bestelling.getID(), bestelling.getKlant(),bestelling.getComputer(),bestelling.getPrice());
        }
    }
    public void modifyCurrentRow(){
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedBestelling.setID(Integer.parseInt(addID.getText()));
        modifiedBestelling.setKlant(Integer.parseInt(addKlant.getText()));
        modifiedBestelling.setComputer(addComputer.getText());
        modifiedBestelling.setPrice(Integer.parseInt(addPrice.getText()));

        jdbi.useHandle(handle -> {
            handle.execute("UPDATE Bestelling SET ID = ? ,Klant = ?, Computer = ? , Price = ? WHERE ID = ?",
                    modifiedBestelling.getID(), modifiedBestelling.getKlant(), modifiedBestelling.getComputer(), modifiedBestelling.getPrice(), modifiedBestelling.getID());
        });
        ObservableList<Bestelling> bestellingList = tableView.getItems();
        bestellingList.set(selectedRow,modifiedBestelling);
        tableView.setItems(bestellingList);
    }


}
