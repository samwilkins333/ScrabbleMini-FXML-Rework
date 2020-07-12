package main.java.samwilkins333.ScrabbleMini.Logic.GameAgents.Referee.Initializer;

import main.java.samwilkins333.ScrabbleMini.Logic.Computation.Generator;
import main.java.samwilkins333.ScrabbleMini.Logic.Computation.Trie;
import main.resources.ResourceCreator;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * An implementer of <code>DictionaryInitializer</code>
 * that reads the appropriate information from a text file
 * of word candidates in the project's configuration folder.
 */
public class TrieInitializer implements DictionaryInitializer<Trie> {

  @Override
  public Trie initialize() {
    Trie trie = new Trie();
    try {
      BufferedReader reader = ResourceCreator.read("ospd4.txt");
      String word;
      while ((word = reader.readLine()) != null) {
        trie.addWord(word.trim());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    Generator.Instance.setRoot(trie.getRoot());
    return trie;
  }

}
