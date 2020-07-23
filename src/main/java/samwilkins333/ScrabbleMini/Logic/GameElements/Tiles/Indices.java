package main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Tiles;

import java.util.Objects;

/**
 * A simple convenience struct that stores
 * the column and row of a location on the
 * <code>Board</code>.
 */
public class Indices {
  private final int column;
  private final int row;

  /**
   * Constructor.
   *
   * @param column the column of the location
   * @param row    the row of the location
   */
  public Indices(int column, int row) {
    this.column = column;
    this.row = row;
  }

  /**
   * @return the location's column
   */
  public int column() {
    return column;
  }

  /**
   * @return the location's row
   */
  public int row() {
    return row;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Indices indices = (Indices) o;
    return column == indices.column
            && row == indices.row;
  }

  @Override
  public int hashCode() {
    return Objects.hash(column, row);
  }
}
