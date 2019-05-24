package main.java.samwilkins333.ScrabbleMini.Logic.GameAgents.Players;

import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Word.Word;
import main.java.samwilkins333.ScrabbleMini.Logic.Computation.CandidateGenerator;
import main.java.samwilkins333.ScrabbleMini.Logic.Computation.CandidateSelector;

import java.util.List;

/**
 * Models an artificially intelligent player
 * computing all possible moves with a GADDAG
 * and applying a heuristic for move selection.
 */
public class SimulatedPlayer extends Player {
  private final CandidateGenerator<Word, Board> generator;
  private final CandidateSelector<Word, Board> heuristic;

  SimulatedPlayer(int playerNumber, CandidateGenerator<Word, Board> generator,
                  CandidateSelector<Word, Board> heuristic) {
    super(PlayerType.SIMULATED, playerNumber);
    this.generator = generator;
    this.heuristic = heuristic;
  }

  @Override
  public void move(Board board) {
    List<Word> candidates = generator.generate(board);
    Word optimal = heuristic.select(candidates, board);
  }

}
