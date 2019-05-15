package main.java.samwilkins333.ScrabbleMini.Logic.Board;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Initializer.BoardInitializer;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Tile;

import java.util.Map;

public class Board {
  private final Tile[][] internalState;

  private final Pane root;
  private int squareCount;
  private final BoardInitializer<Multiplier, Paint> initializer;
  private BoardInitializer.BoardAttributes<Multiplier, Paint> attributes;

  private Multiplier[][] multipliers;

  public Board(Pane root, BoardInitializer<Multiplier, Paint> initializer) {
    this.root = root;
    this.initializer = initializer;

    initializeLayout();

    this.internalState = new Tile[squareCount][squareCount];
  }

  private void initializeLayout() {
    attributes = initializer.initialize();

    squareCount = attributes.squareCount();
    multipliers = attributes.locationMapping();

    int squareSize = attributes.squareSize();
    Map<Multiplier, Paint> colors = attributes.attributeMapping();

    root.setPrefWidth(squareCount * squareSize);
    root.setPrefHeight(squareCount * squareSize);

    for (int col = 0; col < squareCount; col++) {
      for (int row = 0; row < squareCount; row++) {
        int layoutX = squareSize * col;
        int layoutY = squareSize * row;
        Paint fill = colors.get(multipliers[col][row]);
        BoardSquare square = new BoardSquare(layoutX, layoutY, squareSize, fill);
        root.getChildren().add(square.node());
      }
    }
  }

  public BoardInitializer.BoardAttributes<Multiplier, Paint> attributes() {
    return attributes;
  }

  public boolean has(int column, int row) {
    return internalState[column][row] != null;
  }

  public void play(Tile tile, int column, int row) {
    this.internalState[column][row] = tile;
  }

}
