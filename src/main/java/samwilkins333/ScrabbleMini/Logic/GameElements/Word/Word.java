package samwilkins333.ScrabbleMini.Logic.GameElements.Word;

import samwilkins333.ScrabbleMini.Logic.DataStructures.Utility.UtilityArrayList;
import samwilkins333.ScrabbleMini.Logic.GameElements.Tiles.Indices;
import samwilkins333.ScrabbleMini.Logic.GameElements.Tiles.OverlayType;
import samwilkins333.ScrabbleMini.Logic.GameElements.Tiles.TileView;

import java.util.*;

/**
 * An augmented ArrayList of tiles that only
 * logically models a word, or an ordered, axial
 * sequence of tiles.
 */
public class Word extends UtilityArrayList<TileView> {
  private static Map<Axis, Comparator<TileView>> readers = new HashMap<>();

  static {
    readers.put(Axis.HORIZONTAL,
            Comparator.comparingDouble(t -> t.indices().column()));
    readers.put(Axis.VERTICAL,
            Comparator.comparingDouble(t -> t.indices().row()));
  }

  /**
   * Constructor.
   *
   * @param initial any individual initial tiles to include in the word.
   */
  public Word(TileView... initial) {
    super(Arrays.asList(initial));
  }

  /**
   * Constructor.
   *
   * @param c a collection of initial tiles to include in the word.
   */
  public Word(Collection<? extends TileView> c) {
    super(c);
  }

  /**
   * Flashes the requested superimposed overlay
   * for all tiles in the word.
   *
   * @param type the requested overlay
   */
  public void flash(OverlayType type) {
    forEach(tile -> tile.flash(type));
  }

  /**
   * Retrieves the first character in the word,
   * lexigraphically. If the word follows a vertical
   * axis, this is the top tile. If horizontal, the leftmost.
   *
   * @param axis the orientation used to determine first tile
   * @return the lexigraphically / position-based first tile.
   */
  public TileView first(Axis axis) {
    return stream().min(Word.reader(axis)).orElse(get(0));
  }

  /**
   * Retrieves the last character in the word,
   * lexigraphically. If the word follows a vertical
   * axis, this is the bottom tile. If horizontal, the rightmost.
   *
   * @param axis the orientation used to determine last tile
   * @return the lexigraphically / position-based last tile.
   */
  public TileView last(Axis axis) {
    return stream().max(Word.reader(axis)).orElse(get(size() - 1));
  }

  /**
   * Determines whether or not the word contains
   * a tile that has been placed at the given board
   * indices.
   *
   * @param column the target column
   * @param row    the target row
   * @return whether or not the word contains a tile
   * that has been placed at the indices.
   */
  public boolean contains(int column, int row) {
    for (TileView tile : this) {
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
    forEach(t -> word.append(t.getTile().getLetter()));
    return word.toString();
  }

  /**
   * Statically retrieves the comparator that
   * appropriately sorts this internal list, based on
   * this word's orientation. It is assumed that the
   * orientation passed in will have been accurately deduced
   * from Referee.analyzeAxis().
   *
   * @param axis the axis of the word, which influences
   *             the order in which the tiles should be read
   * @return the appropriate comparator
   */
  public static Comparator<TileView> reader(Axis axis) {
    return readers.get(axis);
  }

  /**
   * @return whether any two tiles in this word
   * occupy the same coordinates on the board, or
   * thus if the word overlaps internally.
   */
  public boolean internalOverlap() {
    Set<Indices> indices = new HashSet<>();
    for (TileView tile : this) {
      if (!indices.add(tile.indices())) {
        return true;
      }
    }
    return false;
  }
}
