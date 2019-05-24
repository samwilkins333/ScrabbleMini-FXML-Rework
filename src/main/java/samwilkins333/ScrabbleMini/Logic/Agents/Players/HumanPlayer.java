package main.java.samwilkins333.ScrabbleMini.Logic.Agents.Players;

import main.java.samwilkins333.ScrabbleMini.Logic.Elements.Board.Board;

/**
 * Models a human scrabble player. In effect, it is a
 * lightweight wrapper whose <code>move()</code> method
 * just nominally encapsulates the manual dragging and
 * checking of tiles by an actual human player.
 */
public class HumanPlayer extends Player {

  HumanPlayer(int playerNumber) {
    super(PlayerType.HUMAN, playerNumber);
  }

  @Override
  public void move(Board board) {
    board.resetPlacements();
  }

}
