package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.controller.Objects.Component;
import be.kuleuven.pccustomizer.controller.Objects.Extra;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class configExtra extends _ConfigCommon {
    private List<Extra> extras = new ArrayList<Extra>();

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
        init(tableView, "Checkout");
        btnSkip.setOnAction(e -> {
            skipComponent();
            showBeheerScherm("Checkout");
        });
    }

    public void ReadFromDB(){
        List<String> names = readDBstring("Extra","Name");
        List<String> types =  readDBstring("Extra","Type");
        List<Integer> prices =  readDBint("Extra","Price");
        List<Integer> aantallen =  readDBint("Extra","Aantal");

        for(int i = 0; i < names.size(); i++){
            extras.add(new Extra(names.get(i), types.get(i), prices.get(i), aantallen.get(i)));
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

    public void addComponent(){
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Extra extra = tableView.getSelectionModel().getSelectedItem();
            component.setName(extra.getName());
            componenten.add(component);
        }
    }
}
