package be.kuleuven.pccustomizer.controller;

import be.kuleuven.pccustomizer.controller.Objects.Klant;
import be.kuleuven.pccustomizer.controller.Objects.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class beheercart {
    CustomPC modifiedCustomPC;
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
    private TableView<CustomPC> tableView;
    //input text fields
    @FXML
    private TextField addName;
    @FXML
    private TextField addType;
    @FXML
    private TextField addPrice;
    @FXML
    private TextField addMotherboard;
    @FXML
    private TextField addCPU;
    @FXML
    private TextField addGPU;
    @FXML
    private TextField addRAM;
    @FXML
    private TextField addCase;
    @FXML
    private TextField addPSU;
    @FXML
    private TextField addStorage;
    @FXML
    private TextField addCooling;
    @FXML
    private TableColumn<CustomPC, String> nameColumn;
    @FXML
    private TableColumn<CustomPC, String> typeColumn;
    @FXML
    private TableColumn<CustomPC, Integer> priceColumn;
    @FXML
    private TableColumn<CustomPC, MotherBoard> motherboardColumn;
    @FXML
    private TableColumn<CustomPC, CPU> CPUColumn;
    @FXML
    private TableColumn<CustomPC, GPU> GPUColumn;
    @FXML
    private TableColumn<CustomPC, RAM> RAMColumn;
    @FXML
    private TableColumn<CustomPC, Case> caseColumn;
    @FXML
    private TableColumn<CustomPC, PSU> PSUColumn;
    @FXML
    private TableColumn<CustomPC, Storage> storageColumn;
    @FXML
    private TableColumn<CustomPC, Cooling> coolingColumn;


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
        nameColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, String>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, String>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, Integer>("price"));
        motherboardColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, MotherBoard>("motherBoard"));
        CPUColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, CPU>("cpu"));
        GPUColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, GPU>("gpu"));
        RAMColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, RAM>("ram"));
        caseColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, Case>("cases"));
        PSUColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, PSU>("psu"));
        storageColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, Storage>("storage"));
        coolingColumn.setCellValueFactory(new PropertyValueFactory<CustomPC, Cooling>("cooling"));
        //TODO fix
       /*
        CustomPC customPC1 = new CustomPC(1, "Baker","bob", 3600,"lange straat","245","+32 658 486 259" , "BB@gmail.com");
        CustomPC customPC2 = new CustomPC(2, "Doe","John", 3700,"kortere straat","24","+32 658 457 542" , "JD@gmail.com");
        CustomPC customPC3 = new CustomPC(3, "Dover","Ben", 2400,"korte straat","2","+32 659 629 411" , "BD@gmail.com");
        ObservableList<CustomPC> klantList = tableView.getItems();
        klantList.add(customPC1);
        klantList.add(customPC2);
        klantList.add(customPC3);
        tableView.setItems(klantList);

        */
            }

    private void addNewRow() {
        //TODO fix
        /*
        CustomPC pc = new CustomPC(addName.getText(),
                addType.getText(),
                Integer.parseInt(addPrice.getText()),
                addMotherboard.getText(),
                addCPU.getText(),
                addGPU.getText(),
                addRAM.getText(),
                addCase.getText(),
                addStorage.getText(),
                addCooling.getText());


         */



        ObservableList<CustomPC> CustomPCList = tableView.getItems();
        //CustomPCList.add(pc);
        tableView.setItems(CustomPCList);
    }

    private void deleteCurrentRow() {
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        tableView.getItems().remove(selectedRow);
    }

    private void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            CustomPC customPC = tableView.getSelectionModel().getSelectedItem();

            addName.setText(String.valueOf(customPC.getName()));
            addType.setText(customPC.getType());
            addPrice.setText(String.valueOf(customPC.getPrice()));;
            addMotherboard.setText(String.valueOf(customPC.getMotherBoard()));
            addCPU.setText(String.valueOf(customPC.getCpu()));
            addGPU.setText(String.valueOf(customPC.getGpu()));
            addRAM.setText(String.valueOf(customPC.getRam()));
            addCase.setText(String.valueOf(customPC.getCases()));
            addPSU.setText(String.valueOf(customPC.getPsu()));
            addStorage.setText(String.valueOf(customPC.getStorage()));
            addCooling.setText(String.valueOf(customPC.getCooling()));
            //TODO fix
/*
            modifiedCustomPC = new CustomPC(customPC.getName(),
                    customPC.getType(),
                    customPC.getPrice(),
                    customPC.getMotherBoard(),
                    customPC.getCpu(),
                    customPC.getGpu(),
                    customPC.getRam(),
                    customPC.getCases(),
                    customPC.getStorage(),
                    customPC.getCooling());

 */
        }
    }
    private void modifyCurrentRow(){
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedCustomPC.setName(addName.getText());
        modifiedCustomPC.setType(addType.getText());
        //modifiedCustomPC.setPrice();
        //TODO fix

        ObservableList<CustomPC> CustomPCList = tableView.getItems();
        CustomPCList.set(selectedRow,modifiedCustomPC);
        tableView.setItems(CustomPCList);
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
            showAlert("Hela!", "Eerst een record selecteren hé.");
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
