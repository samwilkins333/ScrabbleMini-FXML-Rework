package main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag;

public class ArcLetter implements Comparable<ArcLetter> {
  private final Letter letter;

  public ArcLetter(Letter letter) {
    this.letter = letter;
  }

  public Letter letter() {
    return letter;
  }

  @Override
  public int compareTo(ArcLetter other) {
    boolean selfDelim = this instanceof Delimiter;
    boolean otherDelim = other instanceof Delimiter;

    if (!(selfDelim || otherDelim)) {
      return letter.compareTo(other.letter);
    }
    if (otherDelim && !selfDelim) {
      return -1;
    }
    if (!otherDelim) {
      return 1;
    }
    return 0;
  }
}
