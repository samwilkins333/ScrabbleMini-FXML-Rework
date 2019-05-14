package main.java.samwilkins333.ScrabbleMini.Logic.Scrabble;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.ImageHelper;

import java.util.HashSet;
import java.util.Set;

import static main.java.samwilkins333.ScrabbleMini.Logic.Scrabble.Constants.*;
import static main.java.samwilkins333.ScrabbleMini.Logic.Scrabble.Constants.GRID_FACTOR;

public class Board implements VisualElement {

  private Pane boardPane = new Pane();
  private Pane textPane = new Pane();

  private final Set<Tile> placed = new HashSet<>();
  private final Set<Tile> locked = new HashSet<>();

  private final BoardSquare[][] state;

  private final Set<BoardSquare> doubleLetter = new HashSet<>();
  private final Set<BoardSquare> doubleWord = new HashSet<>();
  private final Set<BoardSquare> tripleLetter = new HashSet<>();
  private final Set<BoardSquare> tripleWord = new HashSet<>();

  public Board(int sideLength) {
    state = new BoardSquare[sideLength][sideLength];

    boardPane.getChildren().add(textPane);
    for (int column = 0; column < sideLength; column++) {
      for (int row = 0; row < sideLength; row++) {
        BoardSquare boardSquare = new BoardSquare(column, row);
        state[column][row] = boardSquare;
        boardPane.getChildren().add(boardSquare.node());
      }
    }
    this.setUpSpecialBoardSquares();
  }

