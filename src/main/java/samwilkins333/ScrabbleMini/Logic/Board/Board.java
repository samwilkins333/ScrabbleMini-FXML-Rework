package main.java.samwilkins333.ScrabbleMini.Logic.Board;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Initializer.BoardInitializer;
import main.java.samwilkins333.ScrabbleMini.Logic.Rack.RackLayoutManager;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Indices;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.OverlayType;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Tile;
import main.java.samwilkins333.ScrabbleMini.Logic.Word.Orientation;
import main.java.samwilkins333.ScrabbleMini.Logic.Word.Word;
import main.java.samwilkins333.ScrabbleMini.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.*;
import static main.java.samwilkins333.ScrabbleMini.Logic.Rack.RackLayoutManager.rackSize;
import static main.java.samwilkins333.ScrabbleMini.Logic.Word.Orientation.*;

public class Board {
  private Tile[][] internalState;
  private Rectangle[][] squares;
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

    RackLayoutManager.originTopPixels = tilePadding + ((dimensions - rackSize) * squareSidePixels) / 2;
    RackLayoutManager.leftOriginLeftPixels = tilePadding + -2 * squareSidePixels;
    RackLayoutManager.rightOriginLeftPixels = tilePadding + sideLengthPixels + squareSidePixels;

    root.setLayoutX(originLeftPixels);
    root.setLayoutY(originTopPixels);

    int dim = (int) dimensions;
    internalState = new Tile[dim][dim];
    squares = new Rectangle[dim][dim];

    for (int column = 0; column < dimensions; column++) {
      for (int row = 0; row < dimensions; row++) {
        int layoutX = squareSidePixels * column;
        int layoutY = squareSidePixels * row;
        Paint fill = colors.get(multipliers[column][row]);
        Rectangle rectangle = new Rectangle(layoutX, layoutY, squareSidePixels, squareSidePixels);
        rectangle.setFill(fill);
        rectangle.setStroke(Color.BLACK);
        root.getChildren().add(rectangle);
        squares[column][row] = rectangle;
      }
    }
  }

  public BoardInitializer.BoardAttributes<Multiplier, Paint> attributes() {
    return attributes;
  }

  public boolean occupied(int column, int row) {
    if (column < 0 || column >= dimensions) return false;
    if (row < 0 || row >= dimensions) return false;
    return internalState[column][row] != null;
  }

  public boolean hasNeighbors(int column, int row) {
    return occupied(column + 1, row) || occupied(column - 1, row) || occupied(column, row + 1) || occupied(column, row - 1);
  }

  public Tile get(int column, int row) {
    return internalState[column][row];
  }

  public void place(Tile tile) {
    placed.add(tile);
  }

  public void discard(Tile tile) {
    placed.remove(tile);
  }

  public void play(Tile tile) {
    placed.remove(tile);
    int column = tile.indices().column();
    int row = tile.indices().row();
    internalState[column][row] = tile;
    squares[column][row].setFill(Color.GRAY);
    tile.flash(OverlayType.SUCCESS);
  }

  public void resetPlacements() {
    placed.forEach(Tile::reset);
    placed.clear();
  }

  public Word placements() {
    return new Word(placed);
  }

  public BoardScore score(Word word, boolean official) {
    int score = 0;
    int wordMultiplier = 1;

    for (Tile tile : word) {
      int column = tile.indices().column();
      int row = tile.indices().row();

      Multiplier multiplier = multipliers[column][row];

      wordMultiplier *= multiplier.wordValue();
      score += tile.score() * multiplier.letterValue();

      if (official) multipliers[column][row] = new Multiplier(1, 1);
    }

    return new BoardScore(score * wordMultiplier, word.toString());
  }

  public boolean complete(Word word, Orientation orientation) {
    assert orientation != UNDEFINED;

    Tile first = word.first(orientation);
    Indices last = word.last(orientation).indices();

    int column = first.indices().column();
    int row = first.indices().row();

    return (collect(word, column, row, orientation) > (orientation == VERTICAL ? last.row() : last.column()));
  }

  public List<Word> crosses(Word word, Orientation inverted) {
    List<Word> crosses = new ArrayList<>();

    for (Tile tile : word) {
      int column = tile.indices().column();
      int row = tile.indices().row();

      if (occupied(column, row)) continue;

      Word cross = new Word(tile);
      collect(cross, column, row, inverted);

      if (cross.size() > 1) {
        cross.sort(Word.reader(inverted));
        crosses.add(cross);
      }
    }

    return crosses;
  }

  private int collect(Word word, int column, int row, Orientation orientation) {
    collect(word, column, row, orientation, -1);
    return collect(word, column, row, orientation, 1);
  }

  private int collect(Word word, int column, int row, Orientation orientation, int dir) {
    assert orientation != UNDEFINED;
    boolean v = orientation == VERTICAL;
    int target = v ? row : column;
    while ((target += dir) >= 0 && target < dimensions) {
      int c = v ? column : target;
      int r = v ? target : row;
      Tile get = get(c, r);
      if (get != null) word.add(get);
      else if (!word.contains(c, r)) break;
    }
    return target;
  }
}
