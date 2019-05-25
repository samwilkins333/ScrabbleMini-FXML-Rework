package main.java.samwilkins333.ScrabbleMini.Logic.GameAgents.Players;

import main.java.samwilkins333.ScrabbleMini.Logic.Computation.Context;
import main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag.GADDAG;

/**
 * Models a human scrabble player. In effect, it is a
 * lightweight wrapper whose <code>move()</code> method
 * just nominally encapsulates the manual dragging and
 * checking of tiles by an actual human player.
 */
public class HumanPlayer extends Player<GADDAG> {

  /**
   * Constructor.
   */
  public HumanPlayer() {
    super();
  }

  @Override
  public void move(Context<GADDAG> context) {
    context.board().resetPlacements();
  }
}
