package main.java.samwilkins333.ScrabbleMini.Logic.Players;

import java.util.ArrayList;

public class PlayerSchema extends ArrayList<Player> {

  public PlayerSchema(int capacity) {
    super(capacity);
  }

  public void initialize(int playerNumber, PlayerType type) {
    add(playerNumber - 1, Player.fromType(type));
  }
}
