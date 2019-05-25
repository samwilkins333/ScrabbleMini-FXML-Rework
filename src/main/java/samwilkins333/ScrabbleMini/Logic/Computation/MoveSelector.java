package main.java.samwilkins333.ScrabbleMini.Logic.Computation;

import main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag.GADDAG;

import java.util.Set;

/**
 * Contains a heuristic used by
 * <code>SimulatedPlayer</code>s to select
 * the optimal word given the current board state.
 */
public class MoveSelector implements CandidateSelector<Move, Context<GADDAG>> {
  @Override
  public Move select(Set<Move> candidates, Context<GADDAG> context) {
    return null;
  }
}
