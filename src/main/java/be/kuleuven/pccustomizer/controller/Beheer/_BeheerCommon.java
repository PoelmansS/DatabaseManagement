package be.kuleuven.pccustomizer.controller.Beheer;

import be.kuleuven.pccustomizer.controller.SQLiteClient;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.List;

public class _BeheerCommon {
    int selectedRow;
    SQLiteClient client = new SQLiteClient();
    Jdbi jdbi = client.getJdbi();

    @FXML
    public Button btnDelete;
    @FXML
    public Button btnAdd;
    @FXML
    public Button btnModify;
    @FXML
    public Button btnClose;
    @FXML
    public Button btnLoad;

    public List<String> readDBstring(String Table, String Column){
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT " + Column + " FROM " + Table)
                        .mapTo(String.class)
                        .list());
    }
    public List<Integer> readDBint(String Table, String Column){
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT " + Column + " FROM " + Table)
                        .mapTo(Integer.class)
                        .list());
    }
    public List<Boolean> readDBbool(String Table, String Column){
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT " + Column + " FROM " + Table)
                        .mapTo(Boolean.class)
                        .list());
    }

    public boolean doubles(String Table , String Column, String name){
        List<String> names = readDBstring(Table, Column);
        for (String s : names) {
            if (name.equals(s)) {
                return true;
            }
        }
        return false;
    }

    public void ReadFromDB(){}
    public void initTable() {}
    public void deleteCurrentRow(){}
    public void LoadCurrentRow(){}
    public void init(TableView tableView){
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


    public void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void verifyOneRowSelected(TableView tbl) {
        if(tbl.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Select error", "There was no item selected for your action to be applied to.");
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
