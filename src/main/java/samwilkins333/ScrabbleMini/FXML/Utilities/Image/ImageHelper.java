package main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image;

import javafx.scene.image.Image;
import main.resources.ResourceLoader;
import main.resources.ResourceType;

public class ImageHelper {

  public static Image create(String url) {
    return new Image(ResourceLoader.instance.load(ResourceType.IMAGE, url));
  }

}
