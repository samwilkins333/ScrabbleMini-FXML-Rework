package main.java.samwilkins333.ScrabbleMini.Logic.ScrabbleBoard;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import main.java.samwilkins333.ScrabbleMini.Logic.ScrabbleBoard.BoardInitializer.BoardInitializer;

import java.util.HashMap;
import java.util.Map;

public class Board {
  private final Pane root;
  private int squareCount;
  private int squareSize;
  private final BoardInitializer<Multiplier, Paint> initializer;
  private BoardInitializer.BoardAttributes<Multiplier, Paint> attributes;

  private Map<Point2D, Multiplier> multipliers = new HashMap<>();
  private Map<Multiplier, Paint> colors = new HashMap<>();

  public Board(Pane root, BoardInitializer<Multiplier, Paint> initializer) {
    this.root = root;
    this.initializer = initializer;

    initializeLayout();
  }

  private void initializeLayout() {
    attributes = initializer.initialize();

    squareCount = attributes.squareCount();
    squareSize = attributes.squareSize();
    multipliers = attributes.locationMapping();
    colors = attributes.attributeMapping();

    root.setPrefWidth(squareCount * squareSize);
    root.setPrefHeight(squareCount * squareSize);

    for (int col = 0; col < squareCount; col++) {
      for (int row = 0; row < squareCount; row++) {
        int layoutX = squareSize * col;
        int layoutY = squareSize * row;
        Paint fill = colors.get(multipliers.get(new Point2D(col, row)));
        BoardSquare square = new BoardSquare(layoutX, layoutY, squareSize, fill);
        root.getChildren().add(square.node());
      }
    }

  }

  public BoardInitializer.BoardAttributes<Multiplier, Paint> attributes() {
    return attributes;
  }

}
