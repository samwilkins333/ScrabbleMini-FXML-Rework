package samwilkins333.ScrabbleMini.Logic.GameAgents.Players;

import com.swilkins.ScrabbleBase.Board.Location.TilePlacement;
import samwilkins333.ScrabbleMini.Logic.GameElements.GameContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Models a human scrabble player. In effect, it is a
 * lightweight wrapper whose <code>move()</code> method
 * just nominally encapsulates the manual dragging and
 * checking of tiles by an actual human player.
 */
public class HumanPlayer<T extends Collection<String>> extends Player<T> {

  /**
   * Constructor.
   */
  public HumanPlayer() {
    super();
  }

  @Override
  public List<TilePlacement> move(GameContext<T> context) {
    return new ArrayList<>();
  }
}
