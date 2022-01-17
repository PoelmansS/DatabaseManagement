package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.Extra;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class beheerExtra extends _BeheerCommon {
    private Extra modifiedExtra;
    private final List<Extra> extras = new ArrayList<Extra>();
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
    private TextField addAantal;
    @FXML
    private TableColumn<Extra, String> nameColumn;
    @FXML
    private TableColumn<Extra, String> typeColumn;
    @FXML
    private TableColumn<Extra, Integer> priceColumn;

    public void initialize() {
        init(tableView);
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

    public void deleteCurrentRow() {
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Extra extra = tableView.getSelectionModel().getSelectedItem();
            jdbi.useHandle(handle -> {
                handle.execute("DELETE FROM Extra WHERE Name = ?", extra.getName());
            });
            tableView.getItems().remove(selectedRow);
        }
    }

    public void addNewRow() {
        if(!doubles("Extra", "Name", addName.getText())) {
            Extra extra = new Extra(addName.getText(), addType.getText(), Integer.parseInt(addPrice.getText()),
                    Integer.parseInt(addAantal.getText()));
            jdbi.useHandle(handle -> {
                handle.execute("insert into Extra (Name,Type,  Price) values (?, ?, ?)",
                        extra.getName(), extra.getType(), extra.getPrice());
            });

            ObservableList<Extra> extraList = tableView.getItems();
            extraList.add(extra);
            tableView.setItems(extraList);
        }
        else{
            showAlert("Unseported Entry","Dit component bestaat al in de db");
        }
    }

    public void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Extra extra = tableView.getSelectionModel().getSelectedItem();
            addName.setText(extra.getName());
            addType.setText(extra.getType());
            addPrice.setText(String.valueOf(extra.getPrice()));
            addAantal.setText(String.valueOf(extra.getAantal()));

            modifiedExtra = new Extra(extra.getName(), extra.getType(), extra.getPrice(), extra.getAantal());
        }
    }

    public void modifyCurrentRow(){
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedExtra.setName(addName.getText());
        modifiedExtra.setType(addType.getText());
        modifiedExtra.setPrice(Integer.parseInt(addPrice.getText()));

        jdbi.useHandle(handle -> {
            handle.execute("UPDATE Extra SET Name = ?, Type = ? ,Price = ? WHERE Name = ?",
                    modifiedExtra.getName(), modifiedExtra.getType(), modifiedExtra.getPrice(), modifiedExtra.getName());
        });

        ObservableList<Extra> extraList = tableView.getItems();
        extraList.set(selectedRow,modifiedExtra);
        tableView.setItems(extraList);
    }
}
