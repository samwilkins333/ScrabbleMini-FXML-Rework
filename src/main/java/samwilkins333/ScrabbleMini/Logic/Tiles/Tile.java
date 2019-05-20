package main.java.samwilkins333.ScrabbleMini.Logic.Tiles;

import javafx.scene.Node;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.ObservableImage;

public class Tile {
  private final ObservableImage root;
  private final String letter;

  Tile(String letter, ObservableImage root) {
    this.letter = letter;
    this.root = root;
  }

  public Node node() {
    return root.imageView();
  }
}
