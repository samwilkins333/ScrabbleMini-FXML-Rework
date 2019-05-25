package main.java.samwilkins333.ScrabbleMini.Logic.GameAgents.Referee.Initializer;

import main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag.GADDAG;
import main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag.Letter;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Tiles.Initializer.TileMetaData;
import main.resources.ResourceCreator;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * An implementer of <code>DictionaryInitializer</code>
 * that reads the appropriate information from a text file
 * of word candidates in the project's configuration folder.
 */
public class GaddagInitializer implements DictionaryInitializer<GADDAG> {
  private final Map<String, TileMetaData> metaDataMap;

  /**
   * Constructor.
   * @param metaDataMap the mapping from string letters to metadata
   *                    regarding score and frequency of tiles of a given raw
   */
  public GaddagInitializer(Map<String, TileMetaData> metaDataMap) {
    this.metaDataMap = metaDataMap;
  }

  @Override
  public GADDAG initialize() {
    Set<Letter> alphabet = new TreeSet<>();
    metaDataMap.forEach((s, m) -> {
      if (!s.equals("_")) {
        alphabet.add(new Letter(s, m.score()));
      }
    });
    GADDAG gaddag = new GADDAG(alphabet);
    try {
      BufferedReader reader = ResourceCreator.read("ospd4.txt");
      String word;
      while ((word = reader.readLine()) != null) {
        word = word.trim();
        gaddag.add(word, fromString(word));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return gaddag;
  }

  private List<Letter> fromString(String word) {
    List<Letter> letters = new ArrayList<>(word.length());
    for (char c : word.toCharArray()) {
      String l = String.valueOf(c);
      letters.add(new Letter(l, metaDataMap.get(l).score()));
    }
    return letters;
  }
}
