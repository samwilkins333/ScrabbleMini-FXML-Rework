package main.java.samwilkins333.ScrabbleMini.Logic.Tiles;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Indices indices = (Indices) o;
    return column == indices.column &&
            row == indices.row;
  }

  @Override
  public int hashCode() {
    return Objects.hash(column, row);
  }
}
