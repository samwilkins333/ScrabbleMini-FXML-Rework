package main.java.samwilkins333.ScrabbleMini.Logic.Control.Match;

import javafx.scene.input.KeyEvent;
import main.java.samwilkins333.ScrabbleMini.Logic.Control.Referee.Referee;
import main.java.samwilkins333.ScrabbleMini.Logic.Players.HumanPlayer;

public class Match {
  private final Referee referee;

  public Match(Referee referee) {
    this.referee = referee;
  }

  public void begin() {
    referee.current().setRackVisible(true);
  }

  public void notify(KeyEvent e) {
    if (!(referee.current() instanceof HumanPlayer)) return;
    switch (e.getCode()) {
      case ENTER:
        if (e.isMetaDown()) referee.evaluateHumanPlacements();
        break;
      case ESCAPE:
        referee.resetBoard();
        break;
    }
  }
}
