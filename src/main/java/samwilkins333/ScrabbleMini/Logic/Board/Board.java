package main.java.samwilkins333.ScrabbleMini.Logic.Board;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Initializer.BoardInitializer;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Tile;
import main.java.samwilkins333.ScrabbleMini.Main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.*;

public class Board {
  private BoardSquare[][] internalState;
  private List<Tile> placed = new ArrayList<>();

  private final Pane root;
  private int squareCount;
  private final BoardInitializer<Multiplier, Paint> initializer;
  private BoardInitializer.BoardAttributes<Multiplier, Paint> attributes;

  private Multiplier[][] multipliers;

  public Board(Pane root, BoardInitializer<Multiplier, Paint> initializer) {
    this.root = root;
    this.initializer = initializer;

    initializeLayout();
  }

  public Pane node() {
    return root;
  }

  private void initializeLayout() {
    attributes = initializer.initialize();

    squareCount = attributes.squareCount();
    multipliers = attributes.locationMapping();

    squareSidePixels = attributes.squareSize();
    dimensions = attributes.squareCount();
    sideLengthPixels = squareCount * squareSidePixels;

    Map<Multiplier, Paint> colors = attributes.attributeMapping();

    root.setPrefWidth(sideLengthPixels);
    root.setPrefHeight(sideLengthPixels);

    // centers the boardPane in the primary desk space regardless of actual width and height
    originLeftPixels = (Main.screenWidth * 0.75 - sideLengthPixels) / 2 + Main.screenWidth * 0.25;
    originTopPixels = (Main.screenHeight * 0.85 - sideLengthPixels) / 2 + Main.screenHeight * 0.15;

    tilePadding = 0.1 * squareSidePixels;
    tileWidth = 0.8 * squareSidePixels;

    root.setLayoutX(originLeftPixels);
    root.setLayoutY(originTopPixels);

    internalState = new BoardSquare[squareCount][squareCount];
    for (int col = 0; col < squareCount; col++) {
      for (int row = 0; row < squareCount; row++) {
        int layoutX = squareSidePixels * col;
        int layoutY = squareSidePixels * row;
        Paint fill = colors.get(multipliers[col][row]);
        BoardSquare square = new BoardSquare(layoutX, layoutY, squareSidePixels, fill);
        internalState[col][row] = square;
        root.getChildren().add(square.node());
      }
    }
  }

  public BoardInitializer.BoardAttributes<Multiplier, Paint> attributes() {
    return attributes;
  }

  public boolean has(int column, int row) {
    return internalState[column][row].played();
  }

  public void play(Tile tile) {
    int column = (int) tile.indices().getX();
    int row = (int) tile.indices().getY();
    this.internalState[column][row].play(tile);
  }

  public void place(Tile tile) {
    placed.add(tile);
  }

  public void discard(Tile tile) {
    placed.remove(tile);
  }

  public void clearPlacements() {
    placed.forEach(Tile::reset);
    placed.clear();
  }

  public List<Tile> placements() {
    return List.copyOf(placed);
  }
}
