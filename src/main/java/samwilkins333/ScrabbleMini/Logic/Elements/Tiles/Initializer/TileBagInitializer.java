package main.java.samwilkins333.ScrabbleMini.Logic.Elements.Tiles.Initializer;

import java.util.Map;

/**
 * A functional interface that specifies any entity that
 * can initialize a <code>TileBag</code> by returning
 * meaningful <code>TileBagAttributes</code>.
 */
public interface TileBagInitializer {

  /**
   * Initializes the TileBag.
   * @return the <code>TileBagAttributes</code>
   * populated during initialization
   */
  TileBagAttributes initialize();

  /**
   * A wrapper around the mappings and information
   * needed to initialize a TileBag.
   */
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
