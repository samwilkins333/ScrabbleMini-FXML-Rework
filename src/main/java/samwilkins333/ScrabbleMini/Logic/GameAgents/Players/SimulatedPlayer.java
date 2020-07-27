package main.java.samwilkins333.ScrabbleMini.Logic.GameAgents.Players;

import com.swilkins.ScrabbleBase.Board.Location.TilePlacement;
import com.swilkins.ScrabbleBase.Board.State.Tile;
import com.swilkins.ScrabbleBase.Generation.Generator;
import com.swilkins.ScrabbleBase.Generation.Candidate;
import com.swilkins.ScrabbleBase.Vocabulary.Trie;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.GameContext;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Tiles.TileView;

import java.util.ArrayList;
import java.util.List;

import static com.swilkins.ScrabbleBase.Generation.Generator.getDefaultOrdering;

/**
 * Models an artificially intelligent player
 * computing all possible moves with a GADDAG
 * and applying a heuristic for move selection.
 */
public class SimulatedPlayer extends Player<Trie> {

  @Override
  public List<TilePlacement> move(GameContext<Trie> context) {
    if (this.rack.isEmpty()) {
      return null;
    }
    List<Candidate> candidates = Generator.compute(context.getRack(), context.getBoard(), getDefaultOrdering());
    if (candidates.size() == 0) {
      return new ArrayList<>();
    }
    System.out.println(candidates.get(0));
    for (TilePlacement placement : candidates.get(0).getPrimary()) {
      if (placement.isExisting()) {
        continue;
      }
      for (TileView tileView : this.rack) {
        if (tileView.getTile().getLetter() == placement.getTile().getLetter()) {
          if (placement.getTile().getLetter() == Tile.BLANK) {
            tileView.getTile().setLetterProxy(placement.getTile().getLetterProxy());
          }
          tileView.playAt(placement.getX(), placement.getY(), true);
          this.rack.remove(tileView);
          break;
        }
      }
    }
    return candidates.get(0).getPrimary();
  }

}
