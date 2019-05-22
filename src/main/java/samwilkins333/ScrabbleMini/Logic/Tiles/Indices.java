package main.java.samwilkins333.ScrabbleMini.Logic.Tiles;

public class Indices {
  private final int column;
  private final int row;

  public Indices(int column, int row) {
    this.column = column;
    this.row = row;
  }

  public int column() {
    return column;
  }

  public int row() {
    return row;
  }
}
