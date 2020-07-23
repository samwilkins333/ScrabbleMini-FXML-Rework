package main.java.samwilkins333.ScrabbleMini.Logic.GameAgents.Referee.Initializer;

import com.swilkins.ScrabbleBase.Generation.Generator;
import com.swilkins.ScrabbleBase.Vocabulary.Trie;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Rack.RackView;
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
    Generator.setRoot(trie.getRoot());
    Generator.setRackCapacity(RackView.CAPACITY);
    return trie;
  }

}
