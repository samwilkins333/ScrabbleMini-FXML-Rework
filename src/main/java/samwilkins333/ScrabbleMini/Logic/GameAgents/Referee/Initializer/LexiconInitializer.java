package samwilkins333.ScrabbleMini.Logic.GameAgents.Referee.Initializer;

import java.util.Collection;

/**
 * Specifies an entity that populate the specified data structure.
 *
 * @param <T> the type of data structure (containing Strings) to return
 */
public interface LexiconInitializer<T extends Collection<String>> {
  /**
   * @return the initialized set of strings, acting as a dictionary
   * used primarily for containment checking
   */
  T initialize();
}
