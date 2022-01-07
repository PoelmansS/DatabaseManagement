package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.controller.Objects.Cooling;
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

public class configCooling extends _beheerConfig {
    List<Cooling> coolings = new ArrayList<Cooling>();
    @FXML
    private TableView<Cooling> tableView;

    @FXML
    private TableColumn<Cooling, String> nameColumn;
    @FXML
    private TableColumn<Cooling, String> typeColumn;
    @FXML
    private TableColumn<Cooling, Integer> priceColumn;
    @FXML
    private TableColumn<Cooling, Integer> wattageColumn;


    public void initialize() {
        ReadFromDB();
        initTable();
        btnAdd.setOnAction(e -> showBeheerScherm("PSU"));
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
        List<String> names = readDBstring("Cooling","Name");
        List<String> types =  readDBstring("Cooling","Type");
        List<Integer> prices =  readDBint("Cooling","Price");
        List<Integer> wattages =  readDBint("Cooling","Wattage");

        for(int i = 0; i < names.size(); i++){
            coolings.add(new Cooling(names.get(i), types.get(i), prices.get(i), wattages.get(i)));
        }
    }
    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Cooling, String>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Cooling, String>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Cooling, Integer>("price"));
        wattageColumn.setCellValueFactory(new PropertyValueFactory<Cooling, Integer>("wattage"));

        ObservableList<Cooling> coolingList = tableView.getItems();
        coolingList.addAll(coolings);
        tableView.setItems(coolingList);
    }
}
