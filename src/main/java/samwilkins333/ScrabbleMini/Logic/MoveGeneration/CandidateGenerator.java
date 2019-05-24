package main.java.samwilkins333.ScrabbleMini.Logic.MoveGeneration;

import java.util.List;

/**
 * Produces a list of items of the
 * specified type.
 * @param <T> the type of the candidates produced
 */
public interface CandidateGenerator<T> {

  /**
   * @return the generated list of candidates
   */
  List<T> generate();
}
