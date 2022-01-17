package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.Case;
import be.kuleuven.pccustomizer.controller.Objects.Extra;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.jdbi.v3.core.Jdbi;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.valueOf;

public class beheerCase extends _BeheerCommon {
    private Case modifiedCase;
    private final List<Case> cases = new ArrayList<Case>();
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
    private TextField addAantal;
    @FXML
    private TableColumn<Case, String> nameColumn;
    @FXML
    private TableColumn<Case, String> typeColumn;
    @FXML
    private TableColumn<Case, Integer> priceColumn;
    @FXML
    private TableColumn<Case, String> sizeColumn;

    public void initialize() {
        ReadFromDB();
        initTable();
        btnAdd.setOnAction(e -> {
            if(doubles("PcCase", "Name", addName.getText())){
                System.out.println("Deze case bestaat al");
                //we kunnen hier toevoegen voor de aantallen
            }
            else{
                addNewRow();
            }
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
        List<String> names = readDBstring("PcCase","Name");
        List<String> types =  readDBstring("PcCase","Type");
        List<Integer> prices =  readDBint("PcCase","Price");
        List<String> sizes =  readDBstring("PcCase","Size");
        List<Integer> aantallen =  readDBint("PcCase","Aantal");

        for(int i = 0; i < names.size(); i++){
            cases.add(new Case(names.get(i), types.get(i), prices.get(i), sizes.get(i), aantallen.get(i)));
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

    public void deleteCurrentRow() {
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Case cases = tableView.getSelectionModel().getSelectedItem();
            jdbi.useHandle(handle -> {
                handle.execute("DELETE FROM PcCase WHERE Name = ?", cases.getName()); });
            tableView.getItems().remove(selectedRow);
        }
    }

    public void addNewRow() {
        if(doubles("PcCase", "Name", addName.getText())){
            System.out.println("Deze case bestaat al");
            //we kunnen hier toevoegen voor de aantallen
        }
        Case _case = new Case(addName.getText(),addType.getText(), Integer.parseInt(addPrice.getText()), addSize.getText(), Integer.parseInt(addAantal.getText()));
        jdbi.useHandle(handle -> {
            handle.execute("insert into PcCase (Name, Type, Price, Size) values (?, ?, ?, ?)",
                    _case.getName(),_case.getType(),_case.getPrice(),_case.getSize()); });

        ObservableList<Case> caseList = tableView.getItems();
        caseList.add(_case);
        tableView.setItems(caseList);
    }

    public void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Case _case = tableView.getSelectionModel().getSelectedItem();
            addName.setText(_case.getName());
            addType.setText(_case.getType());
            addPrice.setText(String.valueOf(_case.getPrice()));
            addSize.setText(_case.getSize());
            addAantal.setText(String.valueOf(_case.getPrice()));

            modifiedCase = new Case(_case.getName(), _case.getType(), _case.getPrice(), _case.getSize(), _case.getAantal());
        }
    }

    public void modifyCurrentRow(){
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedCase.setName(addName.getText());
        modifiedCase.setType(addType.getText());
        modifiedCase.setPrice(Integer.parseInt(addPrice.getText()));
        modifiedCase.setSize(addSize.getText());

        jdbi.useHandle(handle -> {
            handle.execute("UPDATE PcCase SET Name = ? ,Type = ?, Price = ? , Size = ? WHERE Name = ?",
                    modifiedCase.getName(), modifiedCase.getType(), modifiedCase.getPrice(), modifiedCase.getSize(), modifiedCase.getName());
        });

        ObservableList<Case> caseList = tableView.getItems();
        caseList.set(selectedRow,modifiedCase);
        tableView.setItems(caseList);
    }
}
