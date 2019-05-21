package main.java.samwilkins333.ScrabbleMini.Logic.Board;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Initializer.BoardInitializer;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.OverlayType;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Tile;
import main.java.samwilkins333.ScrabbleMini.Logic.Word.Word;
import main.java.samwilkins333.ScrabbleMini.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.*;

public class Board {
  private Tile[][] internalState;
  private List<Tile> placed = new ArrayList<>();

  private final Pane root;
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
    multipliers = attributes.locationMapping();

    squareSidePixels = attributes.squareSize();
    dimensions = attributes.squareCount();
    sideLengthPixels = dimensions * squareSidePixels;

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

    internalState = new Tile[(int) dimensions][(int) dimensions];

    for (int column = 0; column < dimensions; column++) {
      for (int row = 0; row < dimensions; row++) {
        int layoutX = squareSidePixels * column;
        int layoutY = squareSidePixels * row;
        Paint fill = colors.get(multipliers[column][row]);
        Rectangle rectangle = new Rectangle(layoutX, layoutY, squareSidePixels, squareSidePixels);
        rectangle.setFill(fill);
        rectangle.setStroke(Color.BLACK);
        root.getChildren().add(rectangle);
      }
    }
  }

  public BoardInitializer.BoardAttributes<Multiplier, Paint> attributes() {
    return attributes;
  }

  public boolean has(int column, int row) {
    return internalState[column][row] != null;
  }

  public void place(Tile tile) {
    placed.add(tile);
  }

  public void discard(Tile tile) {
    placed.remove(tile);
  }

  public void play(Tile tile) {
    placed.remove(tile);
    int column = (int) tile.indices().getX();
    int row = (int) tile.indices().getY();
    internalState[column][row] = tile;
    tile.flash(OverlayType.SUCCESS);
  }

  public void resetPlacements() {
    placed.forEach(Tile::reset);
    placed.clear();
  }

  public Word placements() {
    return new Word(placed);
  }

  public int score(Word word) {
    int score = 0;
    int wordMultiplier = 1;

    for (Tile tile : word) {
      int column = (int) tile.indices().getX();
      int row = (int) tile.indices().getY();

      Multiplier multiplier = multipliers[column][row];

      wordMultiplier *= multiplier.wordValue();
      score += tile.score() * multiplier.letterValue();
    }

    return score * wordMultiplier;
  }
}
