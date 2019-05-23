package main.java.samwilkins333.ScrabbleMini.Logic.Players;

import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;

/**
 * Models an artificially intelligent player
 * computing all possible moves with a GADDAG
 * and applying a heuristic for move selection.
 */
public class SimulatedPlayer extends Player {

  SimulatedPlayer(int playerNumber) {
    super(PlayerType.SIMULATED, playerNumber);
  }

  @Override
  public void move(Board board) {

  }

}
