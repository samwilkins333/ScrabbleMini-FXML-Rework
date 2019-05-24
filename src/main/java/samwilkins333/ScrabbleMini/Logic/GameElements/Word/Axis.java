package main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Word;

/**
 * Enumerates the possible axes of a word
 * placed on the board, including the case
 * where the word is not aligned on any axis.
 */
public enum Axis {
  HORIZONTAL,
  VERTICAL,
  UNDEFINED;

  /**
   * @return the axis perpendicular to the current enumeration instance,
   * which cannot be axially UNDEFINED.
   */
  public Axis perpendicular() {
    assert this != Axis.UNDEFINED;
    return this == Axis.VERTICAL ? Axis.HORIZONTAL : Axis.VERTICAL;
  }
}
