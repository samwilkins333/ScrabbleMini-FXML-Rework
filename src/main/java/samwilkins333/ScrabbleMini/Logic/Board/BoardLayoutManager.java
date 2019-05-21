package main.java.samwilkins333.ScrabbleMini.Logic.Board;

import javafx.geometry.Point2D;

public final class BoardLayoutManager {
  public static double originLeftPixels;
  public static double originTopPixels;
  public static int squareSidePixels;
  public static double dimensions;
  public static double sideLengthPixels;
  public static double tilePadding;
  public static double tileWidth;
  private static int rackSize = 7;

  public static Point2D toPixels(Point2D coordinates) {
    double sceneXPixels = squareSidePixels * coordinates.getX();
    double sceneYPixels = squareSidePixels * coordinates.getY();
    return new Point2D(sceneXPixels, sceneYPixels);
  }

  public static Point2D toIndices(Point2D observedPixels) {
    double column = (int) observedPixels.getX() / squareSidePixels;
    double row = (int) observedPixels.getY()  / squareSidePixels;
    return new Point2D(column, row);
  }

  public static double originTopPixelsLeftRack() { return ((dimensions - rackSize) * squareSidePixels) / 2; }

  public static double originLeftPixelsRightRack() { return originLeftPixels + sideLengthPixels + squareSidePixels; }
  public static double originTopPixelsRightRack() { return originTopPixels + ((dimensions - rackSize) * squareSidePixels) / 2; }
}
