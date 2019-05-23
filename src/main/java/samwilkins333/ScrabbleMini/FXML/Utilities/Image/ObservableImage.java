package main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.BindingMode;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Composite.ImageBindings;
import main.resources.ResourceLoader;

import static main.resources.ResourceType.IMAGE;

/**
 * Models an image whose values are backed by
 * bindings, and thus can be changed in real time
 * as these bound values change as well.
 */
public final class ObservableImage {
  private ImageView view;
  private ImageBindings bindings;
  private DropShadow shadow;

  private static final int RADIUS = 120;
  private static final int DIMENSIONS = 25;

  private ObservableImage(ImageView view, ImageBindings bindings) {
    this.view = view;
    this.bindings = bindings;
    initializeShadow();
  }

  private void initializeShadow() {
    shadow = new DropShadow(BlurType.GAUSSIAN, Color.GRAY, RADIUS, 0.0, 4, 4);
    shadow.setWidth(DIMENSIONS);
    shadow.setHeight(DIMENSIONS);
  }

  /**
   * @return this images underlying <code>ImageBindings</code>.
   * These are used to control the image's properties and thus appearance.
   */
  public ImageBindings bindings() {
    return bindings;
  }

  /**
   * @return the core, internal <code>ImageView</code> actually displaying
   * this image's content.
   */
  public ImageView imageView() {
    return view;
  }

  /**
   * Enables or disables the display of a drop shadow.
   * @param display whether or not the shadow should be displayed.
   */
  public void shadow(boolean display) {
    imageView().setEffect(display ? shadow : null);
  }

  /**
   * Sets the color of the drop shadow.
   * @param color the desired color.
   */
  public void shadowColor(Color color) {
    shadow.setColor(color);
  }

  /**
   * Converts an EXISTING <code>ImageView</code> into an
   * <code>ObservableImage</code> with the specified properties.
   * @param target the existing target ImageView
   * @param url the relative path to the image within the images directory
   * @param mode the binding mode this image will use
   * @param ratio whether or not the original aspect ratio of the image
   *              should be preserved
   * @return the newly created instance of <code>ObservableImage</code>
   */
  public static ObservableImage initialize(ImageView target, String url,
                                           BindingMode mode, boolean ratio) {
    String resource = ResourceLoader.instance.load(IMAGE, url).toExternalForm();
    target.setImage(new Image(resource));
    target.setPreserveRatio(ratio);

    ImageBindings bindings = new ImageBindings();
    if (mode != BindingMode.NONE) {
      bindings.bindAll(target, mode);
      bindings.cached(true);
    }

    return new ObservableImage(target, bindings);
  }

  /**
   * A convenience method used to create a new <code>ObservableImage</code>
   * from scratch, assuming the preservation of the aspect ratio by default.
   * @param location the relative path to the image within the images directory
   * @param mode the binding mode this image will use
   * @return the newly created instance of ObservableImage
   */
  public static ObservableImage create(String location, BindingMode mode) {
    return ObservableImage.initialize(new ImageView(), location, mode, true);
  }

}
