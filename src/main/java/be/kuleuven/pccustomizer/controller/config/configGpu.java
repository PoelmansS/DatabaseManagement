package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.controller.Objects.GPU;
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

public class configGpu extends _beheerConfig{
    List<GPU> gpus = new ArrayList<GPU>();
    //table
    @FXML
    private TableView<GPU> tableView;
    @FXML
    private Button btnSkip;

    @FXML
    private TableColumn<GPU, String> nameColumn;
    @FXML
    private TableColumn<GPU, Integer> priceColumn;
    @FXML
    private TableColumn<GPU, Integer> VRAMColumn;
    @FXML
    private TableColumn<GPU, Integer> powerUsageColumn;


    public void initialize() {
        System.out.println(componenten);
        ObservableList<String> oComponenten = componentView.getItems();
        oComponenten.addAll(componenten);
        componentView.setItems(oComponenten);
        ReadFromDB();
        initTable();
        btnAdd.setOnAction(e -> showBeheerScherm("Ram"));
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
        btnSkip.setOnAction(e -> showBeheerScherm("Ram"));
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
        List<String> names = readDBstring("GPU","Name");
        List<Integer> prices =  readDBint("GPU","Price");
        List<Integer> vrams =  readDBint("GPU","Vram_size");
        List<Integer> powerUsages =  readDBint("GPU","Power_Usage");

        for(int i = 0; i < names.size(); i++){
            gpus.add(new GPU(names.get(i), prices.get(i), vrams.get(i), powerUsages.get(i)));
        }
    }

    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<GPU, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<GPU, Integer>("price"));
        VRAMColumn.setCellValueFactory(new PropertyValueFactory<GPU, Integer>("VRAM"));
        powerUsageColumn.setCellValueFactory(new PropertyValueFactory<GPU, Integer>("powerUsage"));

        ObservableList<GPU> GPUList = tableView.getItems();
        GPUList.addAll(gpus);
        tableView.setItems(GPUList);
    }
}
