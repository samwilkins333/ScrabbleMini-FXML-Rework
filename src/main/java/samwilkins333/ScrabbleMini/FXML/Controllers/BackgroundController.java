package main.java.samwilkins333.ScrabbleMini.FXML.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.BindingMode;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.ObservableImage;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Initializer.TextConfigurer;
import main.java.samwilkins333.ScrabbleMini.Main;

import java.net.URL;
import java.util.ResourceBundle;

import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.*;

public class BackgroundController implements Initializable {
  @FXML
  public ImageView desktop;
  private ObservableImage desktopObservable;
  @FXML
  public ImageView leather;
  private ObservableImage leatherObservable;
  @FXML
  public ImageView tilebag;
  private ObservableImage tilebagObservable;
  @FXML
  public Pane boardRoot;
  private Board board;


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    board = new Board(boardRoot, new TextConfigurer());
    initializeImages();
  }

  private void initializeImages() {
    desktopObservable = ObservableImage.create(desktop, "background/desktop.jpg", BindingMode.BIDIRECTIONAL, true);
    desktopObservable.control().width(Main.screenWidth);
    desktopObservable.control().opacity(1);

    int padding = 15;
    leatherObservable = ObservableImage.create(leather, "background/leather.png", BindingMode.BIDIRECTIONAL, false);
    leatherObservable.control().width(sideLengthPixels + padding * 2);
    leatherObservable.control().height(sideLengthPixels + padding * 2);
    leatherObservable.control().layoutX(originLeftPixels - padding);
    leatherObservable.control().layoutY(originTopPixels - padding);
    leatherObservable.control().opacity(1);
    leatherObservable.shadow(true);

    tilebagObservable = ObservableImage.create(tilebag, "background/tilebag.png", BindingMode.BIDIRECTIONAL, true);
    tilebagObservable.control().width(250);
    tilebagObservable.control().layoutX(200);
    tilebagObservable.control().layoutY(350);
    tilebagObservable.control().opacity(1);
    tilebagObservable.shadow(true);
  }
}
