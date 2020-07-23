package main.java.samwilkins333.ScrabbleMini.Logic.GameAgents.Referee.Initializer;

import com.swilkins.ScrabbleBase.Vocabulary.Trie;

/**
 * Specifies an entity that populate the specified data structure.
 *
 * @param <T> the type of data structure (containing Strings) to return
 */
public interface DictionaryInitializer<T extends Trie> {
  /**
   * @return the initialized set of strings, acting as a dictionary
   * used primarily for containment checking
   */
  T initialize();
}
