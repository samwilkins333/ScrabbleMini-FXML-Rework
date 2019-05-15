package main.java.samwilkins333.ScrabbleMini.Logic.ScrabbleBoard;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

class BoardSquare extends Rectangle {

  BoardSquare(int squareSize, Paint fill) {
    prefWidth(squareSize);
    prefHeight(squareSize);
    setFill(fill);
  }

}
