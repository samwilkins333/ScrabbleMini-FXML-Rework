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
  protected final TileBag tileBag;
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

    // ...properly oriented word
    Orientation orientation;
    if ((orientation = analyzeOrientation(word)) == Orientation.UNDEFINED || !board.complete(word, orientation)) {
      return;
    }

    // order the tiles from left to right or top to bottom ('read' them)
    word.sort(Word.reader(orientation));

    List<Word> crosses = board.crosses(word, orientation.invert());
    if (!crosses.isEmpty()) {
      if (!crosses.stream().allMatch(this::isValid)) {
        word.flash(OverlayType.FAILURE);
        return;
      }
      crosses.forEach(c -> c.flash(OverlayType.SUCCESS));
    }

    if (!isValid(word)) {
      word.flash(OverlayType.FAILURE);
    } else if (!isPositioned(word, orientation)) {
      word.flash(OverlayType.INVALID);
    } else {
      // SUCCESS
      // remove from rack (transfer) and play on board (which removes from placement, if present)
      word.forEach(tile -> board.play(current().transfer(tile)));
      current().increment(board.score(word));
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
