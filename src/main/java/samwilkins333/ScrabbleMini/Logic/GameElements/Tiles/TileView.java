package main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Tiles;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Composite.ImageBindings;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.ObservableImage;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.TransitionHelper;
import main.java.samwilkins333.ScrabbleMini.Logic.Generation.Tile;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Rack.RackView;

import static main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Board.BoardLayoutManager.tilePadding;
import static main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Board.BoardLayoutManager.dimensions;
import static main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Board.BoardLayoutManager.tileWidth;
import static main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Board.BoardLayoutManager.toIndices;
import static main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Board.BoardLayoutManager.toPixels;
import static main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Board.BoardLayoutManager.squarePixels;

/**
 * Models a lettered game tile with a given value.
 * Encapsulates both the logical tile and its graphical
 * counterpart.
 */
public class TileView {
  private final Tile tile;
  private final ObservableImage root;
  private TileOverlayStack overlays;
  private Indices indices;
  private Point2D rackPosition;

  private FadeTransition overlap;

  private double dragReferenceX;
  private double dragReferenceY;
  private Board board;

  TileView(Tile tile, ObservableImage root, boolean interactive) {
    this.tile = tile;
    this.root = root;
    this.indices = new Indices(-1, -1);

    if (interactive) {
      initializeInteractions();
    }
  }

  /**
   * Stores a reference to the game board and
   * adds this tile's node to the board's node's children.
   * @param b the reference to the board
   */
  public void render(Board b) {
    this.board = b;
    this.board.node().getChildren().add(root.imageView());
    overlays = new TileOverlayStack(root.bindings(), this.board.node().getChildren());
  }

  /**
   * The (column, row) of the board this tile
   * occupies. If the tile is not on the board,
   * the indices will be (-1, -1).
   * @return this tile's board location
   */
  public Indices indices() {
    return indices;
  }

  /**
   * @return the pixel layoutY of this tile's position in
   * the rack.
   */
  public double rackPlacement() {
    return rackPosition.getY();
  }

  /**
   * @return the <code>ObservableImage</code> that
   * encompasses the view of this tile.
   */
  public ObservableImage observableImage() {
    return root;
  }

  /**
   * Manipulates ordering of child elements
   * to bring this Tile's view to the front
   * of the pane.
   */
  public void toFront() {
    ObservableList<Node> visibleElements = board.node().getChildren();
    visibleElements.remove(root.imageView());
    visibleElements.add(root.imageView());
  }

  private void initializeInteractions() {
    root.imageView().setOnMousePressed(this.onMousePressed());
    root.imageView().setOnMouseDragged(this.onMouseDragged());
    root.imageView().setOnMouseReleased(this.onMouseReleased());
    overlap = TransitionHelper.flash(root.imageView(), RackView.DELAY, -1);
  }

  /**
   * Graphically and logically returns this tile to
   * its initial starting place in the rack.
   */
  public void reset() {
    indices = new Indices(-1, -1);
    ImageBindings bindings = root.bindings();
    bindings.layoutX(rackPosition.getX());
    bindings.layoutY(rackPosition.getY());
    bindings.opacity(1);
  }

  /**
   * Triggers the flash associated with the
   * given type.
   * @param type the outcome to flash
   */
  public void flash(OverlayType type) {
    overlays.flash(type);
  }

  private EventHandler<MouseEvent> onMousePressed() {
    return e -> {
      if (played()) {
        return;
      }

      toFront();
      overlap.stop();
      root.bindings().opacity(1);
      board.discard(this);
      dragReferenceX = e.getSceneX();
      dragReferenceY = e.getSceneY();
    };
  }

  private EventHandler<MouseEvent> onMouseDragged() {
    return e -> {
      if (played()) {
        return;
      }

      double observedX = e.getSceneX();
      double observedY = e.getSceneY();

      double deltaX = observedX - dragReferenceX;
      double deltaY = observedY - dragReferenceY;

      ImageBindings bindings = root.bindings();
      bindings.layoutX(bindings.layoutX() + deltaX);
      bindings.layoutY(bindings.layoutY() + deltaY);

      dragReferenceX = observedX;
      dragReferenceY = observedY;
    };
  }

  private EventHandler<MouseEvent> onMouseReleased() {
    return e -> {
      if (played()) {
        return;
      }

      ImageBindings bindings = root.bindings();
      double centerX = bindings.layoutX() + tileWidth / 2;
      double centerY = bindings.layoutY() + tileWidth / 2;

      Point2D layout;
      indices = toIndices(new Point2D(centerX, centerY));
      int column = indices.column();
      int row = indices.row();

      if (board.placements().contains(column, row)) {
        overlap.play();
      }

      boolean validCol = column >= 0 && column < dimensions;
      boolean validRow = row >= 0 && row < dimensions;
      boolean single = e.getClickCount() == 1;

      if (validCol && validRow && !board.occupied(column, row) && single) {
        layout = toPixels(indices);
        board.place(this);
        bindings.layoutX(layout.getX() + tilePadding);
        bindings.layoutY(layout.getY() + tilePadding);
      } else {
        reset();
      }
    };
  }

  /**
   * @return whether or not this tile has been permanently played on the board
   */
  private boolean played() {
    int column = indices.column();
    int row = indices.row();
    if (column < 0 && row < 0) {
      return false;
    }
    return board.occupied(column, row);
  }

  /**
   * An analog to shift, but rather than shifting by some
   * amount, it specifically sets the new layoutY. Used in
   * consolidating a rack before refilling.
   * @param adjustedY the new placement in the rack
   */
  public void set(double adjustedY) {
    rackPosition = new Point2D(rackPosition.getX(), adjustedY);
    ImageBindings bindings = root.bindings();
    bindings.layoutY(adjustedY);
  }

  /**
   * Shifts a tile up or down the equivalent of one
   * position. Used in shuffling tiles in a rack.
   * @param dir -1 for up, 1 for down
   */
  public void shift(int dir) {
    double adjustedY = root.bindings().layoutY() + dir * squarePixels;
    rackPosition = new Point2D(rackPosition.getX(), adjustedY);
    root.bindings().layoutY(adjustedY);
  }

  /**
   * Called immediately after a tile has been drawn.
   * Records the tile's initial pixel location in the rack (used for resetting)
   * @param initialX the initial layoutX
   * @param initialY the initial layoutY
   */
  public void setRackPosition(double initialX, double initialY) {
    rackPosition = new Point2D(initialX, initialY);
    root.bindings().layoutX(initialX);
    root.bindings().layoutY(initialY);
  }

  public Tile getTile() {
    return tile;
  }

}
