package main.java.samwilkins333.ScrabbleMini.Logic.Tiles;

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

class TileOverlayStack {
  private final List<Overlay> overlays;

  private final ImageBindings tileBindings;
  private final ObservableList<Node> visibleElements;

  TileOverlayStack(ImageBindings bindings, ObservableList<Node> visibleElements) {
    this.tileBindings = bindings;
    this.visibleElements = visibleElements;

    overlays = Stream.of("success", "invalid", "failure").map(Overlay::new).collect(Collectors.toList());
  }

  void flash(OverlayType type) {
    Overlay requested = overlays.get(type.ordinal());
    visibleElements.add(requested.root.imageView());
    requested.flash.play();
  }

  private class Overlay {
    private ObservableImage root;
    private FadeTransition flash;

    Overlay(String url) {
      initializeImage(url);
      initializeFlash();
    }

    private void initializeFlash() {
      flash = TransitionHelper.flash(root.imageView(), 0.5, 1);
      flash.setOnFinished(e -> visibleElements.remove(root.imageView()));
    }

    private void initializeImage(String url) {
      String complete = String.format("tiles/overlays/%s.png", url);
      root = ObservableImage.createStandard(complete, BindingMode.NONE);

      ImageView target = root.imageView();
      tileBindings.widthBinding().bind(target.fitWidthProperty(), BindingMode.UNIDIRECTIONAL);
      tileBindings.layoutXBinding().bind(target.layoutXProperty(), BindingMode.UNIDIRECTIONAL);
      tileBindings.layoutYBinding().bind(target.layoutYProperty(), BindingMode.UNIDIRECTIONAL);
    }
  }
}
