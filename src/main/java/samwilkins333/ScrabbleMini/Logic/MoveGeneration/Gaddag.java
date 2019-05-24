package main.java.samwilkins333.ScrabbleMini.Logic.MoveGeneration;

import main.java.samwilkins333.ScrabbleMini.Logic.Elements.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.Elements.Rack.Rack;
import main.java.samwilkins333.ScrabbleMini.Logic.Elements.Word.Word;

/**
 * A specialized Trie data structure
 * that calculates valid board moves
 * extremely efficiently.
 */
public class Gaddag {
  private final Board board;

  /**
   * Constructor.
   * @param board the board on which
   *              move generation is calculated
   */
  public Gaddag(Board board) {
    this.board = board;
  }

  /**
   * One of two recursive backtracking co-routines
   * used to generate all possible words.
   * @param position the offset from the anchor square
   * @param word the target word
   * @param rack the player's current rack of letters
   * @param arc the arc to be considered
   */
  public void generate(int position, Word word, Rack rack, Arc arc) {

  }

}
