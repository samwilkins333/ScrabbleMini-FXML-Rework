package main.java.samwilkins333.ScrabbleMini.Logic.Computation;

import java.util.List;

/**
 * Produces a list of items of the
 * specified type.
 * @param <T> the type of the candidates produced
 * @param <I> the type of the input used to contextualize generation
 */
public interface CandidateGenerator<T, I> {

  /**
   * @param input the input used to give information specific
   *              to generation
   * @return the generated list of candidates
   */
  List<T> generate(I input);
}
