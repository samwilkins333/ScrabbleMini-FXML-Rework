package main.java.samwilkins333.ScrabbleMini.Logic.Control.Match;

import main.java.samwilkins333.ScrabbleMini.Logic.Control.Referee;
import main.java.samwilkins333.ScrabbleMini.Logic.Players.PlayerSchema;

public class Match {
  private final VisualElements visuals;
  private final PlayerSchema playersSchema;
  private final Referee referee;

  public Match(VisualElements visuals, PlayerSchema players) {
    this.visuals = visuals;
    this.playersSchema = players;
    referee = new Referee();
  }

  public void begin() {
    playersSchema.forEach(p -> p.fillRack(visuals.tileBag(), visuals.board().node().getChildren()));
  }

}
