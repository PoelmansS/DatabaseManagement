package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.Case;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.jdbi.v3.core.Jdbi;

import static java.lang.Integer.valueOf;


public class beheerCase extends _BeheerCommon {
    Case modifiedCase;

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
        });
        btnModify.setOnAction(e -> {
            verifyOneRowSelected(tableView);
            verifyModifyInput();
        });
        btnDelete.setOnAction(e -> {
            verifyOneRowSelected(tableView);
            deleteCurrentRow(tableView);
        });
        btnLoad.setOnAction(e -> {
            LoadCurrentRow();
        });
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }

    public void initTable() {
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

    Jdbi jdbi = Jdbi.create("");


    public void addNewRow() {
        Case cases = new Case(addName.getText(),addType.getText(), Integer.parseInt(addPrice.getText()),addSize.getText());
        ObservableList<Case> caseList = tableView.getItems();
        caseList.add(cases);
        tableView.setItems(caseList);
    }

    public void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Case cases = tableView.getSelectionModel().getSelectedItem();
            addName.setText(cases.getName());
            addType.setText(cases.getType());
            addPrice.setText(String.valueOf(cases.getPrice()));
            addSize.setText(cases.getSize());
            modifiedCase = new Case(cases.getName(), cases.getType(),cases.getPrice(),cases.getSize());
        }
    }
    public void modifyCurrentRow(){
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedCase.setName(addName.getText());
        modifiedCase.setType(addType.getText());
        modifiedCase.setPrice(Integer.parseInt(addPrice.getText()));
        modifiedCase.setSize(addSize.getText());

        ObservableList<Case> caseList = tableView.getItems();
        caseList.set(selectedRow,modifiedCase);
        tableView.setItems(caseList);
    }



}
