package main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag;

import java.util.*;

/**
 * A specialized Trie data structure
 * that calculates valid board moves
 * extremely efficiently.
 */
public class GADDAG extends HashSet<String> {
  public static final ArcLetter DELIMITER = new Delimiter();
  private final Set<Letter> alphabet;
  private Arc rootArc;
  private Map<Letter, ArcLetter> alphabetMap;

  /**
   * Constructor.
   * @param alphabet all possible letters given from configuration
   */
  public GADDAG(Set<Letter> alphabet) {
    this.alphabet = alphabet;
    rootArc = new Arc(new State());
    alphabetMap = new TreeMap<>();
  }

  public Set<Letter> alphabet() {
    return alphabet;
  }

  public class WordSizeException extends RuntimeException {
    public WordSizeException(List<Letter> word) {
      super(String.format("%s has fewer than two characters.", word));
    }
  }

  public Arc getRootArc() {
    return rootArc;
  }

  public Arc getArc(State state, Letter letter) {
    return state.getArc(alphabetMap.get(letter));
  }

  public void add(String raw, List<Letter> word) {
    int wordSize = word.size();

    if (wordSize < 2) {
      throw new WordSizeException(word);
    }

    List<ArcLetter> gaddagWord = toGaddagWord(word);

    addPath(gaddagWord.subList(2, wordSize + 1), word.get(0));
    State state = addPath(gaddagWord.subList(0, wordSize), word.get(wordSize - 1));

    for (int m = wordSize - 1; m >= 2; m--) {
      State forced = state;
      state = addPath(gaddagWord.subList(0, m), null);
      Arc forcedArc = state.forceArc(gaddagWord.get(m), forced);

      if (m == wordSize - 1) {
        forcedArc.add(word.get(wordSize - 1));
      }
    }

    super.add(raw);
  }

  private List<ArcLetter> toGaddagWord(List<Letter> word) {
    List<ArcLetter> gaddagWord = new ArrayList<>(word.size() + 1);
    gaddagWord.add(DELIMITER);
    for (Letter letter : word) {
      ArcLetter gaddagLetter = alphabetMap.get(letter);
      if (gaddagLetter == null) {
        gaddagLetter = new ArcLetter(letter);
        alphabetMap.put(letter, gaddagLetter);
      }
      gaddagWord.add(gaddagLetter);
    }
    return gaddagWord;
  }

  private State addPath(List<ArcLetter> letters, Letter forLetterSet) {
    Arc arc = rootArc;
    for (int i = letters.size() - 1; i >= 0; i--) {
      arc = arc.destination().addArc(letters.get(i));
    }
    if (forLetterSet != null) {
      arc.add(forLetterSet);
    }
    return arc.destination();
  }
}
