package be.kuleuven.pccustomizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

/**
 *  Authors: Poelmans Sander, Wakker Damianus
 *
 *  User Manual:
 *      -This program is an Admin tool to deal with custom built computers, Clients and orders
 *
 *      -To modify a row you have to follow the next steps:
 *          1) Select your row in the table
 *          2) Click the load button to load it in the text boxes
 *          3) Change the values you want to change
 *          4) Click the modify button
 *
 *      -Adding a new row works similar to modifying except you dont have to load in any existing data
 *
 *      -The PC customizer is made so it only displays components that are compatible with each other (according to our input)
 */
public class ProjectMain extends Application {

    private static Stage rootStage;

    public static Stage getRootStage() {
        return rootStage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        rootStage = stage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("pccustomizermain.fxml")));

        Scene scene = new Scene(root);

        stage.setTitle("PC Customizer Administratie hoofdscherm");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
