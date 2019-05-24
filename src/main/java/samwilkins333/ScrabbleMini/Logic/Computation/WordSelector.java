package main.java.samwilkins333.ScrabbleMini.Logic.Computation;

import main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag.Gaddag;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Word.Word;

import java.util.List;

/**
 * Contains a heuristic used by
 * <code>SimulatedPlayer</code>s to select
 * the optimal word given the current board state.
 */
public class WordSelector implements CandidateSelector<Word, Context<Gaddag>> {
  @Override
  public Word select(List<Word> candidates, Context<Gaddag> context) {
    return null;
  }
}
