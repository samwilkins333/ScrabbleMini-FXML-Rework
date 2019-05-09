package main.java.samwilkins333.ScrabbleMini.FXML.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.ImageUtils;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.ObservableImage;
import main.java.samwilkins333.ScrabbleMini.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class BackgroundController implements Initializable {
  @FXML public ImageView desktopView; private ObservableImage desktop;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    initializeImages();
  }

  private void initializeImages() {
    desktop = ImageUtils.createObservable(desktopView,"background/desktop.jpg");
    desktop.control().width(Main.screenWidth);
  }
}
