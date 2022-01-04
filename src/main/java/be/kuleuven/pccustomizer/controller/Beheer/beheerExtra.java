package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.Extra;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class beheerExtra {
    //buttons
    Extra modifiedExtra;
    int selectedRow;

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnLoad;
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
        initTable();
        btnAdd.setOnAction(e -> {
            verifyInput();
        });
        btnModify.setOnAction(e -> {
            verifyOneRowSelected();
            verifyModifyInput();
        });
        btnDelete.setOnAction(e -> {
            verifyOneRowSelected();
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

    private void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Extra, String>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Extra, String>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Extra, Integer>("price"));


        Extra extra1 = new Extra("extra1","wifi card",50);
        Extra extra2 = new Extra("extra2","cd burner",80);
        Extra extra3 = new Extra("extra1","external ssd",350);
        ObservableList<Extra> extraList = tableView.getItems();
        extraList.add(extra1);
        extraList.add(extra2);
        extraList.add(extra3);
        tableView.setItems(extraList);

    }


    private void addNewRow() {
        Extra extra = new Extra(addName.getText(),addType.getText(), Integer.parseInt(addPrice.getText()));
        ObservableList<Extra> extraList = tableView.getItems();
        extraList.add(extra);
        tableView.setItems(extraList);

    }

    private void deleteCurrentRow() {
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        tableView.getItems().remove(selectedRow);
    }

    private void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Extra extra = tableView.getSelectionModel().getSelectedItem();
            addName.setText(extra.getName());
            addType.setText(extra.getType());
            addPrice.setText(String.valueOf(extra.getPrice()));

            modifiedExtra = new Extra(extra.getName(), extra.getType(),extra.getPrice());
        }
    }
    private void modifyCurrentRow(){
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedExtra.setName(addName.getText());
        modifiedExtra.setType(addType.getText());
        modifiedExtra.setPrice(Integer.parseInt(addPrice.getText()));


        ObservableList<Extra> extraList = tableView.getItems();
        extraList.set(selectedRow,modifiedExtra);
        tableView.setItems(extraList);
    }

    public void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void verifyOneRowSelected() {
        if(tableView.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Eerst een record selecteren he.");
        }
    }
    private void verifyInput() {
        try { addNewRow(); }
        catch (Exception e){ showAlert("Unseported Entry","You tried entering an incorrect value"); }
    }
    private void verifyModifyInput() {
        try { modifyCurrentRow(); }
        catch (Exception e){ showAlert("Unseported Entry","You tried entering an incorrect value"); }
    }
}
