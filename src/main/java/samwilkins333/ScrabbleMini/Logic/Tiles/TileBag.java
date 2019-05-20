package main.java.samwilkins333.ScrabbleMini.Logic.Tiles;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.BindingMode;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.ObservableImage;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Initializer.TileBagInitializer;
import main.java.samwilkins333.ScrabbleMini.Main;

import java.util.ArrayList;
import java.util.List;

import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.tileWidth;

public class TileBag {
  private ObservableImage root;
  private TileBagInitializer initializer;
  private TileBagInitializer.TileBagAttributes attributes;

  private List<String> internalState = new ArrayList<>();

  private RotateTransition shake;
  private TranslateTransition hide;

  public TileBag(ImageView root, TileBagInitializer initializer) {
    this.initializer = initializer;
    this.root = ObservableImage.initialize(root, "background/tilebag.png", BindingMode.BIDIRECTIONAL, true);

    initializeLayout();
    initializeAnimation();
  }

  private void initializeLayout() {
    root.bindings().width(250);
    root.bindings().layoutX(100);
    root.bindings().layoutY(400);
    root.bindings().opacity(1);
    root.shadow(true);
    root.bindings().rotate(45);

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
    hide.setByX(-1 * (root.bindings().layoutX() + root.bindings().width()));
    hide.setByY(Main.screenHeight - root.bindings().layoutY());
  }

  public TileBagInitializer.TileBagAttributes attributes() {
    return attributes;
  }

  public Tile draw() {
    int index = (int) (Math.random() * internalState.size());
    String letter = internalState.remove(index);
    return new Tile(letter, createVisual(letter));
  }

  private ObservableImage createVisual(String letter) {
    String url = String.format("tiles/%s.png", letter);
    ObservableImage visual = ObservableImage.create(url, BindingMode.BIDIRECTIONAL);
    visual.bindings().width(tileWidth);
    visual.shadow(true);
    visual.shadowColor(Color.BLACK);
    visual.bindings().opacity(1);
    return visual;
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
