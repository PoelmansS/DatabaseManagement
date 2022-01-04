package be.kuleuven.pccustomizer.controller.Beheer;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class _BeheerCommon {
    int selectedRow;

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

    public void deleteCurrentRow(TableView tbl) {
        selectedRow = tbl.getSelectionModel().getSelectedIndex();
        tbl.getItems().remove(selectedRow);
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
