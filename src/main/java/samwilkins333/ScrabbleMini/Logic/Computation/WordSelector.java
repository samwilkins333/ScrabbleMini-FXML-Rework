package main.java.samwilkins333.ScrabbleMini.Logic.Computation;

import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Word.Word;

import java.util.List;

/**
 * Contains a heuristic used by
 * <code>SimulatedPlayer</code>s to select
 * the optimal word given the current board state.
 */
public class WordSelector implements CandidateSelector<Word, Board> {
  @Override
  public Word select(List<Word> candidates, Board board) {
    return null;
  }
}
