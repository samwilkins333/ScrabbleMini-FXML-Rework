package main.java.samwilkins333.ScrabbleMini.FXML.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.BindingMode;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.ObservableImage;
import main.java.samwilkins333.ScrabbleMini.Logic.ScrabbleBoard.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.ScrabbleBoard.BoardInitializer.TextConfigurer;
import main.java.samwilkins333.ScrabbleMini.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class BackgroundController implements Initializable {
  @FXML public ImageView desktopView; private ObservableImage desktop;
  @FXML public Pane boardRoot;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    initializeImages();
    initializeBoard();
  }

  private void initializeImages() {
    desktop = ObservableImage.create(desktopView,"background/desktop.jpg", BindingMode.BIDIRECTIONAL);
    desktop.control().width(Main.screenWidth);
    desktop.control().opacity(1);
  }

  private void initializeBoard() {
    Board board = new Board(boardRoot, new TextConfigurer());
  }
}
