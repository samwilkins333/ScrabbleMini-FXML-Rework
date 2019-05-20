package main.java.samwilkins333.ScrabbleMini.Logic.Control.Referee;

import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.Players.Player;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.TileBag;

import java.util.List;

public abstract class Referee {
  protected final List<Player> players;
  protected final Board board;
  protected int current;
  protected int moves = 0;

  Referee(List<Player> players, Board board, TileBag tileBag) {
    this.players = players;
    this.board = board;

    players.forEach(p -> p.fillRack(board, tileBag)); // populate racks
    current = (int) (Math.random() * players.size()); //coin toss
  }

  public Player current() {
    return players.get(current);
  }

  private void nextMove() {
    current().setRackVisible(false);
    current = current + 1 % players.size();
    current().setRackVisible(true);
    current().play(board);
  }


  public void evaluateHumanPlacements() {
    if (!validatePlacements()) return;

    nextMove();
  }

  public void clearBoard() {
    board.clearPlacements();
  }

  protected abstract boolean validatePlacements();
}
