package main.java.samwilkins333.ScrabbleMini.Logic.Generation;

import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Board.Multiplier;

public class BoardStateUnit {

  private final Multiplier multiplier;
  private final Tile tile;

  public BoardStateUnit(Multiplier multiplier, Tile tile) {
    this.multiplier = multiplier;
    this.tile = tile;
  }

  public Multiplier getMultiplier() {
    return multiplier;
  }

  public Tile getTile() {
    return tile;
  }

}
