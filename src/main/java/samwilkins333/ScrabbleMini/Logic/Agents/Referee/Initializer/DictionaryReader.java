package main.java.samwilkins333.ScrabbleMini.Logic.Agents.Referee.Initializer;

import main.resources.ResourceCreator;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * An implementer of <code>DictionaryInitializer</code>
 * that reads the appropriate information from a text file
 * of word candidates in the project's configuration folder.
 */
public class DictionaryReader implements DictionaryInitializer {

  @Override
  public Set<String> initialize() {
    Set<String> dictionary = new HashSet<>();
    try {
      BufferedReader reader = ResourceCreator.read("ospd4.txt");
      String word;
      while ((word = reader.readLine()) != null) {
        dictionary.add(word.trim());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return dictionary;
  }
}
