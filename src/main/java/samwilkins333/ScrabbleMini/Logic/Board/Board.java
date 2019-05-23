package main.java.samwilkins333.ScrabbleMini.Logic.Board;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Initializer.BoardInitializer;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Initializer.BoardInitializer.BoardAttributes;
import main.java.samwilkins333.ScrabbleMini.Logic.Rack.RackLayoutManager;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Indices;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.OverlayType;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Tile;
import main.java.samwilkins333.ScrabbleMini.Logic.Word.Orientation;
import main.java.samwilkins333.ScrabbleMini.Logic.Word.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.tileWidth;
import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.tilePadding;
import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.dimensions;
import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.sideLengthPixels;
import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.squarePixels;
import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.originTopPixels;
import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.originLeftPixels;

import static main.java.samwilkins333.ScrabbleMini.Logic.Rack.RackLayoutManager.rackSize;
import static main.java.samwilkins333.ScrabbleMini.Logic.Word.Orientation.UNDEFINED;
import static main.java.samwilkins333.ScrabbleMini.Logic.Word.Orientation.VERTICAL;
import static main.java.samwilkins333.ScrabbleMini.Main.screenHeight;
import static main.java.samwilkins333.ScrabbleMini.Main.screenWidth;

/**
 * Models a Scrabble board. A board must be initialized in
 * some capacity with a BoardInitializer that specifies
 * multiplier mappings and the colors of each square.
 * In addition to graphical components and display
 * management, the board maintains an internal state
 * of permanently played tiles, and can score
 * sequences and analyze tiles relevant in a sequence (word).
 */
public class Board {
  private Tile[][] internalState;
  private Rectangle[][] squares;
  private List<Tile> placed = new ArrayList<>();

  private final Pane root;
  private final BoardInitializer<Multiplier, Paint> initializer;

  private Multiplier[][] multipliers;
  private static final double H_OFFSET = 0.75;
  private static final double V_OFFSET = 0.85;
  private static final double TILE_RATIO = 0.8;

  /**
   * Constructor.
   * @param root the FXML generated pane to which all
   *             elements will be added
   * @param initializer the caller-specified board initializer
   *                    used to initialize the board
   */
  public Board(Pane root, BoardInitializer<Multiplier, Paint> initializer) {
    this.root = root;
    this.initializer = initializer;
    initializeLayout();
  }

  /**
   * @return the board pane that contains all tiles and
   * colored board square rectangles
   */
  public Pane node() {
    return root;
  }

  private void initializeLayout() {
    BoardAttributes<Multiplier, Paint> attributes = initializer.initialize();
    multipliers = attributes.locationMapping();

    squarePixels = attributes.squareSize();
    dimensions = attributes.squareCount();
    sideLengthPixels = dimensions * squarePixels;

    Map<Multiplier, Paint> colors = attributes.attributeMapping();

    root.setPrefWidth(sideLengthPixels);
    root.setPrefHeight(sideLengthPixels);

    // centers the boardPane in the primary desk space regardless
    // of actual width and height
    originLeftPixels = (screenWidth * H_OFFSET - sideLengthPixels) / 2
            + screenWidth * (1 - H_OFFSET);
    originTopPixels = (screenHeight * V_OFFSET - sideLengthPixels) / 2
            + screenHeight * (1 - V_OFFSET);

    tilePadding = ((1 - TILE_RATIO) / 2) * squarePixels;
    tileWidth = TILE_RATIO * squarePixels;

    RackLayoutManager.originTopPixels = tilePadding
            + ((dimensions - rackSize) * squarePixels) / 2;
    RackLayoutManager.leftOriginLeftPixels = tilePadding
            - 2 * squarePixels;
    RackLayoutManager.rightOriginLeftPixels = tilePadding
            + sideLengthPixels + squarePixels;

    root.setLayoutX(originLeftPixels);
    root.setLayoutY(originTopPixels);

    int dim = (int) dimensions;
    internalState = new Tile[dim][dim];
    squares = new Rectangle[dim][dim];

    for (int column = 0; column < dimensions; column++) {
      for (int row = 0; row < dimensions; row++) {
        int layX = squarePixels * column;
        int layY = squarePixels * row;
        Paint fill = colors.get(multipliers[column][row]);
        Rectangle rect = new Rectangle(layX, layY, squarePixels, squarePixels);
        rect.setFill(fill);
        rect.setStroke(Color.BLACK);
        root.getChildren().add(rect);
        squares[column][row] = rect;
      }
    }
  }

