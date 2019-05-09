package main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings;

import javafx.scene.image.ImageView;

public class ImageBindings {
  private DoubleBinding width;
  private DoubleBinding height;

  public ImageBindings() {
    this.width = new DoubleBinding();
    this.height = new DoubleBinding();
  }

  public void bind(ImageView target, BindingMode mode) {
    width.bind(target.fitWidthProperty(), mode);
    height.bind(target.fitHeightProperty(), mode);
  }

  public double width() {
    return width.getValue();
  }

  public double height() {
    return height.getValue();
  }

  public void setWidth(double width) {
    this.width.setValue(width);
  }

  public void setHeight(double height) {
    this.height.setValue(height);
  }
}
