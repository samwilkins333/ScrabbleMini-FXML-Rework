package main.java.samwilkins333.ScrabbleMini.Logic.Control.Referee;

import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.Control.Referee.Initializer.DictionaryReader;
import main.java.samwilkins333.ScrabbleMini.Logic.Players.Player;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Tile;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.TileBag;
import main.java.samwilkins333.ScrabbleMini.Logic.Word.Orientation;
import main.java.samwilkins333.ScrabbleMini.Logic.Word.Word;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.dimensions;

public class StandardReferee extends Referee {
  private Set<String> dictionary = new DictionaryReader().initialize();

  public StandardReferee(List<Player> players, Board board, TileBag tileBag) {
    super(players, board, tileBag);
  }

  @Override
  protected boolean isValid(Word word) {
    return dictionary.contains(word.toString());
  }

  @Override
  protected boolean isFormatted(Word placements) {
    if (isAlignedHorizontally(placements)) {
      placements.sort(Comparator.comparingDouble(t -> t.indices().getX()));
      return isCompactHorizontally(placements);
    } else if (isAlignedVertically(placements)) {
      placements.sort(Comparator.comparingDouble(t -> t.indices().getY()));
      return isCompactVertically(placements);
    }
    return false;
  }

  @Override
  protected boolean isPositioned(Word placements) {
    if (placements.orientation() == Orientation.HORIZONTAL) {
      if (moves == 0 && validFirstMove(placements)) return true;

      for (Tile placed : placements) {
        int x = (int) placed.indices().getX();
        int y = (int) placed.indices().getY();
        if ((y + 1 < dimensions && board.has(x, y + 1)) || (y > 0 && board.has(x, y - 1)))
          return true;
      }
    } else if (placements.orientation() == Orientation.VERTICAL) {
      if (moves == 0 && validFirstMove(placements)) return true;

      for (Tile placed : placements) {
        int x = (int) placed.indices().getX();
        int y = (int) placed.indices().getY();
        if ((x + 1 < dimensions && board.has(x + 1, y)) || (x > 0 && board.has(x - 1, y)))
          return true;
      }
    }
    return false;
  }

  private boolean validFirstMove(Word placements) {
    int mid = (int) Math.floor(dimensions / 2.0);
    for (Tile placed : placements) {
      int column = (int) placed.indices().getX();
      int row = (int) placed.indices().getY();
      if (column == mid && row == mid) return true;
    }
    return false;
  }

  private boolean isAlignedHorizontally(Word placements) {
    Tile firstTile = placements.get(0);
    for (int i = 1; i < placements.size(); i++)
      if (placements.get(i).indices().getY() != firstTile.indices().getY()) return false;
    placements.orientation(Orientation.HORIZONTAL);
    return true;
  }

  private boolean isAlignedVertically(Word placements) {
    Tile firstTile = placements.get(0);
    for (int i = 1; i < placements.size(); i++)
      if (placements.get(i).indices().getX() != firstTile.indices().getX()) return false;
    placements.orientation(Orientation.VERTICAL);
    return true;
  }

  private boolean isCompactHorizontally(Word placements) {
    Tile firstTile = placements.get(0);
    int leftmost = (int) firstTile.indices().getX();

    for (int i = 1; i < placements.size(); i++) {
      int column = (int) placements.get(i).indices().getX();
      if (column - i != leftmost) return false;
    }
    return true;
  }

  private boolean isCompactVertically(Word placements) {
    Tile firstTile = placements.get(0);
    int topmost = (int) firstTile.indices().getY();

    for (int i = 1; i < placements.size(); i++) {
      int thisY = (int) placements.get(i).indices().getY();
      if (thisY - i != topmost) return false;
    }
    return true;
  }

}
