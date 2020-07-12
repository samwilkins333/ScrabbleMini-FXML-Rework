package main.java.samwilkins333.ScrabbleMini.Logic.Computation;

public class Trie {

  private TrieNode root = new TrieNode(TrieNode.ROOT, null, null);
  private int wordCountState = 0;
  private int nodeCountState = 0;
  public static final char DELIMITER = '#';

  public void clear() {
    this.root = new TrieNode(TrieNode.ROOT, null, null);
  }

  public TrieNode getRoot() {
    return root;
  }

  public int getWordCountState() {
    return wordCountState;
  }

  public int getNodeCountState() {
    return nodeCountState;
  }

  public boolean includes(String word) {
    TrieNode node = this.root;
    char[] letters = word.toCharArray();
    int count = word.length();

    int i;
    TrieNode childNode;
    for (i = 0; i < count; i++) {
      if ((childNode = node.getChild(letters[i])) == null) {
        break;
      }
      node = childNode;
    }
    return i == count && node.getTerminal();
  }

  public void addWord(String word) {
    if (!word.matches("^[a-z]+$")) {
      throw new Error(String.format("%s is malformed.", word));
    }
    char[] letters = word.toCharArray();
    if (this.addNodes(letters)) {
      this.wordCountState++;
    }

    int count = letters.length;
    if (count > 1) {
      int currentIndex = count - 1;
      char[] variation = new char[count + 1];
      System.arraycopy(word.substring(1).toCharArray(), 0, variation, 0, currentIndex);
      variation[currentIndex] = DELIMITER;
      variation[count] = letters[0];
      while (currentIndex > 0) {
        this.addNodes(variation);
        variation[currentIndex--] = variation[0];
        if (currentIndex >= 0) System.arraycopy(variation, 1, variation, 0, currentIndex);
        variation[currentIndex] = DELIMITER;
      }
    }
  }

  private boolean addNodes(char[] letters) {
    boolean terminal = false;
    TrieNode node = this.root;
    TrieNode childNode;
    int count = letters.length;
    for (int i = 0; i < count; i++) {
      terminal = i + 1 == count;
      if ((childNode = node.getChild(letters[i])) == null) {
        node = node.addChild(letters[i], terminal);
        this.nodeCountState++;
      } else {
        if ((terminal &= !childNode.getTerminal())) {
          childNode.setTerminal(true);
        }
        node = childNode;
      }
    }
    return terminal;
  }

}
