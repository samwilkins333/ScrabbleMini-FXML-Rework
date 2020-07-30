package samwilkins333.ScrabbleMini.Logic.GameAgents.Referee.Initializer;

import com.swilkins.ScrabbleBase.Vocabulary.PermutationTrie;

/**
 * An implementer of <code>DictionaryInitializer</code>
 * that reads the appropriate information from a text file
 * of word candidates in the project's configuration folder.
 */
public class PermutationTrieInitializer implements DictionaryInitializer<PermutationTrie> {

  @Override
  public PermutationTrie initialize() {
    return PermutationTrie.loadFrom(getClass().getResource("/configuration/ospd4.txt"));
  }

}
