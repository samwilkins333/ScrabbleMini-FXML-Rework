package main.java.samwilkins333.ScrabbleMini.Logic.ScrabbleBoard;

import javafx.geometry.Point2D;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import main.java.samwilkins333.ScrabbleMini.Logic.ScrabbleBoard.BoardInitializer.BoardInitializer;

import java.util.HashMap;
import java.util.Map;

public class Board {
  private final GridPane root;
  private final int squareCount;
  private final int squareSize;
  private final BoardInitializer<Multiplier, Paint> initializer;

  private Map<Point2D, Multiplier> multipliers = new HashMap<>();
  private Map<Multiplier, Paint> colorMapping = new HashMap<>();

  public Board(GridPane root, int squareCount, int squareSize, BoardInitializer<Multiplier, Paint> initializer) {
    this.root = root;
    this.squareCount = squareCount;
    this.squareSize = squareSize;
    this.initializer = initializer;

    initializeLayout();
  }

  private void initializeLayout() {
    root.prefWidth(squareCount * squareSize);
    root.prefHeight(squareCount * squareSize);
    root.setGridLinesVisible(true);

    colorMapping = initializer.initialize(multipliers, squareCount);

    for (int col = 0; col < squareCount; col++) {
      for (int row = 0; row < squareCount; row++) {
        Multiplier multiplier = multipliers.get(new Point2D(col, row));
        root.add(new BoardSquare(squareSize, colorMapping.get(multiplier)), col, row);
      }
    }
  }

}
