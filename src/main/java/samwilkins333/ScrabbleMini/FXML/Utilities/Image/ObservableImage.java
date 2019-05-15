package main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.BindingMode;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Composite.ImageBindings;
import main.resources.ResourceLoader;
import main.resources.ResourceType;

public class ObservableImage {
  private ImageView view;
  private ImageBindings bindings;

  private ObservableImage(ImageView view, ImageBindings bindings) {
    this.view = view;
    this.bindings = bindings;
  }

  public ImageBindings control() {
    return bindings;
  }

  public ImageView imageView() {
    return view;
  }

  public static ObservableImage create(ImageView target, String location, BindingMode mode) {
    String resource = ResourceLoader.instance.load(ResourceType.IMAGE, location).toExternalForm();
    target.setImage(new Image(resource));
    target.setPreserveRatio(true);

    ImageBindings bindings = new ImageBindings();
    bindings.bind(target, mode);
    bindings.cached(true);

    return new ObservableImage(target, bindings);
  }
}
