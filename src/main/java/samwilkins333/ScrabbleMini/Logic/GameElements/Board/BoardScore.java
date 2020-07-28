package samwilkins333.ScrabbleMini.Logic.GameElements.Board;

/**
 * A utility struct that ensures gives the caller
 * of a scored word both the moves and the <code>String</code>
 * version, as both are commonly needed in tandem.
 */
public class BoardScore {
  private final int score;
  private final String word;

  BoardScore(int score, String word) {
    this.score = score;
    this.word = word;
  }

  /**
   * @return the numeric moves associated
   * with this word
   */
  public int score() {
    return score;
  }

  /**
   * @return the String representation of the
   * scored word
   */
  public String word() {
    return word;
  }
}
