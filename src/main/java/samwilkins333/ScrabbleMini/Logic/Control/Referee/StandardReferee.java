package main.java.samwilkins333.ScrabbleMini.Logic.Control.Referee;

import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.Control.Referee.Initializer.DictionaryReader;
import main.java.samwilkins333.ScrabbleMini.Logic.Players.PlayerList;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Indices;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Tile;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.TileBag;
import main.java.samwilkins333.ScrabbleMini.Logic.Word.Orientation;
import main.java.samwilkins333.ScrabbleMini.Logic.Word.Word;

import java.util.Set;

import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.dimensions;

public class StandardReferee extends Referee {
  private Set<String> dictionary = new DictionaryReader().initialize();

  public StandardReferee(PlayerList players, Board board, TileBag tileBag) {
    super(players, board, tileBag);
  }

  @Override
  protected Orientation analyzeOrientation(Word placements) {
    if (placements.size() == 1) {
      Tile singleton = placements.get(0);
      int column = singleton.indices().column();
      int row = singleton.indices().row();
      boolean verticalNeighbors = board.occupied(column, row + 1) || board.occupied(column, row  - 1);
      boolean horizontalNeighbors = board.occupied(column + 1, row) || board.occupied(column - 1, row);
      if (verticalNeighbors && !horizontalNeighbors) return Orientation.VERTICAL;
      if (horizontalNeighbors && !verticalNeighbors) return Orientation.HORIZONTAL;
      if (!verticalNeighbors) return Orientation.UNDEFINED;
    }
    if (isAlignedHorizontally(placements)) return Orientation.HORIZONTAL;
    if (isAlignedVertically(placements)) return Orientation.VERTICAL;
    return Orientation.UNDEFINED;
  }

  private boolean isAlignedHorizontally(Word placements) {
    Tile firstTile = placements.get(0);
    for (int i = 1; i < placements.size(); i++)
      if (placements.get(i).indices().row() != firstTile.indices().row()) return false;
    return true;
  }

  private boolean isAlignedVertically(Word placements) {
    Tile firstTile = placements.get(0);
    for (int i = 1; i < placements.size(); i++)
      if (placements.get(i).indices().column() != firstTile.indices().column()) return false;
    return true;
  }

  @Override
  protected boolean isValid(Word word) {
    return dictionary.contains(word.toString());
  }

  @Override
  protected boolean isPositioned(Word placements, Orientation orientation) {
    if (moves == 0 && validFirstMove(placements)) return true;

    for (Tile placed : placements) {
      Indices indices = placed.indices();
      if (board.hasNeighbors(indices.column(), indices.row()))
        return true;
    }

    return false;
  }

  private boolean validFirstMove(Word placements) {
    int mid = (int) Math.floor(dimensions / 2.0);
    for (Tile placed : placements) {
      int column = placed.indices().column();
      int row = placed.indices().row();
      if (column == mid && row == mid) return true;
    }
    return false;
  }

}
