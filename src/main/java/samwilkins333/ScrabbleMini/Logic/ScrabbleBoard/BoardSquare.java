package main.java.samwilkins333.ScrabbleMini.Logic.ScrabbleBoard;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

class BoardSquare {
  private Rectangle node;

  BoardSquare(int layoutX, int layoutY, int squareSize, Paint fill) {
    node = new Rectangle(layoutX, layoutY, squareSize, squareSize);
    node.setFill(fill);
    node.setStroke(Color.BLACK);
  }

  Rectangle node() {
    return node;
  }
}
