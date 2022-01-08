package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.ProjectMain;
import be.kuleuven.pccustomizer.controller.SQLiteClient;
import be.kuleuven.pccustomizer.controller.Objects.Component;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.ArrayList;
import java.util.List;

public class _ConfigCommon {
    public static List<Component> componenten = new ArrayList();

    SQLiteClient client = new SQLiteClient();
    Jdbi jdbi = client.getJdbi();

    @FXML
    public Button btnAdd;
    @FXML
    public Button btnClose;
    @FXML
    public TableView<Component> componentView;
    @FXML
    public TableColumn<Component, String> componentColumn;

    public void initTableComponenten(){}
    public void initTable(){}
    public void ReadFromDB(){}
    public void addComponent(){}
    //function initialise all common methods and buttons
    public void init(TableView tableView, String id){
        initTableComponenten();
        ReadFromDB();
        initTable();
        btnAdd.setOnAction(e -> {
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                addComponent();
                showBeheerScherm(id);
            }});
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }
    //function to querry a column of strings
    public List<String> readDBstring(String clas, String columnName){
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT " + columnName + " FROM " + clas)
                        .mapTo(String.class)
                        .list());
    }
    //function to querry a column of ints
    public List<Integer> readDBint(String clas, String columnName){
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT " + columnName + " FROM " + clas)
                        .mapTo(Integer.class)
                        .list());
    }
    //function to querry a column of bools
    public List<Boolean> readDBbool(String clas, String columnName){
        List<Boolean> list = jdbi.withHandle(handle ->
                handle.createQuery("SELECT " + columnName + " FROM " + clas)
                        .mapTo(Boolean.class)
                        .list());
        return list;
    }

    //function to querry and return the price of components
    public Integer readAndCalculateDBint(String clas, String columnName, String item){
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT " + columnName + " FROM " + clas + " WHERE Name = :Name")
                        .bind("Name", item)
                        .mapTo(Integer.class)
                        .one());
    }

    public void showBeheerScherm(String id) {
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

}
