package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.controller.Objects.CPU;
import be.kuleuven.pccustomizer.controller.Objects.Component;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class configCPU extends _ConfigCommon {
    private List<CPU> cpus = new ArrayList<CPU>();

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
        init(tableView, "Gpu");
    }


    public void ReadFromDB() {
        List<String> names = readDBstring("CPU", "Name");
        List<Integer> prices = readDBint("CPU", "Price");
        List<Integer> threads = readDBint("CPU", "Threads");
        List<Integer> cores = readDBint("CPU", "Cores");
        List<Integer> clockSpeeds = readDBint("CPU", "Clock_speed");
        List<Integer> powerUsages = readDBint("CPU", "Power_usage");
        List<Integer> aantallen = readDBint("CPU", "Aantal");

        for (int i = 0; i < names.size(); i++) {
            cpus.add(new CPU(names.get(i), prices.get(i), threads.get(i), cores.get(i), clockSpeeds.get(i), powerUsages.get(i), aantallen.get(i)));
        }
    }

    public void initTable() {
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

    public void addComponent(){
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            CPU cpu = tableView.getSelectionModel().getSelectedItem();
            component.setName(cpu.getName());
            componenten.add(component);
            totalW += cpu.getPowerUsage();
        }
    }
}
