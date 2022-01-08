package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.controller.SQLiteClient;
import be.kuleuven.pccustomizer.controller.Objects.Component;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.ArrayList;
import java.util.List;

public class _beheerConfig {
    public static List<Component> componenten = new ArrayList();

    int selectedRow;
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


    public List<String> readDBstring(String clas, String columnName){
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT " + columnName + " FROM " + clas)
                        .mapTo(String.class)
                        .list());
    }

    public List<Integer> readDBint(String clas, String columnName){
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT " + columnName + " FROM " + clas)
                        .mapTo(Integer.class)
                        .list());
    }

    public Integer readAndCalculateDBint(String clas, String columnName, String item){
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT " + columnName + " FROM " + clas + " WHERE Name = :Name")
                        .bind("Name", item)
                        .mapTo(Integer.class)
                        .one());
    }

    public List<Boolean> readDBbool(String clas, String columnName){
        List<Boolean> list = jdbi.withHandle(handle ->
                handle.createQuery("SELECT " + columnName + " FROM " + clas)
                        .mapTo(Boolean.class)
                        .list());
        return list;
    }

    public void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void verifyOneRowSelected(TableView tbl) {
        if(tbl.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Eerst een record selecteren hé.");
        }
    }

    void addNewRow(){}
    void modifyCurrentRow(){}

    public void verifyInput() {
        try { addNewRow(); }
        catch (Exception e){ showAlert("Unseported Entry","You tried entering an incorrect value"); }
    }

    public void verifyModifyInput() {
        try { modifyCurrentRow(); }
        catch (Exception e){ showAlert("Unseported Entry","You tried entering an incorrect value"); }
    }
}
