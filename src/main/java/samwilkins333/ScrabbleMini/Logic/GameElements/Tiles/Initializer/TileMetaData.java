package main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Tiles.Initializer;

/**
 * A convenience struct that stores
 * information about a given letter.
 * Appears in a mapping from String letters
 * to TileMetaData.
 */
public class TileMetaData {
  private final int score;
  private final int frequency;

  TileMetaData(int score, int frequency) {
    this.score = score;
    this.frequency = frequency;
  }

  /**
   * @return the score of a tile with this letter
   */
  public int score() {
    return score;
  }

  /**
   * @return the frequency with which tiles of this letter occur
   */
  public int frequency() {
    return frequency;
  }

  @Override
  public String toString() {
    String template = "TileMetaData: Score [%d], Frequency [%d]";
    return String.format(template, score, frequency);
  }
}
