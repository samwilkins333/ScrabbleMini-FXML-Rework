package main.java.samwilkins333.ScrabbleMini.Logic.Tiles;

import javafx.scene.image.ImageView;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.BindingMode;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.ObservableImage;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Initializer.TileBagAttributes;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Initializer.TileBagInitializer;

public class TileBag {
  private ObservableImage root;
  private TileBagInitializer initializer;
  private TileBagAttributes attributes;

  public TileBag(ImageView root, TileBagInitializer initializer) {
    this.initializer = initializer;
    this.root = ObservableImage.create(root, "background/tilebag.png", BindingMode.BIDIRECTIONAL, true);
    initializeLayout();
  }

  private void initializeLayout() {
    root.control().width(250);
    root.control().layoutX(100);
    root.control().layoutY(400);
    root.control().opacity(1);
    root.shadow(true);
    root.control().rotate(45);

    attributes = initializer.initialize();
  }

  public TileBagAttributes attributes() {
    return attributes;
  }
}
