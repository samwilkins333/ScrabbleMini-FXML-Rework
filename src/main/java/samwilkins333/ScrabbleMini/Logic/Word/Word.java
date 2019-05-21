package main.java.samwilkins333.ScrabbleMini.Logic.Word;

import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.OverlayType;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Tile;

import java.util.ArrayList;
import java.util.Collection;

public class Word extends ArrayList<Tile> {
  private Orientation orientation = Orientation.UNDEFINED;

  public Word(Collection<? extends Tile> c) {
    super(c);
  }

  public void flash(OverlayType type) {
    forEach(tile -> tile.flash(type));
  }

  public Orientation orientation() {
    return orientation;
  }

  public void orientation(Orientation orientation) {
    this.orientation = orientation;
  }

  @Override
  public String toString() {
    StringBuilder word = new StringBuilder();
    forEach(t -> word.append(t.letter()));
    return word.toString();
  }
}
