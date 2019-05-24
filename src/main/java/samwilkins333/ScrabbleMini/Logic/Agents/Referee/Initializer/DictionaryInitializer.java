package main.java.samwilkins333.ScrabbleMini.Logic.Agents.Referee.Initializer;

import java.util.Set;

/**
 * Specifies an entity that populate a dictionary with strings.
 */
public interface DictionaryInitializer {
  /**
   * @return the initialized set of strings, acting as a dictionary
   * used primarily for containment checking
   */
  Set<String> initialize();
}
