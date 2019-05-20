package main.java.samwilkins333.ScrabbleMini.Logic.Tiles;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Composite.ImageBindings;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.ObservableImage;

import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.*;

public class Tile {
  private final String letter;
  private final ObservableImage root;
  private Point2D initialLayout;

  private double dragReferenceX;
  private double dragReferenceY;

  Tile(String letter, ObservableImage root) {
    this.letter = letter;
    this.root = root;

    initializeInteractions();
  }

  private void initializeInteractions() {
    root.imageView().setOnMousePressed(this.onMousePressed());
    root.imageView().setOnMouseDragged(this.onMouseDragged());
    root.imageView().setOnMouseReleased(this.onMouseReleased());
  }

  private EventHandler<MouseEvent> onMousePressed() {
    return e -> {
      dragReferenceX = e.getSceneX();
      dragReferenceY = e.getSceneY();
    };
  }

  private EventHandler<MouseEvent> onMouseDragged() {
    return e -> {
      double observedX = e.getSceneX();
      double observedY = e.getSceneY();

      double deltaX = observedX - dragReferenceX;
      double deltaY = observedY - dragReferenceY;

      ImageBindings bindings = root.control();
      bindings.layoutX(bindings.layoutX() + deltaX);
      bindings.layoutY(bindings.layoutY() + deltaY);

      dragReferenceX = observedX;
      dragReferenceY = observedY;
    };
  }

  private EventHandler<MouseEvent> onMouseReleased() {
    return e -> {
      ImageBindings bindings = root.control();
      double centerX = bindings.layoutX() + tileWidth / 2;
      double centerY = bindings.layoutY() + tileWidth / 2;

      Point2D indices;
      if ((indices = toIndices(new Point2D(centerX, centerY))) != null) {
        Point2D quantized = toPixels(indices);
        bindings.layoutX(quantized.getX() + tilePadding);
        bindings.layoutY(quantized.getY() + tilePadding);
      } else reset();
    };
  }

  private void reset() {
    ImageBindings bindings = root.control();
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