  /**
   * A convenience, bounds-checking method that
   * determines whether or not a tile has been
   * permanently played at the given board coordinates.
   * @param column the target column
   * @param row the target row
   * @return whether or not a tile exists there
   */
  public boolean occupied(int column, int row) {
    if (column < 0 || column >= dimensions) {
      return false;
    }
    if (row < 0 || row >= dimensions) {
      return false;
    }
    return internalState[column][row] != null;
  }

  /**
   * Determines whether a tile has any permanently placed
   * neighbors directly above, below, right or left of it.
   * @param column the target column
   * @param row the target row
   * @return whether or not any permanently played neighbors exist
   */
  public boolean hasNeighbors(int column, int row) {
    return
        occupied(column + 1, row)
        || occupied(column - 1, row)
        || occupied(column, row + 1)
        || occupied(column, row - 1);
  }

  /**
   * Retrieves the tile present at the specified indices, if any.
   * If it is present, it means that the tile has been
   * permanently played on the board.
   * @param column the target column
   * @param row the target row
   * @return the Tile instance at the location, or null if no
   * tile has been played there
   */
  public Tile get(int column, int row) {
    return internalState[column][row];
  }

  // Placement

  /**
   * Adds a tile to the list of temporarily placed words
   * on the board. This is involved only in human interaction and
   * is NOT the same as permanently playing a tile.
   * @param tile the tile to place
   */
  public void place(Tile tile) {
    placed.add(tile);
  }

  /**
   * Removes a tile from the list of temporarily placed words
   * on the board.
   * @param tile the tile to remove
   */
  public void discard(Tile tile) {
    placed.remove(tile);
  }

  /**
   * Encodes the act of permanently playing a tile on
   * the board. It removes it from the list of temporary
   * placements, logically updates the board, converts
   * the background square to gray as an indicator and flashes
   * the success routine.
   * @param tile the tile to permanently play
   */
  public void play(Tile tile) {
    placed.remove(tile);
    int column = tile.indices().column();
    int row = tile.indices().row();
    internalState[column][row] = tile;
    squares[column][row].setFill(Color.GRAY);
    tile.flash(OverlayType.SUCCESS);
  }

  /**
   * Used to visibly remove tiles from
   * the board and return them to their
   * initial rack positions.
   */
  public void resetPlacements() {
    placed.forEach(Tile::reset);
    // logically updates collection of currently placed words
    placed.clear();
  }

  /**
   * @return a copy of the internal record of tiles placed
   * but not permanently played on the board at the time
   * the method is called.
   */
  public Word placements() {
    return new Word(placed);
  }

  /**
   * Assigns a score value to a given word (not just text
   * but a series of located tiles) based on multipliers
   * mapped during the board's initialization.
   * @param word the tiles to score
   * @param official whether or not this is an official placement
   *                 or just used for computation (SimulatedPlayer)
   * @return a struct containing the numerical score and the string version
   * of the word for convenience
   */
  public BoardScore score(Word word, boolean official) {
    int score = 0;
    int wordMultiplier = 1;

    for (Tile tile : word) {
      int column = tile.indices().column();
      int row = tile.indices().row();

      Multiplier multiplier = multipliers[column][row];

      wordMultiplier *= multiplier.wordValue();
      score += tile.score() * multiplier.letterValue();

      if (official) {
        // ensure that the multiplier at this cell can't be double-
        // counted in future moves
        multipliers[column][row] = new Multiplier(1, 1);
      }
    }

    return new BoardScore(score * wordMultiplier, word.toString());
  }

