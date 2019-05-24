package main.java.samwilkins333.ScrabbleMini.Logic.GameAgents.Players;

import main.java.samwilkins333.ScrabbleMini.Logic.Computation.Context;
import main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag.Gaddag;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Word.Word;
import main.java.samwilkins333.ScrabbleMini.Logic.Computation.CandidateGenerator;
import main.java.samwilkins333.ScrabbleMini.Logic.Computation.CandidateSelector;

import java.util.List;

/**
 * Models an artificially intelligent player
 * computing all possible moves with a GADDAG
 * and applying a heuristic for move selection.
 */
public class SimulatedPlayer extends Player<Gaddag> {
  private final CandidateGenerator<Word, Context<Gaddag>> generator;
  private final CandidateSelector<Word, Context<Gaddag>> heuristic;

  /**
   * Constructor.
   * @param generator the candidate generator instance used
   *                  early in move generation
   * @param heuristic the candidate selector instance used
   *                  in selecting the best word
   */
  public SimulatedPlayer(CandidateGenerator<Word, Context<Gaddag>> generator,
                  CandidateSelector<Word, Context<Gaddag>> heuristic) {
    super();
    this.generator = generator;
    this.heuristic = heuristic;
  }

  @Override
  public void move(Context<Gaddag> context) {
    List<Word> candidates = generator.generate(context.rack(rack));
    Word optimal = heuristic.select(candidates, context);
  }

}
