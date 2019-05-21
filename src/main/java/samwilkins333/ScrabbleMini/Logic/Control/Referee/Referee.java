package main.java.samwilkins333.ScrabbleMini.Logic.Control.Referee;

import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.Players.Player;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.OverlayType;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.TileBag;
import main.java.samwilkins333.ScrabbleMini.Logic.Word.Word;

import java.util.List;

public abstract class Referee {
  protected final List<Player> players;
  protected final Board board;
  protected int current = 0;
  protected int moves = 0;

  Referee(List<Player> players, Board board, TileBag tileBag) {
    this.players = players;
    this.board = board;

    players.forEach(p -> p.fillRack(board, tileBag)); // populate racks
//    current = (int) (Math.random() * players.size()); //coin toss
  }

  public Player current() {
    return players.get(current);
  }

  private void nextMove() {
    moves++;
    current().setRackVisible(false);
    current = current + 1 % players.size();
    current().setRackVisible(true);
    current().play(board);
  }

  public void evaluateHumanPlacements() {
    Word word = board.placements();

    if (word.isEmpty() || !isFormatted(word) || !isValid(word)) {
      word.flash(OverlayType.FAILURE);
    } else if (!isPositioned(word)) {
      word.flash(OverlayType.INVALID);
    } else {
      // remove from rack (transfer) and play on board (which removes from placement, if present)
      word.forEach(tile -> board.play(current().transfer(tile)));
      current().increment(board.score(word));
      nextMove();
    }
  }

  public void resetBoard() {
    board.resetPlacements();
  }

  protected abstract boolean isFormatted(Word placements);

  protected abstract boolean isPositioned(Word placements);

  protected abstract boolean isValid(Word word);
}
