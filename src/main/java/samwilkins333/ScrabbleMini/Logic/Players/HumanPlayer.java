package main.java.samwilkins333.ScrabbleMini.Logic.Players;

import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;

public class HumanPlayer extends Player {

  HumanPlayer(int playerNumber) {
    super(PlayerType.HUMAN, playerNumber);
  }

  @Override
  public void play(Board board) {
    board.resetPlacements();
  }

}
