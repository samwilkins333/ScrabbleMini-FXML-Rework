package main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag;

public class Letter implements Comparable<Letter> {
  private String letter;
  private int score;

  public Letter(String letter, int score) {
    this.letter = letter;
    this.score = score;
  }

  public String raw() {
    return letter;
  }

  public int score() {
    return score;
  }

  @Override
  public int compareTo(Letter o) {
    return letter.compareTo(o.letter);
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Letter)) {
      return false;
    }

    Letter otherLetter = (Letter) other;
    return letter.equals(otherLetter.letter);
  }

  @Override
  public int hashCode() {
    return letter.hashCode();
  }
}
