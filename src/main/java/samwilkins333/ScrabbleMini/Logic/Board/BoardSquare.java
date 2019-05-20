package main.java.samwilkins333.ScrabbleMini.Logic.Board;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Tile;

class BoardSquare {
  private Rectangle node;
  private Tile tile = null;

  BoardSquare(int layoutX, int layoutY, int squareSize, Paint fill) {
    node = new Rectangle(layoutX, layoutY, squareSize, squareSize);
    node.setFill(fill);
    node.setStroke(Color.BLACK);
  }

  Rectangle node() {
    return node;
  }

  void play(Tile tile) {
    this.tile = tile;
  }

  boolean played() {
    return tile != null;
  }
}
