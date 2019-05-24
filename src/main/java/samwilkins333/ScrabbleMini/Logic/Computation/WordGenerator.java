package main.java.samwilkins333.ScrabbleMini.Logic.Computation;

import main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag.Arc;
import main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag.Gaddag;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Rack.Rack;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Tiles.Tile;
import main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Word.Word;

import java.util.List;

import static main.java.samwilkins333.ScrabbleMini.Logic.GameElements.Board.BoardLayoutManager.*;

//https://github.com/ppuryear/ScrabbleTool/blob/master/src/scrabbletool/game/movegen/MoveGenerator.java

/**
 * An implementor of <code>CandidateGenerator</code>, produces
 * a list of all possible words that can be played
 * on the given board.
 */
public class WordGenerator implements
        CandidateGenerator<Word, Context<Gaddag>> {
  private Context<Gaddag> ctx;
  private int column;
  private int row;

  @Override
  public List<Word> generate(Context<Gaddag> context) {
    this.ctx = context;
    for (int c = 0; c < dimensions; c++) {
      for (int r = 0; r < dimensions; r++) {
        this.column = c;
        this.row = r;
        generate(0, null, context.rack(), new Arc());
      }
    }
    return null;
  }

  /**
   * One of two recursive backtracking co-routines
   * used to generate all possible words.
   * @param pos the offset from the anchor square
   * @param word the target word
   * @param rack the player's current rack of letters
   * @param arc the arc to be considered
   */
  private void generate(int pos, String word, Rack rack, Arc arc) {
    Tile get = ctx.board().get(column + pos, row);
    if (get != null) {
      String l = get.letter();
      follow(pos, l, word, rack, ctx.lexicon().nextArc(arc, l), arc);
    } else if (!ctx.rack().isEmpty()) {
      ctx.rack().forEach(tile -> {
//        follow(pos, tile.letter(), word, rack.c);
      });
    }
  }

  private void follow(int pos, String let, String word, Rack rack, Arc newArc, Arc old) {
    if (pos <= 0) {
      word = let == null ? let : word;
      boolean leftNeighbor = ctx.board().occupied(column + pos - 1, row);
      boolean rightNeighbor = ctx.board().occupied(column + pos + 1, row);
      if (old.has(let) && !leftNeighbor) {
        //record play
      }
      if (newArc != null) {
        if (!leftNeighbor) {
          generate(pos - 1, word, rack, newArc);
        }
        newArc = ctx.lexicon().nextArc(newArc, Gaddag.DELIMITER);
        if (newArc != null && !leftNeighbor && !rightNeighbor) {
          generate(1, word, rack, newArc);
        }
      }
    } else {
      word = word == null ? word : let;
      boolean rightNeighbor = ctx.board().occupied(column + pos + 1, row);
      if (old.has(let) && !rightNeighbor) {
        //record play
      }
      if (newArc != null && !rightNeighbor) {
        generate(pos + 1, word, rack, newArc);
      }
    }
  }
}
