package main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Composite;

import javafx.scene.image.ImageView;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.BindingMode;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Primitive.BooleanBinding;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Primitive.DoubleBinding;

/**
 * A compilation of bindings of the most commonly
 * used properties of <code>ImageView</code>s, allowing
 * their values to be automatically updated in an FXML
 * environment.
 */
public class ImageBindings {
  private DoubleBinding width;
  private DoubleBinding height;
  private DoubleBinding layoutX;
  private DoubleBinding layoutY;
  private BooleanBinding cache;
  private DoubleBinding opacity;
  private DoubleBinding rotation;

  /**
   * Default constructor.
   * Initializes the relevant bindings.
   */
  public ImageBindings() {
    this.width = new DoubleBinding();
    this.height = new DoubleBinding();
    this.layoutX = new DoubleBinding();
    this.layoutY = new DoubleBinding();
    this.cache = new BooleanBinding();
    this.opacity = new DoubleBinding();
    this.rotation = new DoubleBinding();
  }

  /**
   * Binds all the relevant properties of the given
   * <code>ImageView</code> to their underlying
   * bindings, creating an image responsive to value
   * changes.
   *
   * @param target the ImageView to which to bind
   * @param mode   whether or not the binding should be bi- or unidirectional
   */
  public void bindAll(ImageView target, BindingMode mode) {
    width.bind(target.fitWidthProperty(), mode);
    height.bind(target.fitHeightProperty(), mode);
    layoutX.bind(target.layoutXProperty(), mode);
    layoutY.bind(target.layoutYProperty(), mode);
    cache.bind(target.cacheProperty(), mode);
    opacity.bind(target.opacityProperty(), mode);
    rotation.bind(target.rotateProperty(), mode);
  }

  /**
   * @return the ImageView's current width
   */
  public double width() {
    return width.getValue();
  }

  /**
   * @return the ImageView's current height
   */
  public double height() {
    return height.getValue();
  }

  /**
   * @return whether or not the ImageView is currently cached
   */
  public boolean cached() {
    return cache.getValue();
  }

  /**
   * @return the ImageView's current rotation, in degrees
   */
  public double rotation() {
    return rotation.getValue();
  }

  /**
   * @return the ImageView's current layoutX (top left corner)
   */
  public double layoutX() {
    return layoutX.getValue();
  }

  /**
   * @return the ImageView's current layoutY (top left corner)
   */
  public double layoutY() {
    return layoutY.getValue();
  }

  /**
   * @return the exposed binding for the ImageView's width.
   * Used if other properties want to bind to this particular
   * ImageView's width
   */
  public DoubleBinding widthBinding() {
    return width;
  }

  /**
   * @return the exposed binding for the ImageView's height.
   * Used if other properties want to bind to this particular
   * ImageView's height.
   */
  public DoubleBinding heightBinding() {
    return height;
  }

  /**
   * @return the exposed binding for the ImageView's layoutX.
   * Used if other properties want to bind to this particular
   * ImageView's layoutX. (Useful if, for example, another
   * ImageView should move in parallel with this one as this is dragged).
   */
  public DoubleBinding layoutXBinding() {
    return layoutX;
  }

  /**
   * @return the exposed binding for the ImageView's layoutY.
   * Used if other properties want to bind to this particular
   * ImageView's layoutX. (Useful if, for example, another
   * ImageView should move in parallel with this one as this is dragged).
   */
  public DoubleBinding layoutYBinding() {
    return layoutY;
  }

  /**
   * @return the exposed binding for the ImageView's caching preferences.
   * Used if other properties want to bind to this particular
   * ImageView's caching preferences.
   */
  public BooleanBinding cacheBinding() {
    return cache;
  }

  /**
   * @return the exposed binding for the ImageView's opacity.
   * Used if other properties want to bind to this particular
   * ImageView's opacity.
   */
  public DoubleBinding opacityBinding() {
    return opacity;
  }

  /**
   * @return the exposed binding for the ImageView's rotation.
   * Used if other properties want to bind to this particular
   * ImageView's rotation.
   */
  public DoubleBinding rotationBinding() {
    return rotation;
  }

  /**
   * Setter.
   *
   * @param xPixels the target layoutX value
   */
  public void layoutX(double xPixels) {
    this.layoutX.setValue(xPixels);
  }

  /**
   * Setter.
   *
   * @param yPixels the target layoutY value
   */
  public void layoutY(double yPixels) {
    this.layoutY.setValue(yPixels);
  }

  /**
   * Setter.
   *
   * @param w the target width value
   */
  public void width(double w) {
    this.width.setValue(w);
  }

  /**
   * Setter.
   *
   * @param h the target height value
   */
  public void height(double h) {
    this.height.setValue(h);
  }

  /**
   * Setter.
   *
   * @param state the target caching state
   */
  public void cached(boolean state) {
    this.cache.setValue(state);
  }

  /**
   * Setter.
   *
   * @param o the target opacity value
   */
  public void opacity(double o) {
    this.opacity.setValue(o);
  }

  /**
   * Setter.
   *
   * @param degrees the target rotation value
   */
  public void rotate(double degrees) {
    this.rotation.setValue(degrees);
  }

}
