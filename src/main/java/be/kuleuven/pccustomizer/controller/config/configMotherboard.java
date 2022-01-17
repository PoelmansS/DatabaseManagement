package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.controller.Objects.Component;
import be.kuleuven.pccustomizer.controller.Objects.MotherBoard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class configMotherboard extends _ConfigCommon {
    List<MotherBoard> motherBoards = new ArrayList<MotherBoard>();
    //table
    @FXML
    private TableView<MotherBoard> tableView;

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
        init(tableView, "Cooling");
    }


    public void ReadFromDB(){
        List<String> names = readDBstring("MotherBord","Name");
        List<Boolean> hasWifis =  readDBbool("MotherBord","Wifi");
        List<Integer> prices =  readDBint("MotherBord","Price");
        List<String> caseSizes =  readDBstring("MotherBord","Required_case_size");
        List<Integer> ramSlots =  readDBint("MotherBord","RAM_slots");
        List<Integer> pcieSlots =  readDBint("MotherBord","PCI_express_slots");
        for(int j = 0; j < pcieSlots.size(); j++){
            if(pcieSlots.get(j) < benodigdePci){
                names.remove(j);
                hasWifis.remove(j);
                prices.remove(j);
                caseSizes.remove(j);
                ramSlots.remove(j);
                pcieSlots.remove(j);
            }
        }

        for(int j = 0; j < pcieSlots.size(); j++){
            if(benodigdeRamSlots < pcieSlots.get(j)){
                names.remove(j);
                hasWifis.remove(j);
                prices.remove(j);
                caseSizes.remove(j);
                ramSlots.remove(j);
                pcieSlots.remove(j);
            }
        }

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

    public void addComponent(){
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            MotherBoard motherBoard = tableView.getSelectionModel().getSelectedItem();
            component.setName(motherBoard.getName());
            componenten.add(component);
        }
    }
}
