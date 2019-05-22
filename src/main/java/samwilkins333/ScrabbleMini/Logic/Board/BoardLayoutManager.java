package main.java.samwilkins333.ScrabbleMini.Logic.Board;

import javafx.geometry.Point2D;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Indices;

public final class BoardLayoutManager {
  public static double originLeftPixels;
  public static double originTopPixels;
  public static int squareSidePixels;
  public static double dimensions;
  public static double sideLengthPixels;
  public static double tilePadding;
  public static double tileWidth;

  public static Point2D toPixels(Indices indices) {
    double sceneXPixels = squareSidePixels * indices.column();
    double sceneYPixels = squareSidePixels * indices.row();
    return new Point2D(sceneXPixels, sceneYPixels);
  }

  public static Indices toIndices(Point2D observedPixels) {
    int column = (int) observedPixels.getX() / squareSidePixels;
    int row = (int) observedPixels.getY()  / squareSidePixels;
    return new Indices(column, row);
  }
}
