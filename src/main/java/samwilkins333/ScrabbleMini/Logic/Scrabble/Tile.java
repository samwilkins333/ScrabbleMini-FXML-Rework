package main.java.samwilkins333.ScrabbleMini.Logic.Scrabble;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.effect.*;
import javafx.event.*;
import javafx.scene.input.*;

import java.util.*;

import javafx.animation.*;
import javafx.util.Duration;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.ImageHelper;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.TransitionHelper;

import static main.java.samwilkins333.ScrabbleMini.Logic.Scrabble.Constants.*;

class Tile implements VisualElement {
	private final String _letter;
	private final ImageView tileViewer;
	private Pane _boardPane;

	private final int _value;
	private int initialX;
	private int initialY;

	private double actualLayoutX;
	private double actualLayoutY;

	private double lastDragEventPositionX;
	private double lastDragEventPositionY;

	private int _xIndex;
	private int _yIndex;

	private ScrabbleGame _scrabbleGame;
	private Word _newestWord;
	private PlayerNum _tileAffiliation;
	private Playable _currentPlayer;

	private List<ImageView> displays = new ArrayList<>();

	private FadeTransition _addedFlash;
	private FadeTransition _failedFlash;
	private FadeTransition _partialFlash;
	private FadeTransition _overlapFlash;
	private ScaleTransition _overlapScale;

	private interface FlashRoutine { void flash(); }
	private Map<WordAddition, FlashRoutine> flashes = new HashMap<>();

	private Pane _root;

	private Boolean _flashable;

	private DropShadow _pieceShadow;


	Tile(int letter) {
		// Create stock new tile image view
		TileData t = TILE_DATA.get(letter);

		_letter = t.getLetter();
		_value = t.getValue();

		tileViewer = new ImageView(t.getImage());
		tileViewer.setFitWidth(GRID_FACTOR - (TILE_PADDING * 2));
		tileViewer.setPreserveRatio(true);
		tileViewer.setCache(true);

		tileViewer.setOnMousePressed(this.pressMouse());
		tileViewer.setOnMouseDragged(this.dragMouse());
		tileViewer.setOnMouseReleased(this.releaseMouse());

		displays.add(tileViewer);

		// Set its default properties

		_flashable = false;

		_xIndex = -1;
		_yIndex = -1;

		_tileAffiliation = PlayerNum.Neither;
		_currentPlayer = null;

		this.addShadow();
		this.setUpOverlapFlash();
	}
	
