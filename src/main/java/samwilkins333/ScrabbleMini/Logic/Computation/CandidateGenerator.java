package main.java.samwilkins333.ScrabbleMini.Logic.Computation;

import java.util.Collection;
import java.util.Set;

/**
 * Produces a list of items of the
 * specified type.
 * @param <T> the type of the candidates produced
 * @param <C> the type of the input used to contextualize generation
 */
public interface CandidateGenerator<T,
        C extends Context<? extends Collection<String>>> {

  /**
   * @param context the input used to give information specific
   *              to generation
   * @return the generated list of candidates
   */
  Set<T> generate(C context);

  void update(T selected);
}
