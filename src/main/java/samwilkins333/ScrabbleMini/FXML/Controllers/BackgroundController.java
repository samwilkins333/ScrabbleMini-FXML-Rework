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
import main.java.samwilkins333.ScrabbleMini.Logic.Control.Match.VisualElements;
import main.java.samwilkins333.ScrabbleMini.Logic.Players.PlayerSchema;
import main.java.samwilkins333.ScrabbleMini.Logic.Players.PlayerType;
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
  private PlayerSchema players = new PlayerSchema(2);

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
    desktopObservable.bindings().width(Main.screenWidth);
    desktopObservable.bindings().opacity(1);

    int padding = 15;
    ObservableImage leatherObservable = ObservableImage.create(leatherView, "background/leather.png", BindingMode.BIDIRECTIONAL, false);
    leatherObservable.bindings().width(sideLengthPixels + padding * 2);
    leatherObservable.bindings().height(sideLengthPixels + padding * 2);
    leatherObservable.bindings().layoutX(originLeftPixels - padding);
    leatherObservable.bindings().layoutY(originTopPixels - padding);
    leatherObservable.bindings().opacity(1);
    leatherObservable.shadow(true);
  }

  public void begin() {
    players.initialize(1, PlayerType.HUMAN);
    players.initialize(2, PlayerType.SIMULATED);
    new Match(visuals, players).begin();
  }
}
