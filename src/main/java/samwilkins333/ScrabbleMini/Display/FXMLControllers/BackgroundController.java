package main.java.samwilkins333.ScrabbleMini.Display.FXMLControllers;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;

public class BackgroundController {

  @FXML
  public ImageView primaryBackground;

  public BackgroundController() {

  }

  public void test(ActionEvent e) {
    System.out.printf("Hello, world! Here is my event...\n%s\n", e.toString());
  }

}
