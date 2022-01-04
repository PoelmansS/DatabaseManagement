package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.MotherBoard;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class beheerMotherboard extends _BeheerCommon {
    MotherBoard modifiedMotherboard;

    //table
    @FXML
    private TableView<MotherBoard> tableView;
    //input text fields
    @FXML
    private TextField addName;
    @FXML
    private TextField addHasWifi;
    @FXML
    private TextField addPrice;
    @FXML
    private TextField addCaseSize;
    @FXML
    private TextField addRAMSlots;
    @FXML
    private TextField addPCIESlots;

    @FXML
    private TableColumn<MotherBoard, String> nameColumn;
    @FXML
    private TableColumn<MotherBoard, Boolean> hasWifiColumn;
    @FXML
    private TableColumn<MotherBoard, Integer> priceColumn;
    @FXML
    private TableColumn<MotherBoard, String> caseSizeColumn;
    @FXML
    private TableColumn<MotherBoard, Integer> RAMSlotsColumn;
    @FXML
    private TableColumn<MotherBoard, Integer> PCIESlotsColumn;

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
        nameColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, String>("name"));
        hasWifiColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, Boolean>("hasWifi"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, Integer>("price"));
        caseSizeColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, String>("caseSize"));
        RAMSlotsColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, Integer>("RAMSlots"));
        PCIESlotsColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, Integer>("PCIESlots"));


        MotherBoard motherBoard1 = new MotherBoard("motherboard1",true,150,"small",2,1);
        MotherBoard motherBoard2 = new MotherBoard("motherboard2",false,250,"medium",4,2);
        MotherBoard motherBoard3 = new MotherBoard("motherboard3",true,350,"large",8,4);
        ObservableList<MotherBoard> MotherBoardList = tableView.getItems();
        MotherBoardList.add(motherBoard1);
        MotherBoardList.add(motherBoard2);
        MotherBoardList.add(motherBoard3);
        tableView.setItems(MotherBoardList);

    }


    public void addNewRow() {
            MotherBoard motherBoard = new MotherBoard(addName.getText(),Boolean.parseBoolean(addHasWifi.getText()),Integer.parseInt(addPrice.getText()),
                    addCaseSize.getText(),Integer.parseInt(addRAMSlots.getText()),Integer.parseInt(addPCIESlots.getText()));
            ObservableList<MotherBoard> MotherBoardList = tableView.getItems();
            MotherBoardList.add(motherBoard);
            tableView.setItems(MotherBoardList);
    }

    public void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            MotherBoard motherBoard = tableView.getSelectionModel().getSelectedItem();
            addName.setText(motherBoard.getName());
            addHasWifi.setText(String.valueOf(motherBoard.isHasWifi()));
            addPrice.setText(String.valueOf(motherBoard.getPrice()));
            addCaseSize.setText(motherBoard.getCaseSize());
            addRAMSlots.setText(String.valueOf(motherBoard.getRAMSlots()));
            addPCIESlots.setText(String.valueOf(motherBoard.getPCIESlots()));
            modifiedMotherboard = new MotherBoard(motherBoard.getName(), motherBoard.isHasWifi(),motherBoard.getPrice(),
                    motherBoard.getCaseSize(), motherBoard.getRAMSlots(),motherBoard.getPCIESlots());
        }
    }
    public void modifyCurrentRow(){
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedMotherboard.setName(addName.getText());
        modifiedMotherboard.setHasWifi(Boolean.parseBoolean(addHasWifi.getText()));
        modifiedMotherboard.setPrice(Integer.parseInt(addPrice.getText()));
        modifiedMotherboard.setCaseSize(addCaseSize.getText());
        modifiedMotherboard.setRAMSlots(Integer.parseInt(addRAMSlots.getText()));
        modifiedMotherboard.setPCIESlots(Integer.parseInt(addPCIESlots.getText()));

        ObservableList<MotherBoard> MotherBoardList = tableView.getItems();
        MotherBoardList.set(selectedRow,modifiedMotherboard);
        tableView.setItems(MotherBoardList);
    }



}
