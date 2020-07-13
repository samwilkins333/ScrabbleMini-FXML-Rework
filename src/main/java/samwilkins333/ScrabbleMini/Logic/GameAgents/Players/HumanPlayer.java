package main.java.samwilkins333.ScrabbleMini.Logic.GameAgents.Players;

import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.GameContext;

import ScrabbleBase.Generator;
import ScrabbleBase.ScoredCandidate;
import ScrabbleBase.Trie;


import java.util.List;

/**
 * Models a human scrabble player. In effect, it is a
 * lightweight wrapper whose <code>move()</code> method
 * just nominally encapsulates the manual dragging and
 * checking of tiles by an actual human player.
 */
public class HumanPlayer extends Player<Trie> {

  /**
   * Constructor.
   */
  public HumanPlayer() {
    super();
  }

  @Override
  public void move(GameContext<Trie> context) {
    List<ScoredCandidate> candidates = Generator.Instance.computeAllCandidates(
            context.getRack(),
            context.board(),
            context.moveCount()
    );
    System.out.println(candidates.size());
    System.out.println(candidates.get(0));
  }
}
