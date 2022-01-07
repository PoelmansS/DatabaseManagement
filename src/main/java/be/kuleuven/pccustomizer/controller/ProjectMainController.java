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

public class ProjectMainController {

    @FXML
    private Button btnComputerConfigs;
    @FXML
    private Button btnHwComponenten;
    @FXML
    private Button btnConfigAttaches;
    @FXML
    private Button btnKlanten;
    @FXML
    private Button btnCart;
    @FXML
    private Button btnBestellingen;

    public void initialize() {
        btnHwComponenten.setOnAction(e -> showBeheerScherm("hwcomponenten"));
        btnComputerConfigs.setOnAction(e -> showBeheerScherm("computerconfigs"));
        btnConfigAttaches.setOnAction(e -> showConfigScherm("Cpu"));
        btnKlanten.setOnAction(e -> showBeheerScherm("klanten"));
        btnCart.setOnAction(e -> showBeheerScherm("cart"));
        btnBestellingen.setOnAction(e -> showBeheerScherm("bestelling"));
    }

    private void showBeheerScherm(String id) {
        var resourceName = "beheer" + id + ".fxml";
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
    private void showConfigScherm(String id) {
        var resourceName = "config" + id + ".fxml";
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
            throw new RuntimeException("Kan configScherm " + resourceName + " niet vinden", e);
        }
    }
}
