package samwilkins333.ScrabbleMini.FXML.Controllers;

import com.swilkins.ScrabbleBase.Vocabulary.PermutationTrie;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.BindingMode;
import samwilkins333.ScrabbleMini.FXML.Utilities.Image.ObservableImage;
import samwilkins333.ScrabbleMini.Logic.DataStructures.Utility.PlayerList;
import samwilkins333.ScrabbleMini.Logic.GameAgents.Players.SimulatedPlayer;
import samwilkins333.ScrabbleMini.Logic.GameAgents.Referee.Referee;
import samwilkins333.ScrabbleMini.Logic.GameAgents.Referee.StandardReferee;
import samwilkins333.ScrabbleMini.Logic.GameElements.Board.Board;
import samwilkins333.ScrabbleMini.Logic.GameElements.Board.Initializer.BoardReader;
import samwilkins333.ScrabbleMini.Logic.GameElements.Tiles.Initializer.TileBagReader;
import samwilkins333.ScrabbleMini.Logic.GameElements.Tiles.TileBag;
import samwilkins333.ScrabbleMini.Main;

import java.net.URL;
import java.util.ResourceBundle;

import static samwilkins333.ScrabbleMini.Logic.GameElements.Board.BoardLayoutManager.*;

/**
 * A controller that acts as the root pane in the JavaFX hierarchy.
 * Here, the referee is created to initialize the match.
 */
public class BackgroundController implements Initializable {
  private static final int LEATHER_PADDING = 15;
  @FXML
  public ImageView desktopView;
  @FXML
  public ImageView leatherView;
  @FXML
  public ImageView tilebagView;
  @FXML
  public Pane boardPane;

  private Referee<PermutationTrie> referee;
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
    boardPane.setOnKeyPressed(e -> {
      if (referee != null) {
        referee.notify(e);
      }
    });

    board = new Board(boardPane, new BoardReader());
    tileBag = new TileBag(tilebagView, new TileBagReader());
  }

  private void initializeBackground() {
    ObservableImage desktopObservable = ObservableImage.initialize(
            desktopView,
            "background/desktop.jpg",
            BindingMode.BIDIRECTIONAL,
            true
    );
    desktopObservable.bindings().width(Main.screenWidth);
    desktopObservable.bindings().opacity(1);

    ObservableImage leatherObservable = ObservableImage.initialize(
            leatherView,
            "background/leather.png",
            BindingMode.BIDIRECTIONAL,
            false
    );
    leatherObservable.bindings().width(sideLengthPixels + LEATHER_PADDING * 2);
    leatherObservable.bindings().height(sideLengthPixels + LEATHER_PADDING * 2);
    leatherObservable.bindings().layoutX(originLeftPixels - LEATHER_PADDING);
    leatherObservable.bindings().layoutY(originTopPixels - LEATHER_PADDING);
    leatherObservable.bindings().opacity(1);
    leatherObservable.shadow(true);
  }

  /**
   * Initializes the referee / match based on GUI-entered player
   * information.
   */
  public void begin() {
    PlayerList<PermutationTrie> players = new PlayerList<>(2);
    players.add(new SimulatedPlayer());
    players.add(new SimulatedPlayer());
    referee = new StandardReferee<>(players, board, tileBag, () -> PermutationTrie.loadFrom(getClass().getResource("/configuration/ospd4.txt")));
  }
}
