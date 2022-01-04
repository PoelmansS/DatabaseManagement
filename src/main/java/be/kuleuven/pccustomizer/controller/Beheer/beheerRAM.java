package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.RAM;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class beheerRAM extends _BeheerCommon {
    RAM modifiedRAM;

    //table
    @FXML
    private TableView<RAM> tableView;
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
    private TableColumn<RAM, String> nameColumn;
    @FXML
    private TableColumn<RAM, String> typeColumn;
    @FXML
    private TableColumn<RAM, Integer> priceColumn;
    @FXML
    private TableColumn<RAM, Integer> sizeColumn;

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
        nameColumn.setCellValueFactory(new PropertyValueFactory<RAM, String>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<RAM, String>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<RAM, Integer>("price"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<RAM, Integer>("size"));

        RAM case1 = new RAM("RAM1","DDR4",50,16);
        RAM case2 = new RAM("RAM2","DDR5",80,32);
        RAM case3 = new RAM("RAM3","DDR5",350,64);
        ObservableList<RAM> RAMList = tableView.getItems();
        RAMList.add(case1);
        RAMList.add(case2);
        RAMList.add(case3);
        tableView.setItems(RAMList);

    }


    public void addNewRow() {
        RAM ram = new RAM(addName.getText(),addType.getText(), Integer.parseInt(addPrice.getText()), Integer.parseInt(addSize.getText()));
        ObservableList<RAM> RAMList = tableView.getItems();
        RAMList.add(ram);
        tableView.setItems(RAMList);

        System.out.println(addName.getText() + " " + addType.getText() +  " " + (addPrice.getText()) + " " +  addSize.getText());
    }

    public void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            RAM ram = tableView.getSelectionModel().getSelectedItem();
            addName.setText(ram.getName());
            addType.setText(ram.getType());
            addPrice.setText(String.valueOf(ram.getPrice()));
            addSize.setText(String.valueOf(ram.getSize()));
            modifiedRAM = new RAM(ram.getName(), ram.getType(),ram.getPrice(),ram.getSize());
        }
    }
    public void modifyCurrentRow(){
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedRAM.setName(addName.getText());
        modifiedRAM.setType(addType.getText());
        modifiedRAM.setPrice(Integer.parseInt(addPrice.getText()));
        modifiedRAM.setSize(Integer.parseInt(addSize.getText()));

        ObservableList<RAM> RAMList = tableView.getItems();
        RAMList.set(selectedRow,modifiedRAM);
        tableView.setItems(RAMList);
    }


}
