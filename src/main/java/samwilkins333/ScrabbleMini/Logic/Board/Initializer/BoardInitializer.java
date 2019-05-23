package main.java.samwilkins333.ScrabbleMini.Logic.Board.Initializer;

import java.util.Map;

/**
 * A functional interface that specifies any entity that
 * can initialize a <code>Board</code> by returning
 * meaningful <code>BoardAttributes</code>.
 * @param <D> The (D)ata that each square in the board receives during
 *           initialization (for example, a multiplier)
 * @param <A> The (A)ttribute that the initializer specifies for each
 *           (D)ata element on the board (for example, fill color
 *           for a given multiplier)
 */
public interface BoardInitializer<D, A> {

  /**
   * Initializes the board.
   * @return the <code>BoardAttributes</code>
   * populated during initialization
   */
  BoardAttributes<D, A> initialize();

  /**
   * A wrapper around the mappings and information
   * needed to initialize a board.
   * @param <D> The (D)ata that each square in the board receives during
   *            initialization (for example, a multiplier)
   * @param <A> The (A)ttribute that the initializer specifies for each
   *           (D)ata element on the board (for example, fill color
   *           for a given multiplier)
   */
  class BoardAttributes<D, A> {
    int squareCount;
    int squareSize;
    D[][] locationMapping;
    Map<D, A> attributeMapping;

    BoardAttributes(int dim, int size, D[][] loc, Map<D, A> attr) {
      this.squareCount = dim;
      this.squareSize = size;
      this.locationMapping = loc;
      this.attributeMapping = attr;
    }

    public int squareCount() {
      return squareCount;
    }

    public int squareSize() {
      return squareSize;
    }

    public D[][] locationMapping() {
      return locationMapping;
    }

    public Map<D, A> attributeMapping() {
      return attributeMapping;
    }
  }
}
