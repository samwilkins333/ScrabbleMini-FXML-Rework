package main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Composite;

import javafx.scene.image.ImageView;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.BindingMode;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Primitive.BooleanBinding;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Primitive.DoubleBinding;

public class ImageBindings {
  private DoubleBinding width;
  private DoubleBinding height;
  private DoubleBinding layoutX;
  private DoubleBinding layoutY;
  private BooleanBinding cache;
  private DoubleBinding opacity;
  private DoubleBinding rotation;

  public ImageBindings() {
    this.width = new DoubleBinding();
    this.height = new DoubleBinding();
    this.layoutX = new DoubleBinding();
    this.layoutY = new DoubleBinding();
    this.cache = new BooleanBinding();
    this.opacity = new DoubleBinding();
    this.rotation = new DoubleBinding();
  }

  public void bind(ImageView target, BindingMode mode) {
    width.bind(target.fitWidthProperty(), mode);
    height.bind(target.fitHeightProperty(), mode);
    layoutX.bind(target.layoutXProperty(), mode);
    layoutY.bind(target.layoutYProperty(), mode);
    cache.bind(target.cacheProperty(), mode);
    opacity.bind(target.opacityProperty(), mode);
    rotation.bind(target.rotateProperty(), mode);
  }

  public double width() {
    return width.getValue();
  }

  public double height() {
    return height.getValue();
  }

  public boolean cached() {
    return cache.getValue();
  }

  public double rotation() { return rotation.getValue(); }

  public void layoutX(double xPixels) {
    this.layoutX.setValue(xPixels);
  }

  public void layoutY(double yPixels) {
    this.layoutY.setValue(yPixels);
  }

  public void width(double width) {
    this.width.setValue(width);
  }

  public void height(double height) {
    this.height.setValue(height);
  }

  public void cached(boolean state) {
    this.cache.setValue(state);
  }

  public void opacity(double opacity) { this.opacity.setValue(opacity); }

  public void rotate(double degrees) { this.rotation.setValue(degrees); }

}
