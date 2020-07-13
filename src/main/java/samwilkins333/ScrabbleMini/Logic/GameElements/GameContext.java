package main.java.samwilkins333.ScrabbleMini.Logic.GameElements;

import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Rack.RackView;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Tiles.TileView;

import ScrabbleBase.Tile;
import ScrabbleBase.BoardStateUnit;
import ScrabbleBase.Trie;

import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Provides the <code>MoveGenerator</code> with all
 * the relevant game state needed to compute all possibilities.
 * @param <T> the type of collection used to store the game's
 *           lexicon
 */
public class GameContext<T extends Trie> {

  private final BoardStateUnit[][] board;
  private final T lexicon;
  private final int movesMade;
  private LinkedList<Tile> rack;

  /**
   * Constructor.
   * @param board the board state at time of generation
   * @param lexicon the entire vocabulary of valid words
   * @param movesMade the total number of moves made on the board
   *
   */
  public GameContext(Board board, T lexicon, int movesMade) {
    this.board = board.toContext();
    this.lexicon = lexicon;
    this.movesMade = movesMade;
  }

  /**
   * @return the board associated with this context
   */
  public BoardStateUnit[][] board() {
    return board;
  }

  /**
   * @return the data structure containing all
   * possible valid words in the lexicon
   */
  public T lexicon() {
    return lexicon;
  }

  /**
   * @return the player's tile rack associated with this context
   */
  public LinkedList<Tile> getRack() {
    return rack;
  }

  /**
   * @return the number of moves made on the board as of this
   * generation.
   */
  public int moveCount() {
    return movesMade;
  }

  /**
   * A convenience mutator that returns
   * this instance, for easy method chaining.
   * @param r the rack to embed in this instance
   * @return this instance itself
   */
  public GameContext<T> setRack(RackView r) {
    this.rack = r.stream().map(TileView::getTile).collect(Collectors.toCollection(LinkedList::new));
    return this;
  }

}
