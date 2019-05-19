package main.java.samwilkins333.ScrabbleMini.Logic.Control.Match;

import main.java.samwilkins333.ScrabbleMini.Logic.Players.HumanPlayer;
import main.java.samwilkins333.ScrabbleMini.Logic.Players.Player;
import main.java.samwilkins333.ScrabbleMini.Logic.Players.PlayerType;
import main.java.samwilkins333.ScrabbleMini.Logic.Players.SimulatedPlayer;
import main.java.samwilkins333.ScrabbleMini.Main;

public class PlayerSchema {
  private Player[] players;

  public void create(PlayerType type) {
    Player player;
    switch (type) {
      case HUMAN:
        player = new HumanPlayer();
        break;
      case SIMULATED:
        player = new SimulatedPlayer();
      default:
        Main.exit(String.format("Attempted to create a player of a nonexistent type, %s.", type));
    }
  }

}
