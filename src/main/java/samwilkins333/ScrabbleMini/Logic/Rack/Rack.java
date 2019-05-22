package main.java.samwilkins333.ScrabbleMini.Logic.Rack;

import javafx.geometry.Point2D;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Tile;

import java.util.ArrayList;
import java.util.List;

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
    for (int i = 0; i < size(); i++) {
      internalState.get(i).adjustRackHeight(RackLayoutManager.originTopPixels + (i * squareSidePixels));
    }
  }
}
