package main.java.samwilkins333.ScrabbleMini.Logic.Rack;

import javafx.geometry.Point2D;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.TransitionHelper;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.*;

public class Rack {
  private final int capacity;
  private List<Tile> internalState = new ArrayList<>();
  private Point2D origin;

  public Rack(int capacity, int playerNumber) {
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
    IntStream.range(0, size()).forEach(i -> TransitionHelper.pause(0.25 * i, e -> placeAt(i)).play());
  }

  private void placeAt(int position) {
    internalState.get(position).adjustRackHeight(RackLayoutManager.originTopPixels + (position * squareSidePixels));
  }
}
