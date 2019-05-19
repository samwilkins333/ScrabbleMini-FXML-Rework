package main.java.samwilkins333.ScrabbleMini.Logic.Tiles;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.BindingMode;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.ObservableImage;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Initializer.TileBagInitializer;
import main.java.samwilkins333.ScrabbleMini.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileBag {
  private ObservableImage root;
  private TileBagInitializer initializer;
  private TileBagInitializer.TileBagAttributes attributes;

  private List<String> internalState = new ArrayList<>();
  private Map<String, ObservableImage> imageCache = new HashMap<>();
  
  private RotateTransition shake;
  private TranslateTransition hide;

  public TileBag(ImageView root, TileBagInitializer initializer) {
    this.initializer = initializer;
    this.root = ObservableImage.create(root, "background/tilebag.png", BindingMode.BIDIRECTIONAL, true);

    initializeLayout();
    initializeAnimation();
  }

  private void initializeLayout() {
    root.control().width(250);
    root.control().layoutX(100);
    root.control().layoutY(400);
    root.control().opacity(1);
    root.shadow(true);
    root.control().rotate(45);

    attributes = initializer.initialize();
    attributes.metadataMapping().forEach((letter, metadata) -> {
      for (int i = 0; i < metadata.frequency(); i++) {
        internalState.add(letter);
      }
    });
  }

  private void initializeAnimation() {
    shake = new RotateTransition(Duration.millis(190), root.imageView());
    shake.setByAngle(15);
    shake.setCycleCount(4);
    shake.setAutoReverse(true);

    hide = new TranslateTransition(Duration.seconds(3), root.imageView());
    hide.setByX(-1 * (root.control().layoutX() + root.control().width()));
    hide.setByY(Main.screenHeight - root.control().layoutY());
  }

  public TileBagInitializer.TileBagAttributes attributes() {
    return attributes;
  }

  public Tile draw() {
    int index = (int) (Math.random() * internalState.size());
    String letter = internalState.remove(index);
    return new Tile(letter, getOrCreateVisual(letter));
  }

  private ObservableImage getOrCreateVisual(String letter) {
    if (imageCache.containsKey(letter))
      return imageCache.get(letter);

    String url = String.format("tiles/%s.png", letter);
    ObservableImage visual = ObservableImage.create(new ImageView(), url, BindingMode.BIDIRECTIONAL, true);
    visual.control().width(BoardLayoutManager.squareSidePixels * 0.9);
    visual.shadow(true);

    return imageCache.put(letter, visual);
  }

  public void shake() {
    if (shake.getStatus() == Animation.Status.RUNNING) return;
    shake.play();
  }

  public void hide() {
    if (hide.getStatus() == Animation.Status.RUNNING) return;
    hide.play();
  }
}
