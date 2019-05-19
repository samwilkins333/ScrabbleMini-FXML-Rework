package main.java.samwilkins333.ScrabbleMini.FXML.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.BindingMode;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.ObservableImage;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Initializer.BoardReader;
import main.java.samwilkins333.ScrabbleMini.Logic.Control.Match.Match;
import main.java.samwilkins333.ScrabbleMini.Logic.Control.Match.PlayerSchema;
import main.java.samwilkins333.ScrabbleMini.Logic.Control.Match.VisualElements;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Initializer.TileBagReader;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.TileBag;
import main.java.samwilkins333.ScrabbleMini.Main;

import java.net.URL;
import java.util.ResourceBundle;

import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.*;

public class BackgroundController implements Initializable {
  @FXML public ImageView desktopView;
  @FXML public ImageView leatherView;
  @FXML public ImageView tilebagView;
  @FXML public Pane boardPane;

  private VisualElements visuals;
  private PlayerSchema players = new PlayerSchema();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    initializeVisualElements();
    initializeBackground();
  }

  private void initializeVisualElements() {
    visuals = new VisualElements(
      new Board(boardPane, new BoardReader()),
      new TileBag(tilebagView, new TileBagReader())
    );
  }

  private void initializeBackground() {
    ObservableImage desktopObservable = ObservableImage.create(desktopView, "background/desktop.jpg", BindingMode.BIDIRECTIONAL, true);
    desktopObservable.control().width(Main.screenWidth);
    desktopObservable.control().opacity(1);

    int padding = 15;
    ObservableImage leatherObservable = ObservableImage.create(leatherView, "background/leather.png", BindingMode.BIDIRECTIONAL, false);
    leatherObservable.control().width(sideLengthPixels + padding * 2);
    leatherObservable.control().height(sideLengthPixels + padding * 2);
    leatherObservable.control().layoutX(originLeftPixels - padding);
    leatherObservable.control().layoutY(originTopPixels - padding);
    leatherObservable.control().opacity(1);
    leatherObservable.shadow(true);
  }

  public void begin() {
    new Match(visuals, players).begin();
  }
}
