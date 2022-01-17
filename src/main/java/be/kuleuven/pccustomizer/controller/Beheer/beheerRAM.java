package be.kuleuven.pccustomizer.controller.Beheer;
import be.kuleuven.pccustomizer.controller.Objects.RAM;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class beheerRAM extends _BeheerCommon {
    private RAM modifiedRAM;
    private final List<RAM> rams = new ArrayList<RAM>();

    @FXML
    private TableView<RAM> tableView;

    @FXML
    private TextField addName;
    @FXML
    private TextField addType;
    @FXML
    private TextField addPrice;
    @FXML
    private TextField addSize;
    @FXML
    private TextField addNRofSticks;
    @FXML
    private TextField addAantal;
    @FXML
    private TableColumn<RAM, String> nameColumn;
    @FXML
    private TableColumn<RAM, String> typeColumn;
    @FXML
    private TableColumn<RAM, Integer> priceColumn;
    @FXML
    private TableColumn<RAM, Integer> sizeColumn;
    @FXML
    private TableColumn<RAM, Integer> NRofSticksColumn;
    @FXML
    private TableColumn<RAM, Integer> aantalColumn;
    public void initialize() {
        init(tableView);
    }

    public void ReadFromDB(){
        List<String> names = readDBstring("RAM","Name");
        List<String> types =  readDBstring("RAM","Type");
        List<Integer> prices =  readDBint("RAM","Price");
        List<Integer> sizes =  readDBint("RAM","Size");
        List<Integer> NRofSticks =  readDBint("RAM","Size");
        for(int i = 0; i < names.size(); i++){
            rams.add(new RAM(names.get(i), types.get(i), prices.get(i), sizes.get(i), NRofSticks.get(i)));
        }
    }

    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<RAM, String>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<RAM, String>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<RAM, Integer>("price"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<RAM, Integer>("size"));
        NRofSticksColumn.setCellValueFactory(new PropertyValueFactory<RAM, Integer>("NRofSticks"));
        aantalColumn.setCellValueFactory(new PropertyValueFactory<RAM, Integer>("aantal"));
        ObservableList<RAM> RAMList = tableView.getItems();
        RAMList.addAll(rams);
        tableView.setItems(RAMList);

    }

    public void deleteCurrentRow() {
        ObservableList<RAM> RAMList = tableView.getItems();
        selectedRow = tableView.getSelectionModel().getSelectedIndex();
        jdbi.useHandle(handle -> {
            handle.execute("DELETE FROM RAM WHERE Name = ?", RAMList.get(0).getName());
        });
        tableView.getItems().remove(selectedRow);
    }

    public void addNewRow() {
        if(!doubles("RAM", "Name", addName.getText())) {
            RAM ram = new RAM(addName.getText(), addType.getText(), Integer.parseInt(addPrice.getText()), Integer.parseInt(addSize.getText()), Integer.parseInt(addNRofSticks.getText());
            jdbi.useHandle(handle -> {
                handle.execute("insert into RAM (Name, Type ,Price, Size, Number_of_sticks, aantal) values (?, ?, ?, ?, ?, ?)",
                        ram.getName(), ram.getType(), ram.getPrice(), ram.getSize(), ram.getNRofSlots(), ram.getAantal());
            });

            ObservableList<RAM> RAMList = tableView.getItems();
            RAMList.add(ram);
            tableView.setItems(RAMList);
        }
        else{
            showAlert("Unseported Entry","Dit component bestaat al in de db");
        }
    }

    public void LoadCurrentRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            RAM ram = tableView.getSelectionModel().getSelectedItem();
            addName.setText(ram.getName());
            addType.setText(ram.getType());
            addPrice.setText(String.valueOf(ram.getPrice()));
            addSize.setText(String.valueOf(ram.getSize()));
            addNRofSticks.setText(String.valueOf(ram.getNRofSlots()));
            addAantal.setText(String.valueOf(ram.getAantal()));

            modifiedRAM = new RAM(ram.getName(), ram.getType(),ram.getPrice(),ram.getSize(), ram.getNRofSlots());
        }
    }
    public void modifyCurrentRow(){
        selectedRow = tableView.getSelectionModel().getSelectedIndex();

        modifiedRAM.setName(addName.getText());
        modifiedRAM.setType(addType.getText());
        modifiedRAM.setPrice(Integer.parseInt(addPrice.getText()));
        modifiedRAM.setSize(Integer.parseInt(addSize.getText()));

        jdbi.useHandle(handle -> {
            handle.execute("UPDATE RAM SET Name = ?, Type = ? , Price = ?, Size = ?, Number_of_sticks=? WHERE Name = ?",
                    modifiedRAM.getName(), modifiedRAM.getType(), modifiedRAM.getPrice(), modifiedRAM.getSize(), modifiedRAM.getName() ,modifiedRAM.getNRofSlots());
        });

        ObservableList<RAM> RAMList = tableView.getItems();
        RAMList.set(selectedRow,modifiedRAM);
        tableView.setItems(RAMList);
    }
}
