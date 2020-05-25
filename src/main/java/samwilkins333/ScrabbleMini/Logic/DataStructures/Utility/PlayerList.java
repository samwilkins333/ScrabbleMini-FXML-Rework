package main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Utility;

import main.java.samwilkins333.ScrabbleMini.Logic.GameAgents.Players.Player;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A specialized <code>ArrayList</code> that maintains
 * an internal state tracking the current player as it
 * cycles through its members and exposes a convenience
 * methods for initialization of players.
 * @param <T> the context containing the type of data structure that
 *           holds the entire vocabulary of valid words in the game
 */
public class PlayerList<T extends Collection<String>>
        extends ArrayList<Player<T>> {
  private int current = 0;

  /**
   * Constructor.
   * @param capacity the initial capacity
   *                 of the <code>ArrayList</code>
   */
  public PlayerList(int capacity) {
    super(capacity);
  }

  @Override
  public boolean add(Player<T> player) {
    player.setPlayerNumber(size() + 1);
    return super.add(player);
  }

  /**
   * @return retrieves the current player in the cycle
   */
  public Player<T> current() {
    return get(current);
  }

  /**
   * Updates the internal state, making the next player in line
   * the current player.
   * @return the *new* current player
   */
  public Player<T> next() {
    current = (current + 1) % size();
    return get(current);
  }

  /**
   * Updates the internal state, making the previous player in line
   * the current player.
   * @return the *new* current player
   */
  public Player<T> previous() {
    current = (current > 0 ? current : size()) - 1;
    return get(current);
  }
}
