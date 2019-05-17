package main.java.samwilkins333.ScrabbleMini.FXML.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.BindingMode;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.ObservableImage;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Initializer.BoardReader;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Initializer.TileReader;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.TileBag;
import main.java.samwilkins333.ScrabbleMini.Main;

import java.net.URL;
import java.util.ResourceBundle;

import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.*;

public class BackgroundController implements Initializable {
  @FXML
  public ImageView desktopView;
  private ObservableImage desktopObservable;
  @FXML
  public ImageView leatherView;
  private ObservableImage leatherObservable;
  @FXML
  public ImageView tilebagView;
  @FXML
  public Pane boardRoot;
  private Board board;
  private TileBag tileBag;


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    board = new Board(boardRoot, new BoardReader());
    tileBag = new TileBag(tilebagView, new TileReader());
    initializeBackground();
  }

  private void initializeBackground() {
    desktopObservable = ObservableImage.create(desktopView, "background/desktop.jpg", BindingMode.BIDIRECTIONAL, true);
    desktopObservable.control().width(Main.screenWidth);
    desktopObservable.control().opacity(1);

    int padding = 15;
    leatherObservable = ObservableImage.create(leatherView, "background/leather.png", BindingMode.BIDIRECTIONAL, false);
    leatherObservable.control().width(sideLengthPixels + padding * 2);
    leatherObservable.control().height(sideLengthPixels + padding * 2);
    leatherObservable.control().layoutX(originLeftPixels - padding);
    leatherObservable.control().layoutY(originTopPixels - padding);
    leatherObservable.control().opacity(1);
    leatherObservable.shadow(true);
  }
}
