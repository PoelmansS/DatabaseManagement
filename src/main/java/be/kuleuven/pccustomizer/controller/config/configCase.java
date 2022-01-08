package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.controller.Objects.Case;
import be.kuleuven.pccustomizer.ProjectMain;
import be.kuleuven.pccustomizer.controller.Objects.Component;
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
import org.jdbi.v3.core.Jdbi;

import java.util.ArrayList;
import java.util.List;

public class configCase extends _beheerConfig{
    Component component = new Component();
    List<Case> cases = new ArrayList<Case>();

    @FXML
    private TableView<Case> tableView;

    @FXML
    private TableColumn<Case, String> nameColumn;
    @FXML
    private TableColumn<Case, String> typeColumn;
    @FXML
    private TableColumn<Case, Integer> priceColumn;
    @FXML
    private TableColumn<Case, String> sizeColumn;

    public void initialize() {
        Component component = new Component();
        initTableComponenten();
        ReadFromDB();
        initTable();
        btnAdd.setOnAction(e -> {
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                addComponent();
                showBeheerScherm("Extra");
            }});
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }




    public void ReadFromDB(){
        List<String> names = readDBstring("PcCase","Name");
        List<String> types =  readDBstring("PcCase","Type");
        List<Integer> prices =  readDBint("PcCase","Price");
        List<String> sizes =  readDBstring("PcCase","Size");

        for(int i = 0; i < names.size(); i++){
            cases.add(new Case(names.get(i), types.get(i), prices.get(i), sizes.get(i)));
        }
    }

    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Case, String>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Case, String>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Case, Integer>("price"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<Case, String>("size"));

        ObservableList<Case> caseList = tableView.getItems();
        caseList.addAll(cases);
        tableView.setItems(caseList);

    }

    private void addComponent(){
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Case cases = tableView.getSelectionModel().getSelectedItem();
            component.setName(cases.getName());
            componenten.add(component);
        }
    }

    public void initTableComponenten() {
        componentColumn.setCellValueFactory(new PropertyValueFactory<Component, String>("name"));
        ObservableList<Component> viewComponenten = FXCollections.observableArrayList();
        viewComponenten.addAll(componenten);
        System.out.println(viewComponenten);
        componentView.setItems(viewComponenten);
    }
}