  private void setUpSpecialBoardSquares() {

    // DOUBLE LETTER SCORES (BLUE)

    // Central square
    state[6][6].setID(SquareIdentity.DoubleLetterScore);
    state[6][8].setID(SquareIdentity.DoubleLetterScore);
    state[8][6].setID(SquareIdentity.DoubleLetterScore);
    state[8][8].setID(SquareIdentity.DoubleLetterScore);

    // Horizontal Outer solos
    state[3][0].setID(SquareIdentity.DoubleLetterScore);
    state[11][0].setID(SquareIdentity.DoubleLetterScore);
    state[3][14].setID(SquareIdentity.DoubleLetterScore);
    state[11][14].setID(SquareIdentity.DoubleLetterScore);

    // Vertical Outer solos
    state[0][3].setID(SquareIdentity.DoubleLetterScore);
    state[0][11].setID(SquareIdentity.DoubleLetterScore);
    state[14][3].setID(SquareIdentity.DoubleLetterScore);
    state[14][11].setID(SquareIdentity.DoubleLetterScore);

    // Lower blue trio
    state[7][11].setID(SquareIdentity.DoubleLetterScore);
    state[8][12].setID(SquareIdentity.DoubleLetterScore);
    state[6][12].setID(SquareIdentity.DoubleLetterScore);

    // Left blue trio
    state[3][7].setID(SquareIdentity.DoubleLetterScore);
    state[2][6].setID(SquareIdentity.DoubleLetterScore);
    state[2][8].setID(SquareIdentity.DoubleLetterScore);

    // Right blue trio
    state[11][7].setID(SquareIdentity.DoubleLetterScore);
    state[12][6].setID(SquareIdentity.DoubleLetterScore);
    state[12][8].setID(SquareIdentity.DoubleLetterScore);

    // Upper blue trio
    state[7][3].setID(SquareIdentity.DoubleLetterScore);
    state[6][2].setID(SquareIdentity.DoubleLetterScore);
    state[8][2].setID(SquareIdentity.DoubleLetterScore);

    // DOUBLE WORD SCORES (RED)

    // Northwest
    state[1][1].setID(SquareIdentity.DoubleWordScore);
    state[2][2].setID(SquareIdentity.DoubleWordScore);
    state[3][3].setID(SquareIdentity.DoubleWordScore);
    state[4][4].setID(SquareIdentity.DoubleWordScore);

    // Southeast
    state[10][10].setID(SquareIdentity.DoubleWordScore);
    state[11][11].setID(SquareIdentity.DoubleWordScore);
    state[12][12].setID(SquareIdentity.DoubleWordScore);
    state[13][13].setID(SquareIdentity.DoubleWordScore);

    // Southwest
    state[1][13].setID(SquareIdentity.DoubleWordScore);
    state[2][12].setID(SquareIdentity.DoubleWordScore);
    state[3][11].setID(SquareIdentity.DoubleWordScore);
    state[4][10].setID(SquareIdentity.DoubleWordScore);

    // Northeast
    state[13][1].setID(SquareIdentity.DoubleWordScore);
    state[12][2].setID(SquareIdentity.DoubleWordScore);
    state[11][3].setID(SquareIdentity.DoubleWordScore);
    state[10][4].setID(SquareIdentity.DoubleWordScore);

    // TRIPLE LETTER SCORES (GREEN)

    // Central square
    state[5][5].setID(SquareIdentity.TripleLetterScore);
    state[5][9].setID(SquareIdentity.TripleLetterScore);
    state[9][5].setID(SquareIdentity.TripleLetterScore);
    state[9][9].setID(SquareIdentity.TripleLetterScore);

    // Horizontal Outer Solos
    state[1][5].setID(SquareIdentity.TripleLetterScore);
    state[1][9].setID(SquareIdentity.TripleLetterScore);
    state[13][5].setID(SquareIdentity.TripleLetterScore);
    state[13][9].setID(SquareIdentity.TripleLetterScore);

    // Vertical Outer Solos
    state[5][1].setID(SquareIdentity.TripleLetterScore);
    state[9][1].setID(SquareIdentity.TripleLetterScore);
    state[5][13].setID(SquareIdentity.TripleLetterScore);
    state[9][13].setID(SquareIdentity.TripleLetterScore);

    // TRIPLE WORD SCORES (ORANGE)

    // Middle
    state[7][7].setID(SquareIdentity.DoubleWordScore);

    // Create and graphically add diamond image view
    _diamondViewer = ImageHelper.create("Main Theme and GUI/diamond.png");
    _diamondViewer.setCache(true);
    _diamondViewer.setPreserveRatio(true);
    _diamondViewer.setFitWidth(GRID_FACTOR - 2 * TILE_PADDING);
    _diamondViewer.setLayoutX(20 * GRID_FACTOR + 4);
    _diamondViewer.setLayoutY(10 * GRID_FACTOR + 13);
    _labelPane.getChildren().add(_diamondViewer);

    // Create and add ghost or transparent overlay square for middle of board
    int xLayout = (ZEROETH_COLUMN_OFFSET + 7) * GRID_FACTOR;
    int yLayout = (ZEROETH_ROW_OFFSET + 7) * GRID_FACTOR;
    BoardSquare ghostSquare = new BoardSquare(xLayout, yLayout, _boardPane, _labelPane);
    ghostSquare.setID(SquareIdentity.Ghost);
    ghostSquare.setUpHoverResponse(this);

    // Corners
    state[0][0].setID(SquareIdentity.TripleWordScore);
    state[14][14].setID(SquareIdentity.TripleWordScore);
    state[14][0].setID(SquareIdentity.TripleWordScore);
    state[0][14].setID(SquareIdentity.TripleWordScore);

    // Edge midpoints
    state[7][0].setID(SquareIdentity.TripleWordScore);
    state[7][14].setID(SquareIdentity.TripleWordScore);
    state[0][7].setID(SquareIdentity.TripleWordScore);
    state[14][7].setID(SquareIdentity.TripleWordScore);

    this.setUpHovers();
  }

  private void setUpHovers() {
    for (BoardSquare _specialSquare : _specialSquares) _specialSquare.setUpHoverResponse(this);
  }

  private void toFront(Tile tile) {
    ObservableList<Node> parent = boardPane.getChildren();
    parent.remove(tile.node());
    parent.add(tile.node());
  }

  @Override
  public Node node() {
    return boardPane;
  }
}
