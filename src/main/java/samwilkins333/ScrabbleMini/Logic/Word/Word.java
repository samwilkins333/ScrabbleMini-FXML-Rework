package main.java.samwilkins333.ScrabbleMini.Logic.Word;

import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Indices;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.OverlayType;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Tile;

import java.util.*;

/**
 * An augmented ArrayList of tiles that only
 * logically models a word, or an ordered, axial
 * sequence of tiles.
 */
public class Word extends ArrayList<Tile> {
  private static Map<Axis, Comparator<Tile>> readers = new HashMap<>();
  static {
    readers.put(Axis.HORIZONTAL, Comparator.comparingDouble(t -> t.indices().column()));
    readers.put(Axis.VERTICAL, Comparator.comparingDouble(t -> t.indices().row()));
  }

  public Word(Tile... initial) {
    super(Arrays.asList(initial));
  }

  public Word(Collection<? extends Tile> c) {
    super(c);
  }

  public void flash(OverlayType type) {
    forEach(tile -> tile.flash(type));
  }

  public Tile first(Axis axis) {
    return stream().min(Word.reader(axis)).orElse(get(0));
  }

  public Tile last(Axis axis) {
    return stream().max(Word.reader(axis)).orElse(get(size() - 1));
  }

  public boolean contains(int column, int row) {
    for (Tile tile : this) {
      int c = tile.indices().column();
      int r = tile.indices().row();
      if (column == c && row == r) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    StringBuilder word = new StringBuilder();
    forEach(t -> word.append(t.letter()));
    return word.toString();
  }

  public static Comparator<Tile> reader(Axis axis) {
    return readers.get(axis);
  }

  public boolean internalOverlap() {
    Set<Indices> indices = new HashSet<>();
    for (Tile tile : this) {
      if (!indices.add(tile.indices())) {
        return true;
      }
    }
    return false;
  }
}
