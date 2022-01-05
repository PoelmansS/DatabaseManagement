package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.CPU;
import be.kuleuven.pccustomizer.controller.Objects.Extra;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class beheerExtra extends _BeheerCommon {
    Extra modifiedExtra;
    List<Extra> extras = new ArrayList<Extra>();

    //table
    @FXML
    private TableView<Extra> tableView;
    //input text fields
    @FXML
    private TextField addName;
    @FXML
    private TextField addType;
    @FXML
    private TextField addPrice;

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
            verifyInput();
        });
        btnModify.setOnAction(e -> {
            verifyOneRowSelected(tableView);
            verifyModifyInput();
        });
        btnDelete.setOnAction(e -> {
            verifyOneRowSelected(tableView);
            deleteCurrentRow();
        });
        btnLoad.setOnAction(e -> {
            LoadCurrentRow();
        });
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
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
    public void deleteCurrentRow() {
        ObservableList<Extra> extraList = tableView.getItems();
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        jdbi.useHandle(handle -> {
            handle.execute("DELETE FROM Extra WHERE Name = ?", extraList.get(0).getName());
        });
        tableView.getItems().remove(selectedRow);
    }

    public void addNewRow() {
        Extra extra = new Extra(addName.getText(),addType.getText(), Integer.parseInt(addPrice.getText()));
        ObservableList<Extra> extraList = tableView.getItems();
        extraList.add(extra);
        tableView.setItems(extraList);

    }

    public void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Extra extra = tableView.getSelectionModel().getSelectedItem();
            addName.setText(extra.getName());
            addType.setText(extra.getType());
            addPrice.setText(String.valueOf(extra.getPrice()));

            modifiedExtra = new Extra(extra.getName(), extra.getType(),extra.getPrice());
        }
    }
    public void modifyCurrentRow(){
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedExtra.setName(addName.getText());
        modifiedExtra.setType(addType.getText());
        modifiedExtra.setPrice(Integer.parseInt(addPrice.getText()));


        ObservableList<Extra> extraList = tableView.getItems();
        extraList.set(selectedRow,modifiedExtra);
        tableView.setItems(extraList);
    }
}
