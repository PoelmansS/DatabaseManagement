package be.kuleuven.pccustomizer.controller.config;
import be.kuleuven.pccustomizer.controller.Objects.Case;
import be.kuleuven.pccustomizer.controller.Objects.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class configCase extends _ConfigCommon {
    Component component = new Component();
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
        init(tableView, "Extra");
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

    public void addComponent(){
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Case cases = tableView.getSelectionModel().getSelectedItem();
            component.setName(cases.getName());
            componenten.add(component);
        }
    }
}
