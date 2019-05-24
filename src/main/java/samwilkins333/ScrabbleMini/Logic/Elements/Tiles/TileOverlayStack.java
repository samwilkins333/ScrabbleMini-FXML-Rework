package main.java.samwilkins333.ScrabbleMini.Logic.Elements.Tiles;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.BindingMode;
import main.java.samwilkins333.ScrabbleMini.FXML.Scenes.Bindings.Composite.ImageBindings;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.ObservableImage;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.TransitionHelper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Contains the series of <code>ImageView</code>
 * overlays that flash to indicate the outcome of
 * attempting to play a tile, as well as the
 * mechanism for invoking the flash.
 */
class TileOverlayStack {
  private final List<Overlay> overlays;

  private final ImageBindings tileBindings;
  private final ObservableList<Node> visibleElements;

  private static final double DURATION = 0.5;

  TileOverlayStack(ImageBindings bindings, ObservableList<Node> visible) {
    this.tileBindings = bindings;
    this.visibleElements = visible;

    overlays = Stream.of("success", "invalid", "failure")
            .map(Overlay::new).collect(Collectors.toList());
  }

  void flash(OverlayType type) {
    Overlay requested = overlays.get(type.ordinal());
    if (requested.flash.getStatus() == Animation.Status.RUNNING) {
      return;
    }
    visibleElements.add(requested.root.imageView());
    requested.flash.play();
  }

  /**
   * A class that stores a pair, of the
   * <code>ObservableImage</code> of the overlay
   * and the <code>FadeTransition</code> flash
   * associated with it.
   */
  private class Overlay {
    private ObservableImage root;
    private FadeTransition flash;

    Overlay(String url) {
      initializeImage(url);
      initializeFlash();
    }

    private void initializeFlash() {
      flash = TransitionHelper.flash(root.imageView(), DURATION, 1);
      flash.setOnFinished(e -> visibleElements.remove(root.imageView()));
    }

    private void initializeImage(String url) {
      String complete = String.format("tiles/overlays/%s.png", url);
      root = ObservableImage.create(complete, BindingMode.NONE);

      ImageView target = root.imageView();
      tileBindings.widthBinding()
              .bind(target.fitWidthProperty(), BindingMode.UNIDIRECTIONAL);
      tileBindings.layoutXBinding()
              .bind(target.layoutXProperty(), BindingMode.UNIDIRECTIONAL);
      tileBindings.layoutYBinding()
              .bind(target.layoutYProperty(), BindingMode.UNIDIRECTIONAL);
    }
  }
}
