package be.kuleuven.pccustomizer.controller;

import be.kuleuven.pccustomizer.ProjectMain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

import static com.sun.javafx.application.PlatformImpl.exit;

public class BeheerHwComponentenController {

    @FXML
    private Button btnMotherboard;
    @FXML
    private Button btnPSU;
    @FXML
    private Button btnCPU;
    @FXML
    private Button btnRAM;
    @FXML
    private Button btnGPU;
    @FXML
    private Button btnStorage;
    @FXML
    private Button btnCase;
    @FXML
    private Button btnCooling;
    @FXML
    private Button btnExtra;
    @FXML
    private Button btnClose;

    public void initialize() {


        btnMotherboard.setOnAction(e -> showBeheerScherm("Motherboard"));
        btnPSU.setOnAction(e -> showBeheerScherm("PSU"));
        btnCPU.setOnAction(e -> showBeheerScherm("CPU"));
        btnRAM.setOnAction(e -> showBeheerScherm("RAM"));
        btnGPU.setOnAction(e -> showBeheerScherm("GPU"));
        btnStorage.setOnAction(e -> showBeheerScherm("Storage"));
        btnCase.setOnAction(e -> showBeheerScherm("Case"));
        btnCooling.setOnAction(e -> showBeheerScherm("Cooling"));
        btnExtra.setOnAction(e -> showBeheerScherm("Extra"));

        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }

    private void showMainScherm(String id) {

        var resourceName = id + ".fxml";
        showMethod(id, resourceName);
    }

    private void showMethod(String id, String resourceName) {
        try {
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(resourceName)));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Admin " + id);
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm " + resourceName + " niet vinden", e);
        }
    }

    private void showBeheerScherm(String id) {
        var resourceName = "_c_beheer" + id + ".fxml";
        showMethod(id, resourceName);
    }
}
