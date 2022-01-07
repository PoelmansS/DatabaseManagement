package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.controller.Objects.Case;
import be.kuleuven.pccustomizer.ProjectMain;
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
        ReadFromDB();
        initTable();
        btnAdd.setOnAction(e -> showBeheerScherm("Extra"));
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
}
