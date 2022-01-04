package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.Cooling;
import javafx.scene.control.*;

public class beheerCooling extends _BeheerCommon {

    private TableView<Cooling> tableView;

    public void initialize() {
      /*  initTable();
        btnAdd.setOnAction(e -> {
            verifyInput();
        });
        btnModify.setOnAction(e -> {
            verifyOneRowSelected(tableView);
            verifyModifyInput();
        });
        btnDelete.setOnAction(e -> {
            verifyOneRowSelected(tableView);
            deleteCurrentRow(tableView);
        });
        //btnLoad.setOnAction(e -> {
            //LoadCurrentRow();
        //});
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });

       */
    }

    public void initTable() {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.getColumns().clear();
    }

    public void addNewRow() {
    }


    public void modifyCurrentRow() {
    }
}