  /**
   * Given a word of temporarily placed tiles and starting
   * at the indices of the first tile, follows
   * the orientation axis of the word and adds consecutive
   * permanently played tiles to the word until the end condition.
   * (See collect(Word word, int column, int row, Orientation o, int dir)
   * for details of end condition).
   * @param word the word to complete
   * @param orientation the word's orientation
   * @return whether or not the word is compact along its
   * axis of orientation
   */
  public boolean complete(Word word, Orientation orientation) {
    assert orientation != UNDEFINED;

    Tile first = word.first(orientation);
    Indices last = word.last(orientation).indices();
    int endCoordinate = orientation == VERTICAL ? last.row() : last.column();

    int column = first.indices().column();
    int row = first.indices().row();

    // If the row or column at which collect() stops (exclusive, and
    // returned from the method) is equal to or before the last row or
    // column in the temporarily placed word, there must have been an invalid
    // gap in the tiles.
    int failedAt = collect(word, column, row, orientation);
    // Thus, if greater, the tiles must form a compact, unbroken sequence.
    return failedAt > endCoordinate;
  }

  /**
   * Given a completed word of placed tiles on the board, computes
   * all the words in the opposite orientation that would be created
   * by playing the given word. Before adding a cross to the list, it
   * ensures the ordering of the list reflects the lexigraphical ordering
   * of the word, depending on the orientation.
   * @param word the word from which crosses will occur
   * @param inverted vertical, if the word is horizontal, and visa versa
   * @return a list of computed, lexigraphically accurate crosses
   */
  public List<Word> crosses(Word word, Orientation inverted) {
    List<Word> crosses = new ArrayList<>();

    for (Tile tile : word) {
      int column = tile.indices().column();
      int row = tile.indices().row();

      if (occupied(column, row)) {
        continue;
      }

      Word cross = new Word(tile);
      collect(cross, column, row, inverted);

      if (cross.size() > 1) {
        cross.sort(Word.reader(inverted));
        crosses.add(cross);
      }
    }

    return crosses;
  }

  /**
   * A convenience method that starts at a pair of indices and collects
   * all relevant/immediately adjacent tiles in both directions of the
   * given orientation.
   * @param word the word to which the collected tiles will be added
   * @param column the column coordinate of reference location
   * @param row the row coordinate of reference location
   * @param orientation the orientation with which to scan for tiles to add
   * @return the row or column at which the collection stopped
   */
  private int collect(Word word, int column, int row, Orientation orientation) {
    collect(word, column, row, orientation, -1);
    return collect(word, column, row, orientation, 1);
  }

  /**
   * Begins at the specified coordinates and adds tiles (that have been
   * permanently played) to the given word, until either an end of the board
   * is reached, or the next consecutive coordinate pair in the search direction
   * does not contain a permanently played tile AND does not correspond to a
   * tile already temporarily placed in the @code word.
   * @param word the word to which the collected tiles will be added
   * @param column the column coordinate of reference location
   * @param row the row coordinate of reference location
   * @param o the orientation with which to scan for tiles to add
   * @param dir the direction in which to search (-1 up / left, 1 right / down)
   * @return the row or column at which the collection stopped
   */
  private int collect(Word word, int column, int row, Orientation o, int dir) {
    assert o != UNDEFINED;
    boolean v = o == VERTICAL;
    int target = v ? row : column;
    while ((target += dir) >= 0 && target < dimensions) {
      int c = v ? column : target;
      int r = v ? target : row;
      Tile get = get(c, r);
      if (get != null) {
        word.add(get);
      } else if (!word.contains(c, r)) {
        break;
      }
    }
    return target;
  }
}
