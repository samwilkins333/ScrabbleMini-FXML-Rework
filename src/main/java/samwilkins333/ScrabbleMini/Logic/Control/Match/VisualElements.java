package main.java.samwilkins333.ScrabbleMini.Logic.Control.Match;

import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.TileBag;

public class VisualElements {
  private final Board board;
  private final TileBag tileBag;

  public VisualElements(Board board, TileBag tileBag) {
    this.board = board;
    this.tileBag = tileBag;
  }

  public Board board() {
    return board;
  }

  public TileBag tileBag() {
    return tileBag;
  }
}
