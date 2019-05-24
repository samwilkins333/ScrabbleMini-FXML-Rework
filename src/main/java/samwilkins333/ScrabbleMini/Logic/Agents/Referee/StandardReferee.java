package main.java.samwilkins333.ScrabbleMini.Logic.Control.Referee;

import main.java.samwilkins333.ScrabbleMini.Logic.Elements.Rack.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.Control.Referee.Initializer.DictionaryReader;
import main.java.samwilkins333.ScrabbleMini.Logic.Agents.Players.PlayerList;
import main.java.samwilkins333.ScrabbleMini.Logic.Elements.Rack.Tiles.Indices;
import main.java.samwilkins333.ScrabbleMini.Logic.Elements.Rack.Tiles.Tile;
import main.java.samwilkins333.ScrabbleMini.Logic.Elements.Rack.Tiles.TileBag;
import main.java.samwilkins333.ScrabbleMini.Logic.Elements.Word.Axis;
import main.java.samwilkins333.ScrabbleMini.Logic.Elements.Word.Word;

import java.util.Set;

import static main.java.samwilkins333.ScrabbleMini.Logic.Elements.Rack.Board.BoardLayoutManager.dimensions;

/**
 * Models a referee that enforces all the rules and move
 * conditions of standard Scrabble.
 */
public class StandardReferee extends Referee {
  private Set<String> dictionary = new DictionaryReader().initialize();

  /**
   * Constructor.
   * @param players the list of player instances involved in the match
   * @param board the fully initialized board on which moves will be played
   * @param tileBag the fully initialized TileBag used to populate
   *                the players' racks
   */
  public StandardReferee(PlayerList players, Board board, TileBag tileBag) {
    super(players, board, tileBag);
  }

  @Override
  protected Axis analyzeOrientation(Word placements) {
    if (placements.size() == 1) {
      Tile singleton = placements.get(0);
      int column = singleton.indices().column();
      int row = singleton.indices().row();
      boolean verticalNeighbors = board.verticalNeighbors(column, row);
      boolean horizontalNeighbors = board.horizontalNeighbors(column, row);
      if (verticalNeighbors && !horizontalNeighbors) {
        return Axis.VERTICAL;
      }
      if (horizontalNeighbors && !verticalNeighbors) {
        return Axis.HORIZONTAL;
      }
      if (!verticalNeighbors) {
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
    return dictionary.contains(word.toString());
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
