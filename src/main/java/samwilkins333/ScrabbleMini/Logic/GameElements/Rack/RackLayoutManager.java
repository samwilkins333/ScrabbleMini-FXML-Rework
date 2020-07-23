package main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Rack;

/**
 * A utility class that allows other
 * classes to statically mutate and
 * access important information regarding
 * the dimensions, square size and origins
 * of the rack layout.
 */
public final class RackLayoutManager {
  private RackLayoutManager() {
    //prevents instantiation
  }

  public static double originTopPixels;
  public static double leftOriginLeftPixels;
  public static double rightOriginLeftPixels;
}
