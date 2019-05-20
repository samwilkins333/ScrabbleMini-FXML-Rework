package main.java.samwilkins333.ScrabbleMini.Logic.Control.Referee;

import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.Players.Player;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.TileBag;

import java.util.List;

public class StandardReferee extends Referee {

  public StandardReferee(List<Player> players, Board board, TileBag tileBag) {
    super(players, board, tileBag);
  }

  @Override
  protected boolean validatePlacements() {
    return true;
  }


}
