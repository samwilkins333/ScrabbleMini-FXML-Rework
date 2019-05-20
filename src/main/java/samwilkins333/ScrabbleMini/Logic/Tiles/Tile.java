package main.java.samwilkins333.ScrabbleMini.Logic.Tiles;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Composite.ImageBindings;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.ObservableImage;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Board;

import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.*;

public class Tile {
  private final String letter;
  private final ObservableImage root;
  private TileOverlayStack overlays;
  private Point2D indices;
  private Point2D initialLayout;

  private double dragReferenceX;
  private double dragReferenceY;
  private Board board;

  Tile(String letter, ObservableImage root) {
    this.letter = letter;
    this.root = root;
    this.indices = new Point2D(-1, -1);

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

  public Point2D indices() {
    return indices;
  }

  private void initializeInteractions() {
    root.imageView().setOnMousePressed(this.onMousePressed());
    root.imageView().setOnMouseDragged(this.onMouseDragged());
    root.imageView().setOnMouseReleased(this.onMouseReleased());
  }

  private EventHandler<MouseEvent> onMousePressed() {
    return e -> {
      toFront();
      board.discard(this);
      dragReferenceX = e.getSceneX();
      dragReferenceY = e.getSceneY();
    };
  }

  private void toFront() {
    ObservableList<Node> visibleElements = board.node().getChildren();
    visibleElements.remove(root.imageView());
    visibleElements.add(root.imageView());
  }

  private EventHandler<MouseEvent> onMouseDragged() {
    return e -> {
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
      ImageBindings bindings = root.bindings();
      double centerX = bindings.layoutX() + tileWidth / 2;
      double centerY = bindings.layoutY() + tileWidth / 2;

      if (e.getClickCount() > 1) overlays.flash(OverlayType.SUCCESS);

      Point2D indices;
      if ((indices = toIndices(new Point2D(centerX, centerY))) != null) {
        this.indices = toPixels(indices);
        board.place(this);
        bindings.layoutX(this.indices.getX() + tilePadding);
        bindings.layoutY(this.indices.getY() + tilePadding);
      } else reset();
    };
  }

  public void reset() {
    ImageBindings bindings = root.bindings();
    bindings.layoutX(initialLayout.getX());
    bindings.layoutY(initialLayout.getY());
  }

  public ObservableImage observableImage() {
    return root;
  }

  public void initialLayout(double initialX, double initialY) {
    initialLayout = new Point2D(initialX, initialY);
  }
}
