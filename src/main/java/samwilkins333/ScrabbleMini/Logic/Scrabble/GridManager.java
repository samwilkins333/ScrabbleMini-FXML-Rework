package main.java.samwilkins333.ScrabbleMini.Logic.Scrabble;

import javafx.geometry.Point2D;

import static main.java.samwilkins333.ScrabbleMini.Logic.Scrabble.Constants.*;
import static main.java.samwilkins333.ScrabbleMini.Logic.Scrabble.Constants.GRID_FACTOR;

public final class GridManager {

  private GridManager() { }

  static int columnToPixels(int column) {
    return ORIGIN_LEFT + column * GRID_FACTOR;
  }

  static int pixelsToColumn(int xValue) {
    return (xValue - ORIGIN_LEFT) / GRID_FACTOR;
  }

  static int rowToPixels(int row) {
    return ORIGIN_TOP + row * GRID_FACTOR;
  }

  static int pixelsToRow(int yValue) {
    return (yValue - ORIGIN_TOP) / GRID_FACTOR;

  }

  static Point2D gridToPixels(int col, int row) {
    return new Point2D(
            columnToPixels(col),
            rowToPixels(row)
    );
  }

  static Point2D pixelsToGrid(Point2D point) {
    return new Point2D(
            pixelsToColumn((int) point.getX()),
            pixelsToRow((int) point.getY())
    );
  }

}
