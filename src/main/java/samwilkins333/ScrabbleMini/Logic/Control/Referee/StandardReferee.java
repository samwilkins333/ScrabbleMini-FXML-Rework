package main.java.samwilkins333.ScrabbleMini.Logic.Control.Referee;

import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.Players.Player;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Tile;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.TileBag;

import java.util.Comparator;
import java.util.List;

import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.dimensions;

public class StandardReferee extends Referee {

  public StandardReferee(List<Player> players, Board board, TileBag tileBag) {
    super(players, board, tileBag);
  }

  @Override
  protected boolean validatePlacements() {
    List<Tile> placements = board.placements();
    if (placements.isEmpty()) return false;
    return isFormatted(placements);
  }

  private Boolean isFormatted(List<Tile> placements) {
    if (isAlignedHorizontally(placements)) {
      placements.sort(Comparator.comparingDouble(t -> t.indices().getX()));
      if (!isCompactHorizontally(placements)) return false;
      if (moves == 0) return true;

      for (Tile placed : placements) {
        int x = (int) placed.indices().getX();
        int y = (int) placed.indices().getY();
        if ((y + 1 < dimensions && board.has(x, y + 1)) || (y > 0 && board.has(x, y - 1)))
          return true;
      }
    } else if (isAlignedVertically(placements)) {
      placements.sort(Comparator.comparingDouble(t -> t.indices().getY()));
      if (!isCompactVertically(placements)) return false;
      if (moves == 0) return true;

      for (Tile placed : placements) {
        int x = (int) placed.indices().getX();
        int y = (int) placed.indices().getY();
        if ((x + 1 < dimensions && board.has(x + 1, y)) || (x > 0 && board.has(x - 1, y)))
          return true;
      }
    }

    return false;
  }

  private boolean isAlignedHorizontally(List<Tile> placements) {
    Tile firstTile = placements.get(0);
    for (int i = 1; i < placements.size(); i++)
      if (placements.get(i).indices().getY() != firstTile.indices().getY()) return false;
    return true;
  }

  private boolean isAlignedVertically(List<Tile> placements) {
    Tile firstTile = placements.get(0);
    for (int i = 1; i < placements.size(); i++)
      if (placements.get(i).indices().getX() != firstTile.indices().getX()) return false;
    return true;
  }

  private boolean isCompactHorizontally(List<Tile> placements) {
    Tile firstTile = placements.get(0);
    int leftmost = (int) firstTile.indices().getX();

    for (int i = 1; i < placements.size(); i++) {
      int column = (int) placements.get(i).indices().getX();
      if (column - i != leftmost) return false;
    }
    return true;
  }

  private boolean isCompactVertically(List<Tile> placements) {
    Tile firstTile = placements.get(0);
    int topmost = (int) firstTile.indices().getY();

    for (int i = 1; i < placements.size(); i++) {
      int thisY = (int) placements.get(i).indices().getY();
      if (thisY - i != topmost) return false;
    }
    return true;
  }

}
