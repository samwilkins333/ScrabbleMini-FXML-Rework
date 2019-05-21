package main.java.samwilkins333.ScrabbleMini.Logic.Players;

import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Composite.ImageBindings;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.Rack.Rack;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Tile;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.TileBag;

import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.*;

public abstract class Player {
  protected PlayerType type;
  protected final int playerNumber;
  protected Rack rack;
  protected int score = 0;

  Player(PlayerType type, int playerNumber) {
    this.type = type;
    this.playerNumber = playerNumber;
    this.rack = new Rack(7);
  }

  static Player fromType(PlayerType type, int playerNumber) {
    switch (type) {
      case HUMAN: return new HumanPlayer(playerNumber);
      case SIMULATED: return new SimulatedPlayer(playerNumber);
      default: return null;
    }
  }

  public void fillRack(Board board, TileBag tileBag) {
    tileBag.shake();
    while (!rack.isFull()) {
      Tile drawn = tileBag.draw();

      double initialX = tilePadding + (playerNumber == 1 ? -2 * squareSidePixels : sideLengthPixels + squareSidePixels);
      double initialY = tilePadding + originTopPixelsLeftRack() + squareSidePixels * rack.size();

      ImageBindings bindings = drawn.observableImage().bindings();
      bindings.layoutX(initialX);
      bindings.layoutY(initialY);
      bindings.opacity(0);
      drawn.initialLayout(initialX, initialY);

      drawn.render(board);

      rack.add(drawn);
    }
  }

  public void increment(int value) {
    System.out.println(value);
    score += value;
  }

  public void transfer(Tile tile) {
    rack.remove(tile);
  }

  public void setRackVisible(boolean state) {
    rack.setVisible(state);
  }

  public abstract void play(Board board);
}
