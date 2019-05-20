package main.java.samwilkins333.ScrabbleMini.Logic.Players;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Composite.ImageBindings;
import main.java.samwilkins333.ScrabbleMini.Logic.Rack.Rack;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Tile;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.TileBag;

import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.*;

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

      ImageBindings bindings = drawn.observableImage().control();
      double initialX = tilePadding - 2 * squareSidePixels;
      double initialY = tilePadding + originTopPixelsLeftRack() + squareSidePixels * rack.size();
      bindings.layoutX(initialX);
      bindings.layoutY(initialY);
      drawn.initialLayout(initialX, initialY);

      rack.add(drawn);
      boardPane.add(drawn.observableImage().imageView());
    }
  }

  public abstract void play();
}
