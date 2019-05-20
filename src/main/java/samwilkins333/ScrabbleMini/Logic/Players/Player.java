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
  private final int playerNumber;
  Rack rack;


  Player(PlayerType type, int playerNumber) {
    this.type = type;
    this.playerNumber = playerNumber;
    this.rack = new Rack(7);
  }

  public static Player fromType(PlayerType type, int playerNumber) {
    switch (type) {
      case HUMAN: return new HumanPlayer(playerNumber);
      case SIMULATED: return new SimulatedPlayer(playerNumber);
      default: return null;
    }
  }

  public void fillRack(TileBag tileBag, ObservableList<Node> boardPane) {
    while (!rack.isFull()) {
      Tile drawn = tileBag.draw();

      ImageBindings bindings = drawn.observableImage().bindings();
      double initialX = tilePadding + (playerNumber == 1 ? -2 * squareSidePixels : sideLengthPixels + squareSidePixels);
      double initialY = tilePadding + originTopPixelsLeftRack() + squareSidePixels * rack.size();
      bindings.layoutX(initialX);
      bindings.layoutY(initialY);
      drawn.initialLayout(initialX, initialY);
      drawn.initializeOverlays(boardPane);

      rack.add(drawn);
      boardPane.add(drawn.observableImage().imageView());
    }
  }

  public abstract void play();
}
