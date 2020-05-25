package main.java.samwilkins333.ScrabbleMini.Logic.Computation;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Tiles.Tile;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Word.Word;

/**
 * A Scrabble move, consisting of a set of tiles to be placed at specified board
 * positions.
 *
 * @author Philip Puryear
 */
public class Move {
  private boolean across;
  private int rowOrCol;
  private Map<Integer, Tile> tileMap;

  /**
   * Creates a new move with no tiles.
   *
   * @param across True if the move is <em>across</em>, false if the move is
   *          <em>down</em>.
   * @param rowOrCol The move's row if it is an <em>across</em> move, or the
   *          move's column if it is a <em>down</em> move.
   */
  public Move(boolean across, int rowOrCol) {
    this.across = across;
    this.rowOrCol = rowOrCol;
    tileMap = new TreeMap<>();
  }

  /**
   * Returns true if this move is <em>across</em>, or false if it is
   * <em>down</em>.
   */
  public boolean isAcross() {
    return across;
  }

  /**
   * Returns this move's row if it is an <em>across</em> move, or this move's
   * column if it is a <em>down</em> move.
   */
  public int getRowOrCol() {
    return rowOrCol;
  }

  /**
   * Returns a map which maps column numbers (if the move is across, otherwise
   * row numbers) to the {@link Tile}s to be played at those positions.
   */
  public Map<Integer, Tile> getTileMap() {
    return Collections.unmodifiableMap(tileMap);
  }

  /**
   * Adds a tile to this move at the specified column (if the move is across,
   * otherwise row).
   *
   * @param tile The tile to add.
   * @param pos The column or row on which to place the tile.
   */
  public void addTile(Tile tile, int pos) {
    tileMap.put(pos, tile);
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Move)) {
      return false;
    }

    Move otherMove = (Move) other;

    // Special case: if both tile maps are empty, then both moves are equal (an
    // empty tile map represents a "no-op" move).
    if (this.tileMap.isEmpty() && otherMove.tileMap.isEmpty()) {
      return true;
    }

    // Special case: if both moves involve only one tile, then only the location
    // of that tile matters (the across/down distinction is irrelevant).
    if (this.tileMap.size() == 1 && otherMove.tileMap.size() == 1) {
      return singleTileMoveEquals(otherMove);
    }

    // General case: compare all members.
    if (this.across != otherMove.across) {
      return false;
    }
    if (this.rowOrCol != otherMove.rowOrCol) {
      return false;
    }
    return this.tileMap.equals(otherMove.tileMap);
  }

  /**
   * Returns true if and only if the given single-tile move represents the same
   * move as this single-tile move.
   */
  private boolean singleTileMoveEquals(Move other) {
    Map.Entry<Integer, Tile> mapEntry = tileMap.entrySet().iterator().next();
    int thisRow = across ? rowOrCol : mapEntry.getKey();
    int thisCol = across ? mapEntry.getKey() : rowOrCol;
    Tile thisTile = mapEntry.getValue();

    mapEntry = other.tileMap.entrySet().iterator().next();
    int otherRow = other.across ? other.rowOrCol : mapEntry.getKey();
    int otherCol = other.across ? mapEntry.getKey() : other.rowOrCol;
    Tile otherTile = mapEntry.getValue();

    if (!thisTile.equals(otherTile)) {
      return false;
    }
    if (thisRow != otherRow) {
      return false;
    }
    return thisCol == otherCol;
  }

  @Override
  public int hashCode() {
    int hashCode = rowOrCol ^ tileMap.hashCode();
    return across ? hashCode : -hashCode;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    tileMap.forEach((i, t) -> {
      stringBuilder.append("[").append(t.letter().raw()).append(" (").append(i).append(", ").append(rowOrCol).append(")]");
    });
    return stringBuilder.toString();
  }
}
