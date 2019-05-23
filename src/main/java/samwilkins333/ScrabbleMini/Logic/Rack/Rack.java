package main.java.samwilkins333.ScrabbleMini.Logic.Rack;

import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.TransitionHelper;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Tile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.squareSidePixels;

public class Rack {
  public static final double DELAY = 0.25;
  public int animationsInProgress = 0;

  private final int capacity;
  private List<Tile> internalState = new ArrayList<>();

  public Rack(int capacity) {
    this.capacity = capacity;
  }

  public void add(Tile tile) {
    internalState.add(tile);
  }

  public void remove(Tile tile) {
    internalState.remove(tile);
  }

  public boolean isFull() {
    return internalState.size() == capacity;
  }

  public int size() {
    return internalState.size();
  }

  public void setVisible(boolean visible) {
    internalState.forEach(tile -> tile.observableImage().bindings().opacity(visible ? 1 : 0));
  }

  public void consolidate() {
    IntStream.range(0, size()).forEach(i -> TransitionHelper.pause(DELAY * i, e -> placeAt(i)).play());
  }

  private void placeAt(int position) {
    internalState.get(position).adjustRackHeight(RackLayoutManager.originTopPixels + (position * squareSidePixels));
  }

  public void shuffle(Board board) {
    if (!board.placements().isEmpty() || animationsInProgress > 0) return;

    internalState.sort(Comparator.comparing(Tile::rackPlacement));

    for (int i = 0; i < internalState.size() - 1; i++) {
      Tile upperTile = internalState.get(i);
      Tile lowerTile = internalState.get(i + 1);

      switch ((int) (Math.random() * 3)) {
        case 0:
        case 1:
          upperTile.shift(1);
          lowerTile.shift(-1);

          internalState.set(i + 1, upperTile);
          internalState.set(i, lowerTile);
          break;
        case 2:
          break;
      }
    }
  }
}
