package main.java.samwilkins333.ScrabbleMini.Logic.Computation;

import main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag.Gaddag;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Word.Word;

import java.util.List;

/**
 * An implementor of <code>CandidateGenerator</code>, produces
 * a list of all possible words that can be played
 * on the given board.
 */
public class WordGenerator implements
        CandidateGenerator<Word, Context<Gaddag>> {

  @Override
  public List<Word> generate(Context<Gaddag> context) {
    return null;
  }
}
