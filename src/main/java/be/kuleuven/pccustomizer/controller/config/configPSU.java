package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.controller.Objects.PSU;
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

public class configPSU extends _beheerConfig{
    List<PSU> psus = new ArrayList<PSU>();
    //table
    @FXML
    private TableView<PSU> tableView;

    @FXML
    private TableColumn<PSU, String> nameColumn;
    @FXML
    private TableColumn<PSU, Integer> wattageColumn;
    @FXML
    private TableColumn<PSU, Integer> priceColumn;


    public void initialize() {
        ReadFromDB();
        initTable();
        btnAdd.setOnAction(e -> showBeheerScherm("Case"));
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
        List<String> names = readDBstring("Power_supply","Name");
        List<Integer> prices =  readDBint("Power_supply","Price");
        List<Integer> wattages =  readDBint("Power_supply","Wattage");
        for(int i = 0; i < names.size(); i++){
            psus.add(new PSU(names.get(i), prices.get(i), wattages.get(i)));
        }
    }

    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<PSU, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<PSU, Integer>("price"));
        wattageColumn.setCellValueFactory(new PropertyValueFactory<PSU, Integer>("wattage"));

        ObservableList<PSU> PSUList = tableView.getItems();
        PSUList.addAll(psus);
        tableView.setItems(PSUList);

    }
}
