package main.java.samwilkins333.ScrabbleMini.Logic.Players;

import java.util.ArrayList;

public class PlayerList extends ArrayList<Player> {
  private int current = 0;

  public PlayerList(int capacity) {
    super(capacity);
  }

  public void register(int playerNumber, PlayerType type) {
    add(playerNumber - 1, Player.fromType(type, playerNumber));
  }

  public Player current() {
    return get(current);
  }
}
