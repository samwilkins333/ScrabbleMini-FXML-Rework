package main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Models a progression of letters, each of which
 * contains a valid raw set.
 */
public class Arc {
  private final State destination;
  private Set<Letter> letterSet = new TreeSet<>();

  /**
   * Constructor.
   * @param destination the destination
   *                    state of this arc
   */
  public Arc(State destination) {
    this.destination = destination;
  }

  public void add(Letter letter) {
    letterSet.add(letter);
  }

  public boolean has(Letter letter) {
    return letterSet.contains(letter);
  }

  public Set<Letter> letters() {
    return Collections.unmodifiableSet(letterSet);
  }

  public State destination() {
    return destination;
  }
}
