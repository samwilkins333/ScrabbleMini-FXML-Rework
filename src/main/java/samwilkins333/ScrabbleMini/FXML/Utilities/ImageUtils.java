package main.java.samwilkins333.ScrabbleMini.FXML.Utilities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.BindingMode;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.ImageBindings;
import main.resources.ResourceLoader;
import main.resources.ResourceType;

public class ImageUtils {

  private static Image create(String location) {
    return new Image(ResourceLoader.instance.load(ResourceType.IMAGE, location));
  }

  public static ObservableImage createObservable(ImageView target, String location) {
    target.setImage(create(location));
    target.setPreserveRatio(true);
    target.setCache(true);

    ImageBindings bindings = new ImageBindings();
    bindings.bind(target, BindingMode.BIDIRECTIONAL);
    return new ObservableImage(target, bindings);
  }

}
