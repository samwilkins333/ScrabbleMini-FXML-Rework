package main.java.samwilkins333.ScrabbleMini.Logic.Control.Referee.Initializer;

import main.resources.ResourceCreator;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
