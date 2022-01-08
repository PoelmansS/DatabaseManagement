package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.controller.Objects.Component;
import be.kuleuven.pccustomizer.controller.Objects.Extra;
import be.kuleuven.pccustomizer.ProjectMain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class configExtra extends _beheerConfig{
    Component component = new Component();
    List<Extra> extras = new ArrayList<Extra>();
    @FXML
    private Button btnSkip;
    @FXML
    private TableView<Extra> tableView;


    @FXML
    private TableColumn<Extra, String> nameColumn;
    @FXML
    private TableColumn<Extra, String> typeColumn;
    @FXML
    private TableColumn<Extra, Integer> priceColumn;

    public void initialize() {
        Component component = new Component();
        initTableComponenten();
        ReadFromDB();
        initTable();
        btnAdd.setOnAction(e -> {
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                addComponent();
                showBeheerScherm("Checkout");
            }});
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
        btnSkip.setOnAction(e -> {
            skipComponent();
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }



    private void ReadFromDB(){
        List<String> names = readDBstring("Extra","Name");
        List<String> types =  readDBstring("Extra","Type");
        List<Integer> prices =  readDBint("Extra","Price");

        for(int i = 0; i < names.size(); i++){
            extras.add(new Extra(names.get(i), types.get(i), prices.get(i)));
        }
    }

    private void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Extra, String>("Name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Extra, String>("Type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Extra, Integer>("Price"));

        ObservableList<Extra> extraList = tableView.getItems();
        extraList.addAll(extras);
        tableView.setItems(extraList);
    }

    private void addComponent(){
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Extra extra = tableView.getSelectionModel().getSelectedItem();
            component.setName(extra.getName());
            componenten.add(component);
        }
    }

    private void skipComponent(){
        Extra extra = tableView.getSelectionModel().getSelectedItem();
        component.setName("");
        componenten.add(component);
    }

    private void initTableComponenten() {
        componentColumn.setCellValueFactory(new PropertyValueFactory<Component, String>("name"));
        ObservableList<Component> viewComponenten = FXCollections.observableArrayList();
        viewComponenten.addAll(componenten);
        System.out.println(viewComponenten);
        componentView.setItems(viewComponenten);
    }
}
