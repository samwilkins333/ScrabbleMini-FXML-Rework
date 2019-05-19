package main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Initializer;

public class TileMetaData {
  private final int score;
  private final int frequency;

  TileMetaData(int score, int frequency) {
    this.score = score;
    this.frequency = frequency;
  }

  public int score() {
    return score;
  }

  public int frequency() {
    return frequency;
  }

  @Override
  public String toString() {
    return String.format("TileMetaData: Score [%d], Frequency [%d]", score, frequency);
  }
}
