package main.java.samwilkins333.ScrabbleMini;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;

/**
 * The main class of the application that initializes the
 * FXML controller and records screen dimensions.
 */
public class Main extends Application {
  public static double screenWidth;
  public static double screenHeight;
  static {
    Rectangle2D bounds = Screen.getPrimary().getBounds();
    screenWidth = bounds.getWidth();
    screenHeight = bounds.getHeight();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    URL resource = getClass().getResource("FXML/Scenes/Background.fxml");
    Parent root = FXMLLoader.load(resource);
    primaryStage.setTitle("ScrabbleMini");

    primaryStage.setScene(new Scene(root, screenWidth, screenHeight));
    primaryStage.show();
  }

  /**
   * @param args command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * A utility method that
   * prints the given reason, if any,
   * before exiting the application.
   * @param reason why the application must exit.
   */
  public static void exit(String reason) {
    if (reason != null) {
      System.out.printf("The application had to exit:\n%s", reason);
    }
    System.exit(1);
  }
}
