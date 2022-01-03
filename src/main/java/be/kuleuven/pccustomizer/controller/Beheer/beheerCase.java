package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.Case;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static java.lang.Integer.valueOf;


public class beheerCase {
    //buttons
    Case modifiedCase;
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
    private TableView<Case> tableView;
    //input text fields
    @FXML
    private TextField addName;
    @FXML
    private TextField addType;
    @FXML
    private TextField addPrice;
    @FXML
    private TextField addSize;

    @FXML
    private TableColumn<Case, String> nameColumn;
    @FXML
    private TableColumn<Case, String> typeColumn;
    @FXML
    private TableColumn<Case, Integer> priceColumn;
    @FXML
    private TableColumn<Case, String> sizeColumn;


    public void initialize() {
        initTable();
        btnAdd.setOnAction(e -> {
            verifyInput();
            addNewRow();
        });
        btnModify.setOnAction(e -> {
            verifyOneRowSelected();
            modifyCurrentRow();
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
        nameColumn.setCellValueFactory(new PropertyValueFactory<Case, String>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Case, String>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Case, Integer>("price"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<Case, String>("size"));

        Case case1 = new Case("Case1","pc",50,"small");
        Case case2 = new Case("Case2","pc",80,"large");
        Case case3 = new Case("Case3","server",350,"large");
        ObservableList<Case> caseList = tableView.getItems();
        caseList.add(case1);
        caseList.add(case2);
        caseList.add(case3);
        tableView.setItems(caseList);

    }


    private void addNewRow() {
        Case cases = new Case(addName.getText(),addType.getText(),valueOf(addPrice.getText()),addSize.getText());
        ObservableList<Case> caseList = tableView.getItems();
        caseList.add(cases);
        tableView.setItems(caseList);

        System.out.println(addName.getText() + " " + addType.getText() +  " " + (addPrice.getText()) + " " +  addSize.getText());
    }

    private void deleteCurrentRow() {
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        tableView.getItems().remove(selectedRow);
    }

    private void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Case cases = tableView.getSelectionModel().getSelectedItem();
            addName.setText(cases.getName());
            addType.setText(cases.getType());
            addPrice.setText(String.valueOf(cases.getPrice()));
            addSize.setText(cases.getSize());
            modifiedCase = new Case(cases.getName(), cases.getType(),cases.getPrice(),cases.getSize());
        }
    }
    private void modifyCurrentRow(){
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedCase.setName(addName.getText());
        modifiedCase.setType(addType.getText());
        modifiedCase.setPrice(Integer.parseInt(addPrice.getText()));
        modifiedCase.setSize(addSize.getText());

        ObservableList<Case> caseList = tableView.getItems();
        caseList.set(selectedRow,modifiedCase);
        tableView.setItems(caseList);
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
        if(addName.getText() == null || addType.getText() == null || addPrice.getText() == null || addSize.getText() == null) {
            showAlert("Hela!", "Geen correcte input");
        }
    }
}
