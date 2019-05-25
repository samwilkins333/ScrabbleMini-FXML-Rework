package main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Board;

import main.java.samwilkins333.ScrabbleMini.Logic.Computation.Move;
import main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag.Letter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LetterSetMapping {
  private List<List<Set<Letter>>> mapping = new ArrayList<>();

  public LetterSetMapping(Set<Letter> alphabet, int size) {
    for (int column = 0; column < size; column++) {
      List<Set<Letter>> c = new ArrayList<>();
      mapping.add(column, c);
      for (int row = 0; row < size; row++) {
        c.add(new HashSet<>(alphabet));
      }
    }
  }

  public Set<Letter> letters(int column, int row) {
    return mapping.get(column).get(row);
  }

  public void update(Move move) {

  }
}
