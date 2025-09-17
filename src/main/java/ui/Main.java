package ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nailong.Nailong;


/**
 * A GUI for Nailong using FXML.
 */
public class Main extends Application {

    private Nailong nailong = new Nailong("./data/Nailong.txt");

    @Override
    public void start(Stage stage) {
        try {
            //creates the loader and tells it which FXML file to read
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            //parses the FXML file, builds the scene graph
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setMinHeight(780);
            stage.setMinWidth(450);
            stage.setTitle("Nailong\uD83D\uDC23");
            stage.setScene(scene);
            //fx: controller attribute in FXML tells the loader which Java Class to use
            //fxmlLoader.load() : loader creates an instance of ui.MainWindow and wires up all the @FXML fields and method
            //After getController() : the loader now has UI tree (anchor pane) and controller instance (ui.MainWindow object)
            fxmlLoader.<MainWindow>getController().setNailong(nailong); // inject the Nailong instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

