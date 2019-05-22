package main.java.samwilkins333.ScrabbleMini.Logic.Control.Referee;

import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.Players.Player;
import main.java.samwilkins333.ScrabbleMini.Logic.Rack.RackLayoutManager;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.OverlayType;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.TileBag;
import main.java.samwilkins333.ScrabbleMini.Logic.Word.Orientation;
import main.java.samwilkins333.ScrabbleMini.Logic.Word.Word;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class Referee {
  protected final List<Player> players;
  protected final Board board;
  private final TileBag tileBag;
  protected int current = 0;
  protected int moves = 0;

  Referee(List<Player> players, Board board, TileBag tileBag) {
    this.players = players;
    this.board = board;
    this.tileBag = tileBag;

    players.forEach(p -> p.fillRack(board, tileBag)); // populate racks
//    current = (int) (Math.random() * players.size()); //coin toss
  }

  public Player current() {
    return players.get(current);
  }

  private void nextMove() {
    moves++;
    current().setRackVisible(false);
    current = (current + 1) % players.size();
    current().setRackVisible(true);

    Executors.newScheduledThreadPool(1).schedule(() -> {
      current().fillRack(board, tileBag);
      current().play(board);
    }, 3, TimeUnit.SECONDS);
  }

  public void evaluateHumanPlacements() {
    Word word = board.placements();

    // ensure we have a non-empty...
    if (word.isEmpty()) return;

    // ...properly oriented and appropriately complete (no gaps) word
    Orientation orientation;
    if ((orientation = analyzeOrientation(word)) == Orientation.UNDEFINED || !board.complete(word, orientation)) {
      return;
    }

    // order the tiles from left to right or top to bottom ('read' them)
    word.sort(Word.reader(orientation));

    List<Word> crosses = board.crosses(word, orientation.invert());
    boolean allValid = true;
    boolean anyValid = false;

    if (!crosses.isEmpty()) {
      for (Word cross: crosses) {
        boolean valid = isValid(cross);
        allValid &= valid;
        anyValid |= valid;
        cross.flash(valid ? OverlayType.SUCCESS : OverlayType.FAILURE);
      }
      if (!allValid) {
        word.flash(anyValid ? OverlayType.INVALID : OverlayType.FAILURE);
        return;
      }
    }

    if (!isValid(word)) {
      word.flash(OverlayType.FAILURE);
    } else if (!isPositioned(word, orientation)) {
      word.flash(OverlayType.INVALID);
    } else {
      word.forEach(tile -> board.play(current().transfer(tile)));
      current().increment(board.score(word, true));
      crosses.forEach(cross -> current().increment(board.score(cross, true)));
      nextMove();
    }
  }

  public void resetBoard() {
    board.resetPlacements();
  }

  protected abstract Orientation analyzeOrientation(Word placements);

  protected abstract boolean isPositioned(Word placements, Orientation orientation);

  protected abstract boolean isValid(Word word);
}
