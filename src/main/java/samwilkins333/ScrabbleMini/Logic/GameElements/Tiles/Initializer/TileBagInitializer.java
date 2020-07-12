package main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Tiles.Initializer;

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
    private Map<Character, TileMetaData> metadataMapping;

    TileBagAttributes(Map<Character, TileMetaData> metadata) {
      this.metadataMapping = metadata;
    }

    public Map<Character, TileMetaData> metadataMapping() {
      return metadataMapping;
    }
  }

}
