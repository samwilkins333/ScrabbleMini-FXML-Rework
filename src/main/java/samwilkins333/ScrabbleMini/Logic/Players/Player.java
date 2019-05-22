package main.java.samwilkins333.ScrabbleMini.Logic.Players;

import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Composite.ImageBindings;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.Rack.Rack;
import main.java.samwilkins333.ScrabbleMini.Logic.Rack.RackLayoutManager;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Tile;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.TileBag;

import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.*;
import static main.java.samwilkins333.ScrabbleMini.Logic.Rack.RackLayoutManager.*;

public abstract class Player {
  protected PlayerType type;
  protected final int playerNumber;
  protected Rack rack;
  protected int score = 0;

  Player(PlayerType type, int playerNumber) {
    this.type = type;
    this.playerNumber = playerNumber;
    this.rack = new Rack(7, playerNumber);
  }

  static Player fromType(PlayerType type, int playerNumber) {
    switch (type) {
      case HUMAN: return new HumanPlayer(playerNumber);
      case SIMULATED: return new SimulatedPlayer(playerNumber);
      default: return null;
    }
  }

  public void fillRack(Board board, TileBag tileBag) {
    if (rack.isFull()) return;

    rack.consolidate();

    tileBag.shake();
    while (!rack.isFull()) {
      Tile drawn = tileBag.draw();

      double initialX = playerNumber == 1 ? leftOriginLeftPixels : rightOriginLeftPixels;
      double initialY = RackLayoutManager.originTopPixels + squareSidePixels * rack.size();

      ImageBindings bindings = drawn.observableImage().bindings();
      bindings.layoutX(initialX);
      bindings.layoutY(initialY);
      bindings.opacity(0);
      drawn.setRackPosition(initialX, initialY);

      drawn.render(board);

      rack.add(drawn);
    }
  }

  public void increment(int value) {
    System.out.println(value);
    score += value;
  }

  public Tile transfer(Tile tile) {
    rack.remove(tile);
    return tile;
  }

  public void setRackVisible(boolean state) {
    rack.setVisible(state);
  }

  public abstract void play(Board board);
}
