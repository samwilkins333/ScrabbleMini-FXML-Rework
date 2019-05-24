package main.java.samwilkins333.ScrabbleMini.Logic.GameAgents.Referee.Initializer;

import main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag.Gaddag;
import main.resources.ResourceCreator;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * An implementer of <code>DictionaryInitializer</code>
 * that reads the appropriate information from a text file
 * of word candidates in the project's configuration folder.
 */
public class GaddagInitializer implements DictionaryInitializer<Gaddag> {
  private final String delimiter;

  /**
   * Constructor.
   * @param delimiter the delimiter that will be used
   *                  to separate reversed prefixes and suffixes
   *                  in the generated <code>Gaddag</code>
   */
  public GaddagInitializer(String delimiter) {
    this.delimiter = delimiter;
  }

  @Override
  public Gaddag initialize() {
    Gaddag gaddag = new Gaddag(delimiter);
    try {
      BufferedReader reader = ResourceCreator.read("ospd4.txt");
      String word;
      while ((word = reader.readLine()) != null) {
        gaddag.add(word.trim());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return gaddag;
  }
}
