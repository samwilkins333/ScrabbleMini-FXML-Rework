package main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Initializer;

import java.util.Map;

public interface TileBagInitializer {
  TileBagAttributes initialize();

  class TileBagAttributes {
    private Map<String, TileMetaData> metadata;

    TileBagAttributes(Map<String, TileMetaData> metadata) {
      this.metadata = metadata;
    }

    public TileMetaData request(String letter) {
      return metadata.get(letter);
    }
  }

}
