package main.java.samwilkins333.ScrabbleMini.Logic.Agents.Players;

import java.util.ArrayList;

/**
 * A specialized <code>ArrayList</code> that maintains
 * an internal state tracking the current player as it
 * cycles through its members and exposes a convenience
 * methods for initialization of players.
 */
public class PlayerList extends ArrayList<Player> {
  private int current = 0;

  /**
   * Constructor.
   * @param capacity the initial capacity
   *                 of the <code>ArrayList</code>
   */
  public PlayerList(int capacity) {
    super(capacity);
  }

  /**
   * Inserts a new player of the specified type
   * into the end of the list.
   * @param playerNumber 1-indexed number of the player
   * @param type the type of the player, used to construct the
   *             appropriate implementor of <code>Player</code>
   */
  public void register(int playerNumber, PlayerType type) {
    add(playerNumber - 1, Player.fromType(type, playerNumber));
  }

  /**
   * @return retrieves the current player in the cycle
   */
  public Player current() {
    return get(current);
  }

  /**
   * Updates the internal state, making the next player in line
   * the current player.
   * @return the *new* current player
   */
  public Player next() {
    current = (current + 1) % size();
    return get(current);
  }
}