	void fadeOut() {
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), tileViewer);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.play();
	}

	private void addRoot() {
		_root = _scrabbleGame.getRoot();
	}

	private Point2D center() {
		double halfTileWidth = tileViewer.getFitWidth() / 2;
		return new Point2D(
						actualLayoutX + halfTileWidth,
						actualLayoutY + halfTileWidth
		);
	}

	private Boolean validate(int column, int row) {
		if (column < 0 || column > 15 || row < 0 || row > 15) return false;
		return !_scrabbleGame.boardSquareOccupiedAt(column, row);
	}

	private Boolean isDraggable() {
		if (!_scrabbleGame.gameIsPlaying() || _scrabbleGame.getReferee().isThinking()) return false;

		this.refreshPlayerInfo();

		if (_tileAffiliation == _currentPlayer.getPlayerNumber()) return !_scrabbleGame.played().contains(Tile.this);
		return false;
	}

	private void setUpOverlapFlash() {
		_overlapFlash = TransitionHelper.flash(tileViewer, FEEDBACK_FLASH_DURATION);
		_overlapScale = TransitionHelper.scale(tileViewer, FEEDBACK_FLASH_DURATION, 0.2, 0.2);
	}

	private EventHandler<MouseEvent> pressMouse() {
		return event -> {
			if (!Tile.this.isDraggable()) return;

			Tile.this.toFront();

			_overlapFlash.stop();
			_overlapScale.stop();

			tileViewer.setScaleX(1);
			tileViewer.setScaleY(1);
			tileViewer.setOpacity(1.0);
			tileViewer.setEffect(_pieceShadow);

			if (event.getButton() == MouseButton.PRIMARY) {
				// get the current mouse coordinates according to the scene.
				lastDragEventPositionX = event.getSceneX();
				lastDragEventPositionY = event.getSceneY();

				// get the current coordinates of the draggable boardPane.
				actualLayoutX = tileViewer.getLayoutX();
				actualLayoutY = tileViewer.getLayoutY();
			}
		};
	}

	private EventHandler<MouseEvent> dragMouse() {
		return e -> {
			if (!(Tile.this.isDraggable() && e.getButton() == MouseButton.PRIMARY)) return;

			double newX = e.getSceneX();
			double newY = e.getSceneY();

			// find the delta coordinates by subtracting the new mouse
			// coordinates with the old.
			double deltaX = newX - lastDragEventPositionX;
			double deltaY = newY - lastDragEventPositionY;

			// add the delta coordinates to the boardPane coordinates.
			actualLayoutX += deltaX;
			actualLayoutY += deltaY;

			// set the layout for the draggable boardPane.
			tileViewer.setLayoutX(actualLayoutX);
			tileViewer.setLayoutY(actualLayoutY);

			// get the latest mouse coordinate.
			lastDragEventPositionX = newX;
			lastDragEventPositionY = newY;
		};
	}

	private EventHandler<MouseEvent> releaseMouse() {
		return event -> {
 			if (!Tile.this.isDraggable()) return;

 			Point2D quantizedDrop = GridManager.pixelsToGrid(center());
 			int column = (int) quantizedDrop.getX();
 			int row = (int) quantizedDrop.getY();

			if (validate(column, row) && event.getClickCount() == 1)
				placeAtIndices(column, row);
			else {
				reset();
				return;
			}

			_newestWord.checkAddedTiles();
			// If all temp user-dragged tiles have been manually removed, ensure that no played tiles that had been added as adjacents linger in current word
			if (_newestWord.containsOnlyAddedTiles()) _newestWord.clear();
		};
	}

	void setToOpaque() {
		tileViewer.setOpacity(1.0);
	}

	private void refreshPlayerInfo() {
		_currentPlayer = _scrabbleGame.getReferee().getCurrentPlayerInstance();
		_newestWord = _currentPlayer.getNewestWord();
	}

	private void addShadow() {
		_pieceShadow = new DropShadow();
		_pieceShadow.setRadius(120);
		_pieceShadow.setOffsetX(4);
		_pieceShadow.setOffsetY(4);
		_pieceShadow.setColor(SHADOW_FILL);
		_pieceShadow.setSpread(0.0);
		_pieceShadow.setHeight(25);
		_pieceShadow.setWidth(25);
		_pieceShadow.setBlurType(BlurType.GAUSSIAN);
		tileViewer.setEffect(_pieceShadow);
	}

	void add(Pane boardPane, double x, double y, ScrabbleGame thisGame, PlayerNum tileAffiliation) {
		initialX = (int) x;
		initialY = (int) y;

		_scrabbleGame = thisGame;

		this.initializeOverlays();
		_flashable = true;

		this.addRoot();

		_tileAffiliation = tileAffiliation;

		tileViewer.setLayoutX(x * GRID_FACTOR + TILE_PADDING);
		tileViewer.setLayoutY(y * GRID_FACTOR + TILE_PADDING);

		_boardPane = boardPane;
		_boardPane.getChildren().add(tileViewer);
	}

	void placeAtIndices(int xIndex, int yIndex) {
		int targetX = (ZEROETH_COLUMN_OFFSET + xIndex) * GRID_FACTOR + TILE_PADDING;
		int targetY = (ZEROETH_ROW_OFFSET + yIndex) * GRID_FACTOR + TILE_PADDING;

		// update internal indices
		for (ImageView d : displays) {
			d.setLayoutX(targetX);
			d.setLayoutY(targetY);
		}

		_xIndex = xIndex;
		_yIndex = yIndex;

		// add to newest word if not present
		if (!_newestWord.contains(Tile.this)) {
			_newestWord.add(Tile.this);
		}

		if (_newestWord.occupies(Tile.this, _xIndex, _yIndex)) {
			_overlapFlash.play();
			_overlapScale.play();
			tileViewer.setEffect(null);
		}
	}

	void reset() {
		int resetX = initialX * GRID_FACTOR + TILE_PADDING;
		int resetY = initialY * GRID_FACTOR + TILE_PADDING;

		for (ImageView d : displays) {
			d.setLayoutX(resetX);
			d.setLayoutY(resetY);
		}

		_xIndex = -1;
		_yIndex = -1;

		_overlapFlash.stop();
		_overlapScale.stop();

		tileViewer.setScaleX(1);
		tileViewer.setScaleY(1);
		tileViewer.setOpacity(1.0);
		tileViewer.setEffect(_pieceShadow);
	}

	private void initializeOverlays() {
		String[] resources = new String[] { "greencheck", "redx", "yellowminus" };

		for (int i = 0; i < 3; i++) {
			ImageView view = new ImageView(ImageHelper.create("Interaction Feedback/" + resources[i]));
			view.setFitWidth(GRID_FACTOR - (TILE_PADDING * 2));
			view.setLayoutX(GRID_FACTOR * 5);
			view.setLayoutY(GRID_FACTOR * 4);
			view.setOpacity(0);
			view.setCache(true);
			view.setPreserveRatio(true);
			view.setLayoutX(initialX * GRID_FACTOR + TILE_PADDING);
			view.setLayoutY(initialY * GRID_FACTOR + TILE_PADDING);
			displays.add(view);

			FadeTransition flash = new FadeTransition(Duration.seconds(FEEDBACK_FLASH_DURATION), view);
			flash.setFromValue(1.0);
			flash.setToValue(0.0);
			flash.setAutoReverse(false);
			flash.setCycleCount(1);
			flash.setOnFinished((e) -> _root.getChildren().remove(view));

			flashes.put(WordAddition.values()[i], () -> {
				if (_flashable) return;
				_flashable = true;
				_root.getChildren().add(view);
				flash.play();
			});
		}
	}

	void playFlash(WordAddition outcome) { flashes.get(outcome).flash(); }

	@Override
	public Node node() { return tileViewer; }

	void initialize(int x, int y) {
		initialX = x;
		initialY = y;

		setImageViewLoc(tileViewer, x, y);
		setImageViewLoc(_checkViewer, x, y);
		setImageViewLoc(_minusViewer, x, y);
		setImageViewLoc(_xViewer, x, y);

		_xIndex = -1;
		_yIndex = -1;
	}
	
	ImageView getCheckViewer() {
		return _checkViewer;
	}
	
	void setImageViewLoc(ImageView view, double x, double y) {
		view.setLayoutX(x * GRID_FACTOR + TILE_PADDING);
		view.setLayoutY(y * GRID_FACTOR + TILE_PADDING);
	}

	int getY() {
		return initialY;
	}

	int getXIndex() {
		return _xIndex;
	}

	int getYIndex() {
		return _yIndex;
	}

	ImageView getTileViewer() {
		return tileViewer;
	}

	int getValue() {
		return _value;
	}

	void hide() {
		tileViewer.setOpacity(0);
	}

	String getLetter() {
		return _letter;
	}

	void stopOverlapFlash() {
		_overlapFlash.stop();
		_overlapScale.stop();
		tileViewer.setScaleX(1);
		tileViewer.setScaleY(1);
		tileViewer.setOpacity(1.0);
	}

	void resetShadow() {
		tileViewer.setEffect(_pieceShadow);
	}

	/**
	 * Used during the shuffling of the tiles
	 * while still on the rack.
	 * @param increment the amount by which to offset the tile,
	 *                  either positive or negative
	 */
	void verticalShift(int increment) {
		if (_xIndex >= 0 || _yIndex >= 0) return;

		initialY += increment;
		double computed = initialY * GRID_FACTOR + TILE_PADDING;
		displays.forEach(d -> d.setLayoutY(computed));
	}
	
	boolean isOnBoard() {
		return _scrabbleGame.played().contains(Tile.this);
	}
}