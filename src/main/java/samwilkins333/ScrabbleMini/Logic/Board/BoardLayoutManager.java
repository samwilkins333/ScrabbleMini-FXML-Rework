package main.java.samwilkins333.ScrabbleMini.Logic.Board;

import javafx.geometry.Point2D;

public final class BoardLayoutManager {
  public static double originLeftPixels;
  public static double originTopPixels;
  public static int squareSidePixels;
  public static double dimensions;
  public static double sideLengthPixels;
  private static int rackSize = 7;

  public static Point2D toPixels(int column, int row) {
    double sceneXPixels = originLeftPixels + squareSidePixels * column;
    double sceneYPixels = originTopPixels + squareSidePixels * row;
    return new Point2D(sceneXPixels, sceneYPixels);
  }

  public static Point2D toIndices(double sceneXPixels, double sceneYPixels) {
    double column = (int) (sceneXPixels - originLeftPixels) / squareSidePixels;
    double row = (int) (sceneYPixels - originTopPixels) / squareSidePixels;
    return new Point2D(column, row);
  }

  public final class LeftRack {
    public double originLeftPixels() { return BoardLayoutManager.originLeftPixels - squareSidePixels; }
    public double originTopPixels() { return BoardLayoutManager.originTopPixels + ((dimensions - rackSize) * squareSidePixels) / 2; }
  }

  public final class RightRack {
    public double originLeftPixels = BoardLayoutManager.originLeftPixels + sideLengthPixels + squareSidePixels;
    public double originTopPixels() { return BoardLayoutManager.originTopPixels + ((dimensions - rackSize) * squareSidePixels) / 2; }
  }
}
