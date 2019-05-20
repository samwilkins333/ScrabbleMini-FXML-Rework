package main.java.samwilkins333.ScrabbleMini.Logic.Players;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import main.java.samwilkins333.ScrabbleMini.Logic.Rack.Rack;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Tile;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.TileBag;

public abstract class Player {
  PlayerType type;
  Rack rack;


  Player(PlayerType type) {
    this.type = type;
    this.rack = new Rack(7);
  }

  public static Player fromType(PlayerType type) {
    switch (type) {
      case HUMAN: return new HumanPlayer();
      case SIMULATED: return new SimulatedPlayer();
      default: return null;
    }
  }

  public void fillRack(TileBag tileBag, ObservableList<Node> boardPane) {
    while (!rack.isFull()) {
      Tile drawn = tileBag.draw();
      rack.add(drawn);
      boardPane.add(drawn.node());
    }
  }

  public abstract void play();
}
