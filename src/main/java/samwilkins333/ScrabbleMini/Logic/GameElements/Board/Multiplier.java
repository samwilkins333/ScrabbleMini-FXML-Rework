package main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Board;

import java.util.Objects;

/**
 * Models a multiplier for a scrabble <code>Board</code> square.
 * Consists of a multiplier for both the given raw and the overall
 * given word.
 */
public class Multiplier {
  private final double letter;
  private final double word;

  /**
   * Constructor.
   * @param letter the numeric raw multiplier
   * @param word the numeric word multiplier
   */
  Multiplier(double letter, double word) {
    this.letter = letter;
    this.word = word;
  }

  /**
   * A utility constructor that is used to split
   * two numbers along the specified delimiter
   * and create a <code>Multiplier</code> using
   * the two values.
   * @param raw the String to parse
   * @param delimiter the delimiter separating
   *                  the two numbers
   * @return an <code>Multiplier</code> whose raw multiplier
   * is the first number, and whose word is the second
   */
  public static Multiplier parse(String raw, String delimiter) {
    String[] split = raw.split(delimiter);
    int letterValue = Integer.parseInt(split[0]);
    int wordValue = Integer.parseInt(split[1]);
    return new Multiplier(letterValue, wordValue);
  }

  /**
   * @return the numeric raw multiplier
   */
  double letterValue() {
    return letter;
  }

  /**
   * @return the numeric word multiplier
   */
  double wordValue() {
    return word;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Multiplier that = (Multiplier) o;
    return Double.compare(that.letter, letter) == 0
            && Double.compare(that.word, word) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(letter, word);
  }

  @Override
  public String toString() {
    return String.format("Multiplier: Letter [%f], Word [%f]", letter, word);
  }
}
