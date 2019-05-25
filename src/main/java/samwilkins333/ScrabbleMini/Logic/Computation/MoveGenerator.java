package main.java.samwilkins333.ScrabbleMini.Logic.Computation;

import main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag.Arc;
import main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag.GADDAG;
import main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag.Letter;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Board.Board;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Board.LetterSetMapping;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Tiles.Blank;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Tiles.Tile;

import java.util.*;

//https://bit.ly/2VQNrrz

/**
 * An implementor of <code>CandidateGenerator</code>, produces
 * a list of all possible words that can be played
 * on the given board.
 */
public class MoveGenerator implements
        CandidateGenerator<Move, Context<GADDAG>> {
  private Set<Move> moves;
  private Set<String> encountered;
  private Board board;
  private GADDAG gaddag;
  private LetterSetMapping letterSets;
  private CrossSetUpdater updater = new CrossSetUpdater();

  private int anchorColumn;
  private int anchorRow;

  private boolean transposed;

  @Override
  public Set<Move> generate(Context<GADDAG> context) {
    moves = new HashSet<>();
    encountered = new HashSet<>();
    board = context.board();
    gaddag = context.lexicon();

    letterSets = new LetterSetMapping(gaddag.alphabet(), board.size());

    LinkedList<Tile> rack = new LinkedList<>(context.rack());
    LinkedList<Tile> word = new LinkedList<>();

    int size = board.size();

    if (!context.isFirstMoveMade()) {
      int mid = (int) Math.floor(size / 2.0);
      anchorColumn = mid;
      anchorRow = mid;
      generateOn(anchorColumn, word, rack, gaddag.getRootArc());
    } else {
      for (anchorColumn = 0; anchorColumn < size; anchorColumn++) {
        for (anchorRow = 0; anchorRow < size; anchorRow++) {
          if (board.horizontalNeighbors(anchorColumn, anchorRow) > 0) {
            generateOn(anchorColumn, word, rack, gaddag.getRootArc());
          }
        }
      }
    }
    return moves;
  }

  @Override
  public void update(Move move) {
    updater.update(move);
  }

  /**
   * One of two recursive backtracking co-routines
   * used to generateOn all possible words.
   * @param targetCol the offset from the anchor square
   * @param word the target word
   * @param rack the player's current rack of letters
   * @param arc the arc to be considered
   */
  private void generateOn(int targetCol, LinkedList<Tile> word, List<Tile> rack, Arc arc) {
    Tile tar = board.get(targetCol, anchorRow);
    if (tar != null) {
      Arc newArc = gaddag.getArc(arc.destination(), tar.letter());
      follow(targetCol, tar, word, rack, newArc, arc);
    } else if (!rack.isEmpty()) {
      Set<Letter> crossSet = letterSets.letters(targetCol, anchorRow);
      Set<Tile> unique = new HashSet<>(rack);
      for (Tile rackTile : unique) {
        List<Tile> newRack = remove(rack, rackTile);
        if (!(rackTile instanceof Blank)) {
          Arc newArc = gaddag.getArc(arc.destination(), rackTile.letter());
          follow(targetCol, rackTile, word, newRack, newArc, arc);
        } else if (crossSet.contains(rackTile.letter())) {
          for (Letter valid : crossSet) {
            Tile filledBlank = new Blank(valid);
            Arc newArc = gaddag.getArc(arc.destination(), valid);
            follow(targetCol, filledBlank, word, newRack, newArc, arc);
          }
        }
      }
    }
  }

  private List<Tile> remove(List<Tile> rack, Tile rackTile) {
    rack.remove(rackTile);
    rack.add(rackTile);
    return rack.subList(0, rack.size() - 1);
  }

  private void follow(int targetCol, Tile tile, LinkedList<Tile> word, List<Tile> rack, Arc newArc, Arc oldArc) {
    if (targetCol <= anchorColumn) {
      word.addFirst(tile);

      boolean noLeftNeighbors = targetCol == 0 || !board.occupied(targetCol - 1, anchorRow);

      if (oldArc.has(tile.letter()) && noLeftNeighbors) {
        collectMove(targetCol, word);
      }

      if (newArc != null) {
        if (targetCol > 0) {
          generateOn(targetCol - 1, word, rack, newArc);
        }
        Arc delimiterArc = newArc.destination().getArc(GADDAG.DELIMITER);

        if (delimiterArc != null && noLeftNeighbors && anchorColumn < board.size() - 1) {
          generateOn(targetCol + 1, word, rack, delimiterArc);
        }
      }
      word.removeFirst();
    } else {
      word.addLast(tile);

      boolean noRightNeighbors = targetCol == board.size() - 1 || !board.occupied(targetCol + 1, anchorRow);

      if (oldArc.has(tile.letter()) && noRightNeighbors) {
        collectMove(targetCol - word.size() + 1, word);
      }

      if (newArc != null && targetCol < board.size() - 1) {
        generateOn(targetCol + 1, word, rack, newArc);
      }

      word.removeLast();
    }
  }

  private void collectMove(int start, List<Tile> word) {
    Move move = new Move(transposed, anchorRow);
    for (int i = 0; i < word.size(); i++) {
      Tile wordTile = word.get(i);
      int target = start + i;
      if (target >= board.size()) {
        return;
      }
      if (board.get(target, anchorRow) == null) {
        move.addTile(wordTile, target);
      }
    }
    StringBuilder builder = new StringBuilder();
    word.forEach(tile -> builder.append(tile.letter().raw()));
//    System.out.printf("Potential play found! %s going across at (%d, %d)\n", builder.toString(), start, anchorRow);
    String found = builder.toString();
    if (gaddag.contains(found) && !encountered.contains(found)) {
      System.out.println(found);
      encountered.add(found);
      moves.add(move);
    }
  }

  /**
   * Cross-set management logic is factored into this subclass.
   */
  private class CrossSetUpdater {
    private Arc arc;
    private int row;
    private int col;

    /**
     * Updates the cross-set data for this game, given that the specified move
     * has been played on the board.
     *
     * @param move The move that was played.
     */
    public void update(Move move) {
      // If the move is across, then we'll be looking at column word-formations
      // first, so transpose the board.

      int r = move.getRowOrCol();
      int c = 0;
      for (Map.Entry<Integer, Tile> mapEntry : move.getTileMap().entrySet()) {
        // Update the cross-sets for the word formed on this column.
        c = mapEntry.getKey();
        update(c, r);
      }
    }

    /**
     * Updates the cross-sets for the word-boundary squares of the given word.
     *
     * @param r The row that this word is located on.
     * @param letterPos Any internal column of the word.
     */
    private void update(int r, int letterPos) {
      arc = gaddag.getRootArc();
      this.row = r;
      int wordEnd = findWordBoundary(r, letterPos, 1);
      col = wordEnd;

      // Travel along the GADDAG to the left word boundary.
      traverseToWordBoundary(-1);

      // Find the cross-set at the left boundary.
      if (col >= 0) {
        computeCrossSet(-1);
      }

      // Switch to the suffix sub-graph.
      arc = arc.destination().getArc(GADDAG.DELIMITER);

      // Find the cross-set at the right boundary.
      col = wordEnd + 1;
      if (col < board.size()) {
        computeCrossSet(1);
      }
    }

    /**
     * Computes the cross set at the current location in the specified
     * direction.
     */
    private void computeCrossSet(int direction) {
      Set<Letter> crossSet = letterSets.letters(col, row);
      crossSet.clear();

      // If there is no tile immediately to the (left or right, according to
      // |direction|), then the cross-set on this tile is equal to the
      // raw-set on the current arc.
      if (!board.isValidPosition(row, col + direction)
              || board.get(row, col + direction) == null) {
        if (arc != null) {
          crossSet.addAll(arc.letters());
        }
      } else {
        // Otherwise, we must try every possible raw and see if we can reach
        // the /next/ word boundary by following the GADDAG. If we can, then the
        // raw we tried is part of the cross-set.
        Arc oldArc = arc;
        int oldCol = col;
        for (Letter letter : arc.letters()) {
          arc = gaddag.getArc(arc.destination(), letter);
          traverseToWordBoundary(direction);
          if (arc != null) {
            crossSet.add(letter);
          }
          col = oldCol;
          arc = oldArc;
        }
      }
    }

    /**
     * Using the letters on the board, traverses the GADDAG until an empty
     * square is reached. If the GADDAG does not contain a path for the letters,
     * then this function will set {@code arc} to {@code null} and return
     * early.
     *
     * @param dir The direction to traverse on the board.
     */
    private void traverseToWordBoundary(int dir) {
      while (board.isValidPosition(row, col) && arc != null) {
        Tile tile = board.get(row, col);
        if (tile == null) {
          break;
        }
        arc = gaddag.getArc(arc.destination(), tile.letter());
        col += dir;
      }
    }

    private int findWordBoundary(int r, int internalPos, int dir) {
      int c = internalPos;
      while (board.isValidPosition(r, c) && board.get(r, c) != null) {
        c += dir;
      }
      return c - dir;
    }
  }
}
