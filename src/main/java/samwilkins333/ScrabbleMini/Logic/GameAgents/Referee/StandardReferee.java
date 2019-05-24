package main.java.samwilkins333.ScrabbleMini.Logic.GameAgents.Referee;

import main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag.Gaddag;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.GameAgents.Referee.Initializer.GaddagInitializer;
import main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Utility.PlayerList;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Tiles.Indices;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Tiles.Tile;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Tiles.TileBag;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Word.Axis;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Word.Word;

import static main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Board.BoardLayoutManager.dimensions;

/**
 * Models a referee that enforces all the rules and move
 * conditions of standard Scrabble.
 */
public class StandardReferee extends Referee<Gaddag> {
  /**
   * Constructor.
   * @param players the list of player instances involved in the match
   * @param board the fully initialized board on which moves will be played
   * @param tileBag the fully initialized TileBag used to populate
   *                the players' racks
   */
  public StandardReferee(PlayerList<Gaddag> players, Board board,
                         TileBag tileBag) {
    super(players, board, tileBag, new GaddagInitializer());
  }

  @Override
  protected Axis analyzeAxis(Word placements) {
    if (placements.size() == 1) {
      Tile singleton = placements.get(0);
      int column = singleton.indices().column();
      int row = singleton.indices().row();
      boolean vertical = board.verticalNeighbors(column, row);
      boolean horizontal = board.horizontalNeighbors(column, row);
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
    Tile firstTile = placements.get(0);
    for (int i = 1; i < placements.size(); i++) {
      Tile tile = placements.get(i);
      if (tile.indices().row() != firstTile.indices().row()) {
        return false;
      }
    }
    return true;
  }

  private boolean isAlignedVertically(Word placements) {
    Tile firstTile = placements.get(0);
    for (int i = 1; i < placements.size(); i++) {
      Tile tile = placements.get(i);
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
    if (moves == 0 && validFirstMove(placements)) {
      return true;
    }

    for (Tile placed : placements) {
      Indices indices = placed.indices();
      if (board.neighbors(indices.column(), indices.row())) {
        return true;
      }
    }

    return false;
  }

  private boolean validFirstMove(Word placements) {
    int mid = (int) Math.floor(dimensions / 2.0);
    for (Tile placed : placements) {
      int column = placed.indices().column();
      int row = placed.indices().row();
      if (column == mid && row == mid) {
        return true;
      }
    }
    return false;
  }

}
