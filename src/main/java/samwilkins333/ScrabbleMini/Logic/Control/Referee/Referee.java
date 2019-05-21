package main.java.samwilkins333.ScrabbleMini.Logic.Control.Referee;

import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.Control.Referee.Initializer.DictionaryInitializer;
import main.java.samwilkins333.ScrabbleMini.Logic.Players.Player;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.OverlayType;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.TileBag;
import main.java.samwilkins333.ScrabbleMini.Logic.Word.Word;

import java.util.List;
import java.util.Set;

public abstract class Referee {
  protected final List<Player> players;
  protected final Board board;
  protected int current = 0;
  protected int moves = 0;
  protected Set<String> dictionary;

  Referee(List<Player> players, Board board, TileBag tileBag, DictionaryInitializer initializer) {
    this.players = players;
    this.board = board;

    players.forEach(p -> p.fillRack(board, tileBag)); // populate racks
//    current = (int) (Math.random() * players.size()); //coin toss

    dictionary = initializer.initialize();
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
    Word placements = board.placements();

    if (placements.isEmpty() || !isFormatted(placements) || !dictionary.contains(placements.toString())) {
      placements.flash(OverlayType.FAILURE);
      return;
    }

    if (!isPositioned(placements)) {
      placements.flash(OverlayType.INVALID);
      return;
    }

    placements.forEach(tile -> {
      current().transfer(tile);
      board.play(tile);
    });
    board.clearPlacements();

    nextMove();
  }

  public void resetBoard() {
    board.resetPlacements();
  }

  protected abstract boolean isFormatted(Word placements);

  protected abstract boolean isPositioned(Word placements);
}
