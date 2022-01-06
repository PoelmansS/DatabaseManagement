package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.CPU;
import be.kuleuven.pccustomizer.controller.Objects.Extra;
import be.kuleuven.pccustomizer.controller.Objects.MotherBoard;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class beheerMotherboard extends _BeheerCommon {
    MotherBoard modifiedMotherboard;
    List<MotherBoard> motherBoards = new ArrayList<MotherBoard>();
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
        List<String> names = readDBstring("MotherBord","Name");
        List<Boolean> hasWifis =  readDBbool("MotherBord","Wifi");
        List<Integer> prices =  readDBint("MotherBord","Price");
        List<String> caseSizes =  readDBstring("MotherBord","Required_case_size");
        List<Integer> ramSlots =  readDBint("MotherBord","RAM_slots");
        List<Integer> pcieSlots =  readDBint("MotherBord","PCI_express_slots");

        for(int i = 0; i < names.size(); i++){
            motherBoards.add(new MotherBoard(names.get(i), hasWifis.get(i), prices.get(i), caseSizes.get(i),
                    ramSlots.get(i), pcieSlots.get(i)));
        }
    }

    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, String>("name"));
        hasWifiColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, Boolean>("hasWifi"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, Integer>("price"));
        caseSizeColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, String>("caseSize"));
        RAMSlotsColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, Integer>("RAMSlots"));
        PCIESlotsColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, Integer>("PCIESlots"));

        ObservableList<MotherBoard> MotherBoardList = tableView.getItems();
        MotherBoardList.addAll(motherBoards);
        tableView.setItems(MotherBoardList);

    }

    public void deleteCurrentRow() {
        ObservableList<MotherBoard> motherBoardList = tableView.getItems();
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        jdbi.useHandle(handle -> {
            handle.execute("DELETE FROM Motherbord WHERE Name = ?", motherBoardList.get(0).getName());
        });
        tableView.getItems().remove(selectedRow);
    }

    public void addNewRow() {
            MotherBoard motherBoard = new MotherBoard(addName.getText(),Boolean.parseBoolean(addHasWifi.getText()),Integer.parseInt(addPrice.getText()),
                    addCaseSize.getText(),Integer.parseInt(addRAMSlots.getText()),Integer.parseInt(addPCIESlots.getText()));
              jdbi.useHandle(handle -> { handle.execute("insert into Motherbord (Name, Wifi ,Price, Required_case_size, RAM_slots, PCI_express_slots) values (?, ?, ?, ?,?,?)",
                      motherBoard.getName(),motherBoard.isHasWifi() ,motherBoard.getPrice(),
                      motherBoard.getCaseSize(), motherBoard.getRAMSlots(), motherBoard.getPCIESlots());});
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

        jdbi.useHandle(handle -> {
            handle.execute("UPDATE Motherbord SET Name = ? ,Wifi = ?, Price = ?, Required_case_size = ?, RAM_slots = ?, PCI_express_slots = ? WHERE Name = ?",
                    modifiedMotherboard.getName(), modifiedMotherboard.isHasWifi(), modifiedMotherboard.getPrice(),
                    modifiedMotherboard.getCaseSize(), modifiedMotherboard.getRAMSlots() , modifiedMotherboard.getPCIESlots());
        });

        ObservableList<MotherBoard> MotherBoardList = tableView.getItems();
        MotherBoardList.set(selectedRow,modifiedMotherboard);
        tableView.setItems(MotherBoardList);
    }



}
