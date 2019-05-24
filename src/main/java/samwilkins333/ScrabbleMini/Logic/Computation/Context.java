package main.java.samwilkins333.ScrabbleMini.Logic.Computation;

import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Board.Board;

import java.util.Collection;

/**
 * Provides the <code>WordGenerator</code> with all
 * the relevant game state needed to compute all possibilities.
 * @param <T> the type of collection used to store the game's
 *           lexicon
 */
public class Context<T extends Collection<String>> {
  private final Board board;
  private final T lexicon;

  /**
   * Constructor.
   * @param board the board state at time of generation
   * @param lexicon the entire vocabulary of valid words
   */
  public Context(Board board, T lexicon) {
    this.board = board;
    this.lexicon = lexicon;
  }

  /**
   * @return the board associated with this context
   */
  public Board board() {
    return board;
  }

  /**
   * @return the data structure containing all
   * possible valid words in the lexicon
   */
  public T lexicon() {
    return lexicon;
  }
}
