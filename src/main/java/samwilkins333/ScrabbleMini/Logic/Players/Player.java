package main.java.samwilkins333.ScrabbleMini.Logic.Players;

import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Composite.ImageBindings;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.TransitionHelper;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardScore;
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

    int i = 0;
    tileBag.shake();
    while (!rack.isFull()) {
      i++;
      Tile drawn = tileBag.draw();

      double initialX = playerNumber == 1 ? leftOriginLeftPixels : rightOriginLeftPixels;
      double initialY = RackLayoutManager.originTopPixels + squareSidePixels * rack.size();

      ImageBindings bindings = drawn.observableImage().bindings();
      bindings.layoutX(initialX);
      bindings.layoutY(initialY);
      bindings.opacity(1);
      drawn.setRackPosition(initialX, initialY);

      TransitionHelper.pause(0.25 * (i + rack.size()), e -> drawn.render(board)).play();

      rack.add(drawn);
    }
  }

  public void apply(BoardScore result) {
    System.out.printf("Player %s played %s for %d points\n", playerNumber, result.word().toUpperCase(), result.score());
    score += result.score();
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
