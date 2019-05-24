package main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A specialized Trie data structure
 * that calculates valid board moves
 * extremely efficiently.
 */
public class Gaddag extends HashSet<String> {
  public final String delimiter;
  private final Set<String> raw;

  /**
   * Constructor.
   * @param delimiter the delimiter used to separate
   *                  reversed prefixes and suffixes
   */
  public Gaddag(String delimiter) {
    this.raw = new HashSet<>();
    this.delimiter = delimiter;
  }

  @Override
  public boolean contains(Object o) {
    return raw.contains(o);
  }

  @Override
  public boolean add(String word) {
    boolean success = true;
    Set<String> representations = new HashSet<>();
    for (int i = 1; i < word.length() + 1; i++) {
      String rev = new StringBuilder(word.substring(0, i)).reverse().toString();
      String result = rev + delimiter + word.substring(i);
      representations.add(result);
      success &= super.add(result);
    }
    if (!success) {
      representations.forEach(super::remove);
    } else {
      raw.add(word);
    }
    return success;
  }

  @Override
  public boolean addAll(Collection<? extends String> c) {
    boolean success = true;
    for (String w : c) {
      success &= add(w);
    }
    return success;
  }

  /**
   * Retrieves the next arc associated
   * with the given arc via the given letter.
   * @param arc the reference arc
   * @param l the associated letter
   * @return the next arc pointed to by the reference and letter pair
   */
  public Arc getArc(Arc arc, String l) {
    return null;
  }
}
