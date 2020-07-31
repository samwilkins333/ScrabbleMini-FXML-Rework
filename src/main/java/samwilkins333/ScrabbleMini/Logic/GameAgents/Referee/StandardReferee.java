package samwilkins333.ScrabbleMini.Logic.GameAgents.Referee;

import samwilkins333.ScrabbleMini.Logic.DataStructures.Utility.PlayerList;
import samwilkins333.ScrabbleMini.Logic.GameAgents.Referee.Initializer.LexiconInitializer;
import samwilkins333.ScrabbleMini.Logic.GameElements.Board.Board;
import samwilkins333.ScrabbleMini.Logic.GameElements.Tiles.Indices;
import samwilkins333.ScrabbleMini.Logic.GameElements.Tiles.TileBag;
import samwilkins333.ScrabbleMini.Logic.GameElements.Tiles.TileView;
import samwilkins333.ScrabbleMini.Logic.GameElements.Word.Axis;
import samwilkins333.ScrabbleMini.Logic.GameElements.Word.Word;

import java.util.Collection;

import static samwilkins333.ScrabbleMini.Logic.GameElements.Board.BoardLayoutManager.dimensions;

/**
 * Models a referee that enforces all the rules and move
 * conditions of standard Scrabble.
 */
public class StandardReferee<T extends Collection<String>> extends Referee<T> {
  /**
   * Constructor.
   *
   * @param players the list of player instances involved in the match
   * @param board   the fully initialized board on which moves will be played
   * @param tileBag the fully initialized TileBag used to populate
   *                the players' racks
   */
  public StandardReferee(PlayerList<T> players, Board board,
                         TileBag tileBag, LexiconInitializer<T> initializer) {
    super(players, board, tileBag, initializer);
  }

  @Override
  protected Axis analyzeAxis(Word placements) {
    if (placements.size() == 1) {
      TileView singleton = placements.get(0);
      int column = singleton.indices().column();
      int row = singleton.indices().row();
      boolean vertical = board.verticalNeighbors(column, row) > 0;
      boolean horizontal = board.horizontalNeighbors(column, row) > 0;
      if (vertical && !horizontal) {
        return Axis.VERTICAL;
      }
      if (horizontal && !vertical) {
        return Axis.HORIZONTAL;
      }
      if (!vertical) {
        return Axis.UNDEFINED;
      }
    }
    if (isAlignedHorizontally(placements)) {
      return Axis.HORIZONTAL;
    }
    if (isAlignedVertically(placements)) {
      return Axis.VERTICAL;
    }
    return Axis.UNDEFINED;
  }

  private boolean isAlignedHorizontally(Word placements) {
    TileView firstTile = placements.get(0);
    for (int i = 1; i < placements.size(); i++) {
      TileView tile = placements.get(i);
      if (tile.indices().row() != firstTile.indices().row()) {
        return false;
      }
    }
    return true;
  }

  private boolean isAlignedVertically(Word placements) {
    TileView firstTile = placements.get(0);
    for (int i = 1; i < placements.size(); i++) {
      TileView tile = placements.get(i);
      if (tile.indices().column() != firstTile.indices().column()) {
        return false;
      }
    }
    return true;
  }

  @Override
  protected boolean isValid(Word word) {
    return lexicon.contains(word.toString());
  }

  @Override
  protected boolean isPositioned(Word placements, Axis axis) {
    if (movesInitiated == 1 && validFirstMove(placements)) {
      return true;
    }

    for (TileView placed : placements) {
      Indices indices = placed.indices();
      if (board.neighbors(indices.column(), indices.row())) {
        return true;
      }
    }

    return false;
  }

  private boolean validFirstMove(Word placements) {
    int mid = (int) Math.floor(dimensions / 2.0);
    for (TileView placed : placements) {
      int column = placed.indices().column();
      int row = placed.indices().row();
      if (column == mid && row == mid) {
        return true;
      }
    }
    return false;
  }

}
