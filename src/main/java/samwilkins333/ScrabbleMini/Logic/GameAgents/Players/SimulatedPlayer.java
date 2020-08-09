package samwilkins333.ScrabbleMini.Logic.GameAgents.Players;

import com.swilkins.ScrabbleBase.Board.Location.TilePlacement;
import com.swilkins.ScrabbleBase.Board.State.Tile;
import com.swilkins.ScrabbleBase.Generation.Candidate;
import com.swilkins.ScrabbleBase.Generation.Generator;
import com.swilkins.ScrabbleBase.Generation.GeneratorResult;
import com.swilkins.ScrabbleBase.Vocabulary.PermutationTrie;
import samwilkins333.ScrabbleMini.Logic.GameElements.GameContext;
import samwilkins333.ScrabbleMini.Logic.GameElements.Tiles.TileView;

import java.util.ArrayList;
import java.util.List;

import static com.swilkins.ScrabbleBase.Board.Configuration.STANDARD_RACK_CAPACITY;
import static com.swilkins.ScrabbleBase.Generation.Generator.getDefaultOrdering;

/**
 * Models an artificially intelligent player
 * computing all possible moves with a GADDAG
 * and applying a heuristic for move selection.
 */
public class SimulatedPlayer extends Player<PermutationTrie> {
  private static Generator generator = new Generator();

  static {
    generator.setRackCapacity(STANDARD_RACK_CAPACITY);
  }

  @Override
  public List<TilePlacement> move(GameContext<PermutationTrie> context) {
    if (this.rack.isEmpty()) {
      return null;
    }
    generator.setTrie(context.lexicon());
    GeneratorResult result = generator.compute(context.getRack(), context.getBoard());
    result.orderBy(getDefaultOrdering());
    if (result.isEmpty()) {
      return new ArrayList<>();
    }
    Candidate optimal = result.get(0);
    System.out.println(optimal);
    for (TilePlacement placement : optimal.getPrimary()) {
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
    return optimal.getPrimary();
  }

}
