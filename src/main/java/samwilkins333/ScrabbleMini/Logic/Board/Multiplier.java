package main.java.samwilkins333.ScrabbleMini.Logic.Board;

import java.util.Objects;

public class Multiplier {
  private final double letter;
  private final double word;

  public Multiplier(double letter, double word) {
    this.letter = letter;
    this.word = word;
  }

  public static Multiplier parse(String raw, String delimiter) {
    String[] split = raw.split(delimiter);
    int letterValue = Integer.valueOf(split[0]);
    int wordValue = Integer.valueOf(split[1]);
    return new Multiplier(letterValue, wordValue);
  }

  public double letterValue() {
    return letter;
  }

  public double wordValue() {
    return word;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Multiplier that = (Multiplier) o;
    return Double.compare(that.letter, letter) == 0 &&
            Double.compare(that.word, word) == 0;
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
