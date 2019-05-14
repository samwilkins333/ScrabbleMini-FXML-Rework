package main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.resources.ResourceLoader;
import main.resources.ResourceType;

public class ImageHelper {

  public static ImageView create(String url) {
    return new ImageView(new Image(ResourceLoader.instance.load(ResourceType.IMAGE, url)));
  }

}
