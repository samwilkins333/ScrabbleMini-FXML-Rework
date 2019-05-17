package main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Initializer;

import main.resources.ResourceCreator;

import java.io.BufferedReader;
import java.io.IOException;

public class TileReader implements TileBagInitializer {
  @Override
  public TileBagAttributes initialize() {
    try {
      BufferedReader reader = ResourceCreator.read("tilebag_config.txt");
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
    return new TileBagAttributes();
  }
}
