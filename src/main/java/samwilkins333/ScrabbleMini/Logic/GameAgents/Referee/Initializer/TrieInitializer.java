package samwilkins333.ScrabbleMini.Logic.GameAgents.Referee.Initializer;

import com.swilkins.ScrabbleBase.Vocabulary.Trie;
import com.swilkins.ScrabbleBase.Vocabulary.TrieFactory;

/**
 * An implementer of <code>DictionaryInitializer</code>
 * that reads the appropriate information from a text file
 * of word candidates in the project's configuration folder.
 */
public class TrieInitializer implements DictionaryInitializer<Trie> {

  @Override
  public Trie initialize() {
    return TrieFactory.loadFrom(getClass().getResource("/configuration/ospd4.txt"));
  }

}
