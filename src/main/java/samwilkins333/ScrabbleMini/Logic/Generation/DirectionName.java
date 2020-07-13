package main.java.samwilkins333.ScrabbleMini.Logic.Generation;

public enum DirectionName {

  UP("up"),
  DOWN("down"),
  LEFT("left"),
  RIGHT("right");

  private final String name;

  DirectionName(final String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
