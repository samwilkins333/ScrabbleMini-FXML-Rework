package main.java.samwilkins333.ScrabbleMini.Logic.Tiles.Initializer;

import java.util.Map;

public interface TileBagInitializer {
  TileBagAttributes initialize();

  class TileBagAttributes {
    private Map<String, TileMetaData> metadataMapping;

    TileBagAttributes(Map<String, TileMetaData> metadata) {
      this.metadataMapping = metadata;
    }

    public Map<String, TileMetaData> metadataMapping() {
      return metadataMapping;
    }
  }

}
