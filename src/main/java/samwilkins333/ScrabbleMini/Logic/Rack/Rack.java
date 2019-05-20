package main.java.samwilkins333.ScrabbleMini.Logic.Rack;

import javafx.scene.Node;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Rack {
  private final int capacity;
  private List<Tile> internalState = new ArrayList<>();

  public Rack(int capacity) {
    this.capacity = capacity;
  }

  public void add(Tile tile) {
    internalState.add(tile);
  }

  public boolean isFull() {
    return internalState.size() == capacity;
  }

  public List<Node> node() {
    return internalState.stream().map(Tile::node).collect(Collectors.toList());
  }
}
