package main.java.samwilkins333.ScrabbleMini.Logic.Board;

public class BoardScore {
  private final int score;
  private final String word;

  public BoardScore(int score, String word) {
    this.score = score;
    this.word = word;
  }

  public int score() {
    return score;
  }

  public String word() {
    return word;
  }
}
