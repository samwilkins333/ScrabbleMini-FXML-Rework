package main.java.samwilkins333.ScrabbleMini.Logic.Computation;

import java.util.List;

/**
 * In effect, a heuristic applied
 * to a list of candidates to ultimately
 * select just one from the list.
 * @param <T> the type of candidate being selected
 * @param <I> the type of input used to contextualize selection
 */
public interface CandidateSelector<T, I> {

  /**
   * Selects the optimal candidate from the list.
   * @param candidates the exhaustive list of all candidates
   * @param input the input used as auxiliary data when determining
   *              selection
   * @return the optimal candidate
   */
  T select(List<T> candidates, I input);
}
