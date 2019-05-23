package main.java.samwilkins333.ScrabbleMini.Logic.Control.Referee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.TransitionHelper;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.Players.HumanPlayer;
import main.java.samwilkins333.ScrabbleMini.Logic.Players.Player;
import main.java.samwilkins333.ScrabbleMini.Logic.Players.PlayerList;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.TileBag;
import main.java.samwilkins333.ScrabbleMini.Logic.Word.Orientation;
import main.java.samwilkins333.ScrabbleMini.Logic.Word.Word;

import java.util.List;

import static main.java.samwilkins333.ScrabbleMini.Logic.Tiles.OverlayType.*;

public abstract class Referee {
  private static final double DELAY = 0.55;
  protected final PlayerList players;
  protected final Board board;
  private final TileBag tileBag;
  protected int moves = 0;

  Referee(PlayerList players, Board board, TileBag tileBag) {
    this.players = players;
    this.board = board;
    this.tileBag = tileBag;

    players.forEach(p -> p.fillRack(board, tileBag));
    players.forEach(p -> p.setRackVisible(p == players.current()));
  }

  protected abstract Orientation analyzeOrientation(Word placements);

  protected abstract boolean isPositioned(Word placements, Orientation orientation);

  protected abstract boolean isValid(Word word);

  public void notify(KeyEvent e) {
    if (!(players.current() instanceof HumanPlayer)) return;
    switch (e.getCode()) {
      case ENTER: if (e.isMetaDown()) evaluateHumanPlacements(); break;
      case ESCAPE: board.resetPlacements(); break;
      case SPACE: players.current().shuffleRack(board); break;
    }
  }

  private void nextMove() {
    moves++;
    Player current = players.current();
    current.setRackVisible(false);
    current = players.next();
    current.setRackVisible(true);
    current.fillRack(board, tileBag);
    current.move(board);
  }

  private void evaluateHumanPlacements() {
    Word word = board.placements();

    // ensure we have a non-empty...
    if (word.isEmpty() || word.internalOverlap()) return;

    // ...properly oriented and appropriately complete (no gaps) word
    Orientation orientation;
    if ((orientation = analyzeOrientation(word)) == Orientation.UNDEFINED) return;

    boolean wordPositioned = isPositioned(word, orientation);
    if (!board.complete(word, orientation)) return;

    // order the tiles from left to right or top to bottom ('read' them)
    word.sort(Word.reader(orientation));
    boolean wordValid = isValid(word);

    List<Word> crosses = board.crosses(word, orientation.invert());
    boolean allValid = true;

    if (!crosses.isEmpty()) {
      for (int i = 0; i < crosses.size(); i++) {
        Word cross = crosses.get(i);
        boolean crossValid = isValid(cross);
        allValid &= crossValid;
        EventHandler<ActionEvent> flash = e -> cross.flash(crossValid ? (wordValid ? SUCCESS : INVALID) : FAILURE);
        TransitionHelper.pause((i + 1) * DELAY, flash).play();
      }
      if (!allValid) {
        word.flash(wordValid ? INVALID : FAILURE);
        return;
      }
    }

    if (!wordValid) {
      word.flash(FAILURE);
    } else if (!wordPositioned) {
      word.flash(INVALID);
    } else {
      Player current = players.current();
      if (crosses.isEmpty()) crosses.add(word);
      else crosses.add(0, word);
      word.forEach(tile -> board.play(current.transfer(tile)));
      crosses.forEach(cross -> current.apply(board.score(cross, true)));
      nextMove();
    }
  }
}
