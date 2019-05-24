package main.java.samwilkins333.ScrabbleMini.Logic.Tiles;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.BindingMode;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.ObservableImage;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.TransitionHelper;
import main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Initializer.TileBagInitializer;
import main.java.samwilkins333.ScrabbleMini.Main;

import java.util.ArrayList;
import java.util.List;

import static main.java.samwilkins333.ScrabbleMini.Logic.Board.BoardLayoutManager.tileWidth;

/**
 * A class that logically and graphically models a
 * finite container for tiles, from which they can
 * be drawn at random.
 */
public class TileBag {
  private ObservableImage root;
  private TileBagInitializer initializer;
  private TileBagInitializer.TileBagAttributes attributes;

  private List<String> internalState = new ArrayList<>();

  private RotateTransition shake;
  private TranslateTransition hide;

  private static final double SHAKE_DURATION = 0.25;
  private static final double SHAKE_BY = 15;
  private static final int SHAKE_CYCLES = 4;

  private static final int WIDTH = 250;
  private static final int LAYOUT_X = 100;
  private static final int LAYOUT_Y = 400;
  private static final int ROTATION = 45;

  /**
   * Constructor.
   * @param root the FXML generated <code>ImageView</code> displaying
   *             the tile container
   * @param initializer the caller-specified initializer
   *                    used to initialize the TileBag
   */
  public TileBag(ImageView root, TileBagInitializer initializer) {
    this.initializer = initializer;
    this.root = ObservableImage.initialize(
            root,
            "background/tilebag.png",
            BindingMode.BIDIRECTIONAL,
            true
    );

    initializeLayout();
    initializeAnimation();
  }

  private void initializeLayout() {
    root.bindings().width(WIDTH);
    root.bindings().layoutX(LAYOUT_X);
    root.bindings().layoutY(LAYOUT_Y);
    root.bindings().opacity(1);
    root.shadow(true);
    root.bindings().rotate(ROTATION);

    attributes = initializer.initialize();
    attributes.metadataMapping().forEach((letter, metadata) -> {
      for (int i = 0; i < metadata.frequency(); i++) {
        internalState.add(letter);
      }
    });
  }

  private void initializeAnimation() {
    shake = TransitionHelper.rotate(
            root.imageView(),
            SHAKE_DURATION,
            SHAKE_BY,
            SHAKE_CYCLES,
            true
    );
    hide = TransitionHelper.translate(
            root.imageView(),
            3,
            -1 * (root.bindings().layoutX() + root.bindings().width()),
            Main.screenHeight - root.bindings().layoutY()
    );
  }

  /**
   * @return the next tile drawn from the bag. Logically
   * initialized, but the layout is uninitialized.
   */
  public Tile draw() {
    int index = (int) (Math.random() * internalState.size());
    String letter = internalState.remove(index);
    int score = attributes.metadataMapping().get(letter).score();
    return new Tile(letter, score, createVisual(letter));
  }

  private ObservableImage createVisual(String letter) {
    String url = String.format("tiles/%s.png", letter);
    ObservableImage visual =
            ObservableImage.create(url, BindingMode.BIDIRECTIONAL);
    visual.bindings().width(tileWidth);
    visual.shadow(true);
    visual.shadowColor(Color.BLACK);
    visual.bindings().opacity(1);
    return visual;
  }

  /**
   * Causes the TileBag to shake graphically, indicating
   * tiles have been dispensed.
   */
  public void shake() {
    if (shake.getStatus() == Animation.Status.RUNNING) {
      return;
    }
    shake.play();
  }

  /**
   * Causes the TileBag to slide offscreen to the bottom-
   * left corner, once all tiles have been drawn.
   */
  public void hide() {
    if (hide.getStatus() == Animation.Status.RUNNING) {
      return;
    }
    hide.play();
  }
}
