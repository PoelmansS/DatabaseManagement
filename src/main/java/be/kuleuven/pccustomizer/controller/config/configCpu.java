package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.controller.Objects.CPU;
import be.kuleuven.pccustomizer.controller.Objects.Component;
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

public class configCpu  extends _beheerConfig{
    Component component = new Component();
    List<CPU> cpus = new ArrayList<CPU>();
    @FXML
    private TableView<CPU> tableView;

    @FXML
    private TableColumn<CPU, String> nameColumn;
    @FXML
    private TableColumn<CPU, Integer> priceColumn;
    @FXML
    private TableColumn<CPU, Integer> threadsColumn;
    @FXML
    private TableColumn<CPU, Integer> coresColumn;
    @FXML
    private TableColumn<CPU, Integer> clockSpeedColumn;
    @FXML
    private TableColumn<CPU, Integer> powerUsageColumn;

    public void initialize() {
        componenten.clear();
        ReadFromDB();
        initTable();
        btnAdd.setOnAction(e -> {
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                addComponent();
                showBeheerScherm("Gpu");
            }
        });
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

        }
        catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm " + resourceName + " niet vinden", e);
        }
    }

    private void ReadFromDB() {
        List<String> names = readDBstring("CPU", "Name");
        List<Integer> prices = readDBint("CPU", "Price");
        List<Integer> threads = readDBint("CPU", "Threads");
        List<Integer> cores = readDBint("CPU", "Cores");
        List<Integer> clockSpeeds = readDBint("CPU", "Clock_speed");
        List<Integer> powerUsages = readDBint("CPU", "Power_usage");

        for (int i = 0; i < names.size(); i++) {
            cpus.add(new CPU(names.get(i), prices.get(i), threads.get(i), cores.get(i), clockSpeeds.get(i), powerUsages.get(i)));
        }
    }

    private void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<CPU, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<CPU, Integer>("price"));
        threadsColumn.setCellValueFactory(new PropertyValueFactory<CPU, Integer>("threads"));
        coresColumn.setCellValueFactory(new PropertyValueFactory<CPU, Integer>("cores"));
        clockSpeedColumn.setCellValueFactory(new PropertyValueFactory<CPU, Integer>("clockSpeed"));
        powerUsageColumn.setCellValueFactory(new PropertyValueFactory<CPU, Integer>("powerUsage"));

        ObservableList<CPU> CPUList = tableView.getItems();
        CPUList.addAll(cpus);
        tableView.setItems(CPUList);
    }

    private void addComponent(){
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            CPU cpu = tableView.getSelectionModel().getSelectedItem();
            component.setName(cpu.getName());
            componenten.add(component);
        }
    }


}
