package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.controller.Objects.Extra;
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

import java.util.ArrayList;
import java.util.List;

public class configExtra extends _beheerConfig{
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
        ReadFromDB();
        initTable();
        btnAdd.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
        btnSkip.setOnAction(e -> {
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
        List<String> names = readDBstring("Extra","Name");
        List<String> types =  readDBstring("Extra","Type");
        List<Integer> prices =  readDBint("Extra","Price");

        for(int i = 0; i < names.size(); i++){
            extras.add(new Extra(names.get(i), types.get(i), prices.get(i)));
        }
    }

    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Extra, String>("Name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Extra, String>("Type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Extra, Integer>("Price"));

        ObservableList<Extra> extraList = tableView.getItems();
        extraList.addAll(extras);
        tableView.setItems(extraList);
    }
}
