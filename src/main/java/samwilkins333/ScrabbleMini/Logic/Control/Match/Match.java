package main.java.samwilkins333.ScrabbleMini.Logic.Control.Match;

import main.java.samwilkins333.ScrabbleMini.Logic.Control.Referee;

public class Match {
  private final VisualElements visuals;
  private final PlayerSchema players;
  private final Referee referee;

  public Match(VisualElements visuals, PlayerSchema players) {
    this.visuals = visuals;
    this.players = players;
    referee = new Referee();
  }

  public void begin() {

  }
}
