package main.java.samwilkins333.ScrabbleMini;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

// https://docs.oracle.com/javafx/2/fxml_get_started/why_use_fxml.htm#CHDCHIBE
// https://scss.tcd.ie/publications/theses/diss/2015/TCD-SCSS-DISSERTATION-2015-069.pdf

public class Main extends Application {
    public static double screenWidth = Screen.getPrimary().getBounds().getWidth();
    public static double ScreenHeight = Screen.getPrimary().getBounds().getHeight();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXML/Scenes/Background.fxml"));
        primaryStage.setTitle("Hello World");

        Rectangle2D bounds = Screen.getPrimary().getBounds();

        primaryStage.setScene(new Scene(root, bounds.getWidth(), bounds.getHeight()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
