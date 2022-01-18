package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.MotherBoard;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class beheerMotherboard extends _BeheerCommon {
    private MotherBoard modifiedMotherboard;
    private final List<MotherBoard> motherBoards = new ArrayList<MotherBoard>();
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
    private TextField addAantal;
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
    @FXML
    private TableColumn<MotherBoard, Integer> aantalColumn;

    public void initialize() {
        init(tableView);
    }

    public void ReadFromDB(){
        List<String> names = readDBstring("MotherBord","Name");
        List<Boolean> hasWifis =  readDBbool("MotherBord","Wifi");
        List<Integer> prices =  readDBint("MotherBord","Price");
        List<String> caseSizes =  readDBstring("MotherBord","Required_case_size");
        List<Integer> ramSlots =  readDBint("MotherBord","RAM_slots");
        List<Integer> pcieSlots =  readDBint("MotherBord","PCI_express_slots");
        List<Integer> aantallen =  readDBint("MotherBord","Aantal");

        for(int i = 0; i < names.size(); i++){
            motherBoards.add(new MotherBoard(names.get(i), hasWifis.get(i), prices.get(i), caseSizes.get(i),
                    ramSlots.get(i), pcieSlots.get(i), aantallen.get(i)));
        }
    }

    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, String>("name"));
        hasWifiColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, Boolean>("hasWifi"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, Integer>("price"));
        caseSizeColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, String>("caseSize"));
        RAMSlotsColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, Integer>("RAMSlots"));
        PCIESlotsColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, Integer>("PCIESlots"));
        aantalColumn.setCellValueFactory(new PropertyValueFactory<MotherBoard, Integer>("aantal"));

        ObservableList<MotherBoard> MotherBoardList = tableView.getItems();
        MotherBoardList.addAll(motherBoards);
        tableView.setItems(MotherBoardList);

    }

    public void deleteCurrentRow() {
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            MotherBoard motherboard = tableView.getSelectionModel().getSelectedItem();
            jdbi.useHandle(handle -> {
                handle.execute("DELETE FROM Motherbord WHERE Name = ?", motherboard.getName());
            });
            tableView.getItems().remove(selectedRow);
        }
    }

    public void addNewRow() {
        if(!doubles("Motherbord", "Name", addName.getText())) {
            MotherBoard motherBoard = new MotherBoard(addName.getText(), Boolean.parseBoolean(addHasWifi.getText()), Integer.parseInt(addPrice.getText()),
                    addCaseSize.getText(), Integer.parseInt(addRAMSlots.getText()), Integer.parseInt(addPCIESlots.getText()),
                    Integer.parseInt(addAantal.getText()));
            jdbi.useHandle(handle -> {
                handle.execute("insert into Motherbord (Name, Wifi ,Price, Required_case_size, RAM_slots, PCI_express_slots) values (?, ?, ?, ?,?,?)",
                        motherBoard.getName(), motherBoard.isHasWifi(), motherBoard.getPrice(),
                        motherBoard.getCaseSize(), motherBoard.getRAMSlots(), motherBoard.getPCIESlots());
            });
            ObservableList<MotherBoard> MotherBoardList = tableView.getItems();
            MotherBoardList.add(motherBoard);
            tableView.setItems(MotherBoardList);
        }
        else{
            showAlert("Unseported Entry","Dit component bestaat al in de db");
        }
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
            addAantal.setText(String.valueOf(motherBoard.getAantal()));
            modifiedMotherboard = new MotherBoard(motherBoard.getName(), motherBoard.isHasWifi(), motherBoard.getPrice(),
                    motherBoard.getCaseSize(), motherBoard.getRAMSlots(), motherBoard.getPCIESlots(), motherBoard.getAantal());
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
                    modifiedMotherboard.getCaseSize(), modifiedMotherboard.getRAMSlots() , modifiedMotherboard.getPCIESlots()
                    ,modifiedMotherboard.getName());
        });

        ObservableList<MotherBoard> MotherBoardList = tableView.getItems();
        MotherBoardList.set(selectedRow,modifiedMotherboard);
        tableView.setItems(MotherBoardList);
    }
}
