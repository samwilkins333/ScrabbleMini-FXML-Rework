package main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.BindingMode;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Composite.ImageBindings;
import main.resources.ResourceLoader;
import main.resources.ResourceType;

public class ObservableImage {
  private ImageView view;
  private ImageBindings bindings;
  private DropShadow shadow;

  private ObservableImage(ImageView view, ImageBindings bindings) {
    this.view = view;
    this.bindings = bindings;
    initializeShadow();
  }

  private void initializeShadow() {
    shadow = new DropShadow(BlurType.GAUSSIAN, Color.GRAY, 120, 0.0, 4, 4);
    shadow.setWidth(25);
    shadow.setHeight(25);
  }

  public ImageBindings control() {
    return bindings;
  }

  public ImageView imageView() {
    return view;
  }

  public void shadow(boolean display) {
    imageView().setEffect(display ? shadow : null);
  }

  public static ObservableImage create(ImageView target, String location, BindingMode mode, boolean ratio) {
    String resource = ResourceLoader.instance.load(ResourceType.IMAGE, location).toExternalForm();
    target.setImage(new Image(resource));
    target.setPreserveRatio(ratio);

    ImageBindings bindings = new ImageBindings();
    bindings.bind(target, mode);
    bindings.cached(true);

    return new ObservableImage(target, bindings);
  }

}
