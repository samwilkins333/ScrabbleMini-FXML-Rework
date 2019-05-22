package main.java.samwilkins333.ScrabbleMini.Logic.Word;

public enum Orientation {
  HORIZONTAL,
  VERTICAL,
  UNDEFINED;

  public Orientation invert() {
    assert this != Orientation.UNDEFINED;
    if (this == Orientation.VERTICAL) return Orientation.HORIZONTAL;
    return Orientation.VERTICAL;
  }
}
