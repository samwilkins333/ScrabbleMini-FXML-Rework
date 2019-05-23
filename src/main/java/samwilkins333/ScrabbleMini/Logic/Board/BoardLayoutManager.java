package main.java.samwilkins333.ScrabbleMini.Logic.Board;

import javafx.geometry.Point2D;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Indices;

/**
 * A utility class that allows other
 * classes to statically mutate and
 * access important information regarding
 * the dimensions, square size and origins
 * of the board layout.
 */
public final class BoardLayoutManager {
  private BoardLayoutManager() {
    //prevents instantiation
  }

  public static double originLeftPixels;
  public static double originTopPixels;
  public static int squarePixels;
  public static double dimensions;
  public static double sideLengthPixels;
  public static double tilePadding;
  public static double tileWidth;

  /**
   * Transforms a column and a row of the board
   * into their pixel equivalent (top left corner
   * of the corresponding square).
   * @param indices the coordinates on the board
   * @return the scene pixel values of the coordinates' top left corner
   */
  public static Point2D toPixels(Indices indices) {
    double sceneXPixels = squarePixels * indices.column();
    double sceneYPixels = squarePixels * indices.row();
    return new Point2D(sceneXPixels, sceneYPixels);
  }

  /**
   * Transforms scene pixels into the column and row
   * that contains the pixel point (may be negative).
   * An inherent floor is applied to the calculation by down casting.
   * @param observedPixels the scene pixels of the input point
   * @return the column and row within which the input point falls
   */
  public static Indices toIndices(Point2D observedPixels) {
    int column = (int) observedPixels.getX() / squarePixels;
    int row = (int) observedPixels.getY()  / squarePixels;
    return new Indices(column, row);
  }
}
