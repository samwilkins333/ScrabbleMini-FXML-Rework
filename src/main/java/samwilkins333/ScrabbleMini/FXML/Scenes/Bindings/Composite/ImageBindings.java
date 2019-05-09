package main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Composite;

import javafx.scene.image.ImageView;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.BindingMode;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Primitive.BooleanBinding;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Primitive.DoubleBinding;

public class ImageBindings {
  private DoubleBinding width;
  private DoubleBinding height;
  private BooleanBinding cache;
  private DoubleBinding opacity;

  public ImageBindings() {
    this.width = new DoubleBinding();
    this.height = new DoubleBinding();
    this.cache = new BooleanBinding();
    this.opacity = new DoubleBinding();
  }

  public void bind(ImageView target, BindingMode mode) {
    width.bind(target.fitWidthProperty(), mode);
    height.bind(target.fitHeightProperty(), mode);
    cache.bind(target.cacheProperty(), mode);
    opacity.bind(target.opacityProperty(), mode);
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
}
