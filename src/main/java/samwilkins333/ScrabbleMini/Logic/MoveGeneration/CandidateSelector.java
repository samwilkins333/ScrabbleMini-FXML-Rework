package main.java.samwilkins333.ScrabbleMini.Logic.MoveGeneration;

import java.util.List;

/**
 * In effect, a heuristic applied
 * to a list of candidates to ultimately
 * select just one from the list.
 * @param <T>
 */
public interface CandidateSelector<T> {

  /**
   * Selects the optimal candidate from the list.
   * @param candidates the exhaustive list of all candidates
   * @return the optimal candidate
   */
  T select(List<T> candidates);
}
