package main.java.samwilkins333.ScrabbleMini.FXML.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.BindingMode;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.ObservableImage;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Initializer.BoardReader;
import main.java.samwilkins333.ScrabbleMini.Logic.Control.Match.Match;
import main.java.samwilkins333.ScrabbleMini.Logic.Control.Referee.StandardReferee;
import main.java.samwilkins333.ScrabbleMini.Logic.Players.PlayerList;
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

  private Match match;
  private Board board;
  private TileBag tileBag;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    initializeVisualElements();
    initializeBackground();
    begin();
  }

  private void initializeVisualElements() {
    boardPane.setFocusTraversable(true);
    boardPane.requestFocus();
    boardPane.setOnKeyPressed(e -> { if (match != null) match.notify(e); });

    board = new Board(boardPane, new BoardReader());
    tileBag = new TileBag(tilebagView, new TileBagReader());
  }

  private void initializeBackground() {
    ObservableImage desktopObservable = ObservableImage.initialize(desktopView, "background/desktop.jpg", BindingMode.BIDIRECTIONAL, true);
    desktopObservable.bindings().width(Main.screenWidth);
    desktopObservable.bindings().opacity(1);

    int padding = 15;
    ObservableImage leatherObservable = ObservableImage.initialize(leatherView, "background/leather.png", BindingMode.BIDIRECTIONAL, false);
    leatherObservable.bindings().width(sideLengthPixels + padding * 2);
    leatherObservable.bindings().height(sideLengthPixels + padding * 2);
    leatherObservable.bindings().layoutX(originLeftPixels - padding);
    leatherObservable.bindings().layoutY(originTopPixels - padding);
    leatherObservable.bindings().opacity(1);
    leatherObservable.shadow(true);
  }

  public void begin() {
    PlayerList players = new PlayerList(2);
    players.register(1, PlayerType.HUMAN);
    players.register(2, PlayerType.HUMAN);

    match = new Match(new StandardReferee(players, board, tileBag));
    match.begin();
  }
}
