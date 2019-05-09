package main.java.samwilkins333.ScrabbleMini.FXML.Utilities;

import javafx.scene.image.ImageView;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.ImageBindings;

public class ObservableImage {
  private ImageView view;
  private ImageBindings bindings;

  public ObservableImage(ImageView view, ImageBindings bindings) {
    this.view = view;
    this.bindings = bindings;
  }

  public ImageBindings control() {
    return bindings;
  }

  public ImageView imageView() {
    return view;
  }
}
