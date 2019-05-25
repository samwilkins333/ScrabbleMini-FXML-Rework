package main.java.samwilkins333.ScrabbleMini.Logic.GameAgents.Players;

import main.java.samwilkins333.ScrabbleMini.Logic.Computation.CandidateGenerator;
import main.java.samwilkins333.ScrabbleMini.Logic.Computation.CandidateSelector;
import main.java.samwilkins333.ScrabbleMini.Logic.Computation.Context;
import main.java.samwilkins333.ScrabbleMini.Logic.Computation.Move;
import main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag.GADDAG;

import java.util.Set;

/**
 * Models an artificially intelligent player
 * computing all possible moves with a GADDAG
 * and applying a heuristic for move selection.
 */
public class SimulatedPlayer extends Player<GADDAG> {
  private final CandidateGenerator<Move, Context<GADDAG>> generator;
  private final CandidateSelector<Move, Context<GADDAG>> heuristic;

  /**
   * Constructor.
   * @param generator the candidate generator instance used
   *                  early in move generation
   * @param heuristic the candidate selector instance used
   *                  in selecting the best word
   */
  public SimulatedPlayer(CandidateGenerator<Move, Context<GADDAG>> generator,
                  CandidateSelector<Move, Context<GADDAG>> heuristic) {
    super();
    this.generator = generator;
    this.heuristic = heuristic;
  }

  @Override
  public void move(Context<GADDAG> context) {
    Set<Move> candidates = generator.generate(context.rack(rack));
    Move optimal = heuristic.select(candidates, context);
    generator.update(optimal);
  }

  public void update(Move external) {
    generator.update(external);
  }

}
