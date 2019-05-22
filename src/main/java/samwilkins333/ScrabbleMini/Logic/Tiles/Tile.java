package main.java.samwilkins333.ScrabbleMini.Logic.Tiles;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Composite.ImageBindings;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.ObservableImage;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;

import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.*;

public class Tile {
  private final String letter;
  private final int score;
  private final ObservableImage root;
  private TileOverlayStack overlays;
  private Indices indices;
  private Point2D rackPosition;

  private double dragReferenceX;
  private double dragReferenceY;
  private Board board;

  Tile(String letter, int score, ObservableImage root) {
    this.letter = letter;
    this.score = score;
    this.root = root;
    this.indices = new Indices(-1, -1);

    initializeInteractions();
  }

  public void render(Board board) {
    this.board = board;
    this.board.node().getChildren().add(root.imageView());
    overlays = new TileOverlayStack(root.bindings(), board.node().getChildren());
  }

  public String letter() {
    return letter;
  }

  public int score() {
    return score;
  }

  public Indices indices() {
    return indices;
  }

  private void initializeInteractions() {
    root.imageView().setOnMousePressed(this.onMousePressed());
    root.imageView().setOnMouseDragged(this.onMouseDragged());
    root.imageView().setOnMouseReleased(this.onMouseReleased());
  }

  private void toFront() {
    ObservableList<Node> visibleElements = board.node().getChildren();
    visibleElements.remove(root.imageView());
    visibleElements.add(root.imageView());
  }

  private boolean played() {
    int column = indices.column();
    int row = indices.row();
    if (column < 0 && row < 0) return false;
    return board.occupied(column, row);
  }

  private EventHandler<MouseEvent> onMousePressed() {
    return e -> {
      if (played()) return;

      toFront();
      board.discard(this);
      dragReferenceX = e.getSceneX();
      dragReferenceY = e.getSceneY();
    };
  }

  private EventHandler<MouseEvent> onMouseDragged() {
    return e -> {
      if (played()) return;

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
      if (played()) return;

      ImageBindings bindings = root.bindings();
      double centerX = bindings.layoutX() + tileWidth / 2;
      double centerY = bindings.layoutY() + tileWidth / 2;

      Point2D layout;
      indices = toIndices(new Point2D(centerX, centerY));
      int column = indices.column();
      int row = indices.row();
      boolean inColumnRange = column >= 0 && column < dimensions;
      boolean inRowRange = row >= 0 && row < dimensions;

      if (inColumnRange && inRowRange && !board.occupied(column, row) && e.getClickCount() == 1) {
        layout = toPixels(indices);
        board.place(this);
        bindings.layoutX(layout.getX() + tilePadding);
        bindings.layoutY(layout.getY() + tilePadding);
      } else reset();
    };
  }

  public void reset() {
    indices = new Indices(-1, -1);
    ImageBindings bindings = root.bindings();
    bindings.layoutX(rackPosition.getX());
    bindings.layoutY(rackPosition.getY());
  }

  public void flash(OverlayType type) {
    overlays.flash(type);
  }

  public ObservableImage observableImage() {
    return root;
  }

  public void adjustRackHeight(double adjustedY) {
    rackPosition = new Point2D(rackPosition.getX(), adjustedY);
    ImageBindings bindings = new ImageBindings();
    bindings.layoutY(adjustedY);
  }

  public void setRackPosition(double initialX, double initialY) {
    rackPosition = new Point2D(initialX, initialY);
    ImageBindings bindings = root.bindings();
    bindings.layoutX(initialX);
    bindings.layoutY(initialY);
  }
}
