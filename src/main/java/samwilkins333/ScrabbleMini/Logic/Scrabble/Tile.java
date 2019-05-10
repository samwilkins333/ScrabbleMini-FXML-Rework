package main.java.samwilkins333.ScrabbleMini.Logic.Scrabble;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.effect.*;
import javafx.event.*;
import javafx.scene.input.*;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.*;
import javafx.util.Duration;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.ImageHelper;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.TransitionHelper;

import static main.java.samwilkins333.ScrabbleMini.Logic.Scrabble.Constants.*;

class Tile {
	private final String _letter;
	private final ImageView _tileViewer;
	private Pane _boardPane;

	private final int _value;
	private int initialX;
	private int initialY;

	private double _currentNodeX;
	private double _currentNodeY;

	private double _mouseDragX;
	private double _mouseDragY;

	private int _xIndex;
	private int _yIndex;

	private ScrabbleGame _scrabbleGame;
	private Word _newestWord;
	private PlayerNum _tileAffiliation;
	private Playable _currentPlayer;

	private ImageView _checkViewer;
	private ImageView _xViewer;
	private ImageView _minusViewer;
	private List<ImageView> displays = new ArrayList<>();

	private FadeTransition _addedFlash;
	private FadeTransition _failedFlash;
	private FadeTransition _partialFlash;
	private FadeTransition _overlapFlash;
	private ScaleTransition _overlapScale;

	private Pane _root;

	private Boolean _flashable;

	private DropShadow _pieceShadow;

	Tile(int letter) {
		// Create stock new tile image view
		TileData t = TILE_DATA.get(letter);

		_letter = t.getLetter();
		_value = t.getValue();

		_tileViewer = new ImageView(t.getImage());
		_tileViewer.setFitWidth(GRID_FACTOR - (TILE_PADDING * 2));
		_tileViewer.setPreserveRatio(true);
		_tileViewer.setCache(true);

		displays.add(_tileViewer);

		// Set its default properties

		_flashable = false;

		_xIndex = -1;
		_yIndex = -1;

		_tileAffiliation = PlayerNum.Neither;
		_currentPlayer = null;

		this.addShadow();
		this.setUpOverlapFlash();
		this.setUpDraggable();
	}
	
	void fadeOut() {
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), _tileViewer);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.play();
	}

	private void addRoot() {
		_root = _scrabbleGame.getRoot();
	}

	private Point2D center() {
		return new Point2D(
						_tileViewer.getLayoutX() + _tileViewer.getFitWidth() / 2,
						_tileViewer.getLayoutY() + _tileViewer.getFitWidth() / 2
		);
	}

	private void setUpDraggable() {
		_tileViewer.setOnMousePressed(this.pressMouse());
		_tileViewer.setOnMouseDragged(this.dragMouse());
		_tileViewer.setOnMouseReleased(this.releaseMouse());
	}

	private Boolean validate(int col, int row) {
		if (col < 0 || col > 15 || row < 0 || row > 15) return false;
		return !_scrabbleGame.boardSquareOccupiedAt(col, row);
	}

	private void toFront() {
		ObservableList<Node> parent = _boardPane.getChildren();
		parent.remove(_tileViewer);
		parent.add(_tileViewer);
	}

	private Boolean isDraggable() {
		if (!_scrabbleGame.gameIsPlaying() || _scrabbleGame.getReferee().isThinking()) return false;

		this.refreshPlayerInfo();

		if (_tileAffiliation == _currentPlayer.getPlayerNumber()) return !_scrabbleGame.played().contains(Tile.this);
		return false;
	}

	private void setUpOverlapFlash() {
		_overlapFlash = TransitionHelper.flash(_tileViewer, FEEDBACK_FLASH_DURATION);
		_overlapScale = TransitionHelper.scale(_tileViewer, FEEDBACK_FLASH_DURATION, 0.2, 0.2);
	}

	private EventHandler<MouseEvent> pressMouse() {
		return event -> {
			if (!Tile.this.isDraggable()) return;

			Tile.this.toFront();

			_overlapFlash.stop();
			_overlapScale.stop();

			_tileViewer.setScaleX(1);
			_tileViewer.setScaleY(1);
			_tileViewer.setOpacity(1.0);
			_tileViewer.setEffect(_pieceShadow);

			if (event.getButton() == MouseButton.PRIMARY) {
				// get the current mouse coordinates according to the scene.
				_mouseDragX = event.getSceneX();
				_mouseDragY = event.getSceneY();

				// get the current coordinates of the draggable node.
				_currentNodeX = _tileViewer.getLayoutX();
				_currentNodeY = _tileViewer.getLayoutY();
			}
		};
	}

	private EventHandler<MouseEvent> dragMouse() {
		return event -> {
			if (!Tile.this.isDraggable()) return;

			if (event.getButton() == MouseButton.PRIMARY) {
				// find the delta coordinates by subtracting the new mouse
				// coordinates with the old.
				double deltaX = event.getSceneX() - _mouseDragX;
				double deltaY = event.getSceneY() - _mouseDragY;

				// add the delta coordinates to the node coordinates.
				_currentNodeX += deltaX;
				_currentNodeY += deltaY;

				// set the layout for the draggable node.
				_tileViewer.setLayoutX(_currentNodeX);
				_tileViewer.setLayoutY(_currentNodeY);

				// get the latest mouse coordinate.
				_mouseDragX = event.getSceneX();
				_mouseDragY = event.getSceneY();
			}
		};
	}

	private EventHandler<MouseEvent> releaseMouse() {
		return event -> {
 			if (!Tile.this.isDraggable()) return;

 			Point2D drop = center();
			int snappedX = (int) ((drop.getX() - ORIGIN_LEFT) / GRID_FACTOR);
			int snappedY = (int) ((drop.getY() - ORIGIN_TOP) / GRID_FACTOR);

			if (validate(snappedX, snappedY) && event.getClickCount() == 1)
				placeAtIndices(snappedX, snappedY);
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
		_tileViewer.setOpacity(1.0);
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
		_tileViewer.setEffect(_pieceShadow);
	}

	void add(Pane boardPane, double x, double y, ScrabbleGame thisGame, PlayerNum tileAffiliation) {
		initialX = (int) x;
		initialY = (int) y;

		_scrabbleGame = thisGame;

		this.setUpFlash();
		_flashable = true;

		this.addRoot();

		_tileAffiliation = tileAffiliation;

		_tileViewer.setLayoutX(x * GRID_FACTOR + TILE_PADDING);
		_tileViewer.setLayoutY(y * GRID_FACTOR + TILE_PADDING);

		_boardPane = boardPane;
		_boardPane.getChildren().add(_tileViewer);
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
			_tileViewer.setEffect(null);
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

		_tileViewer.setScaleX(1);
		_tileViewer.setScaleY(1);
		_tileViewer.setOpacity(1.0);
		_tileViewer.setEffect(_pieceShadow);
	}

	private void setUpFlash() {
		_checkViewer = new ImageView(ImageHelper.create("Interaction Feedback/greencheck.png"));
		_checkViewer.setFitWidth(GRID_FACTOR - (TILE_PADDING * 2));
		_checkViewer.setLayoutX(GRID_FACTOR * 5);
		_checkViewer.setLayoutY(GRID_FACTOR * 4);
		_checkViewer.setOpacity(0);
		_checkViewer.setCache(true);
		_checkViewer.setPreserveRatio(true);
		_checkViewer.setLayoutX(initialX * GRID_FACTOR + TILE_PADDING);
		_checkViewer.setLayoutY(initialY * GRID_FACTOR + TILE_PADDING);

		displays.add(_checkViewer);

		_addedFlash = new FadeTransition(Duration.seconds(FEEDBACK_FLASH_DURATION), _checkViewer);
		_addedFlash.setFromValue(1.0);
		_addedFlash.setToValue(0.0);
		_addedFlash.setAutoReverse(false);
		_addedFlash.setCycleCount(1);
		_addedFlash.setOnFinished(new RemoveIconsHandler(WordAddition.Success));

		_xViewer = new ImageView(ImageHelper.create("Interaction Feedback/redx.png"));
		_xViewer.setFitWidth(GRID_FACTOR - (TILE_PADDING * 2));
		_xViewer.setLayoutX(GRID_FACTOR * 5);
		_xViewer.setLayoutY(GRID_FACTOR * 4);
		_xViewer.setOpacity(0);
		_xViewer.setCache(true);
		_xViewer.setPreserveRatio(true);
		_xViewer.setLayoutX(initialX * GRID_FACTOR + TILE_PADDING);
		_xViewer.setLayoutY(initialY * GRID_FACTOR + TILE_PADDING);

		displays.add(_xViewer);

		_failedFlash = new FadeTransition(Duration.seconds(FEEDBACK_FLASH_DURATION), _xViewer);
		_failedFlash.setFromValue(1.0);
		_failedFlash.setToValue(0.0);
		_failedFlash.setAutoReverse(false);
		_failedFlash.setCycleCount(1);
		_failedFlash.setOnFinished(new RemoveIconsHandler(WordAddition.Failure));

		_minusViewer = new ImageView(ImageHelper.create("Interaction Feedback/yellowminus.png"));
		_minusViewer.setFitWidth(GRID_FACTOR - (TILE_PADDING * 2));
		_minusViewer.setLayoutX(GRID_FACTOR * 5);
		_minusViewer.setLayoutY(GRID_FACTOR * 4);
		_minusViewer.setOpacity(0);
		_minusViewer.setCache(true);
		_minusViewer.setPreserveRatio(true);
		_minusViewer.setLayoutX(initialX * GRID_FACTOR + TILE_PADDING);
		_minusViewer.setLayoutY(initialY * GRID_FACTOR + TILE_PADDING);

		_partialFlash = new FadeTransition(Duration.seconds(FEEDBACK_FLASH_DURATION), _minusViewer);
		_partialFlash.setFromValue(1.0);
		_partialFlash.setToValue(0.0);
		_partialFlash.setAutoReverse(false);
		_partialFlash.setCycleCount(1);
		_partialFlash.setOnFinished(new RemoveIconsHandler(WordAddition.Partial));

		displays.add(_minusViewer);
	}

	void playFlash(WordAddition outcome) {
	    if (!_flashable) return;

	    switch (outcome) {
            case Success:
                _root.getChildren().add(_checkViewer);
                _addedFlash.play();
                break;
            case Partial:
                _root.getChildren().add(_minusViewer);
                _partialFlash.play();
                break;
            case Failure:
                _root.getChildren().add(_xViewer);
                _failedFlash.play();
                break;
        }

        _flashable = false;
	}

	private class RemoveIconsHandler implements EventHandler<ActionEvent> {
		private final WordAddition _outcome;

		RemoveIconsHandler(WordAddition outcome) {
			_outcome = outcome;
		}

		@Override
		public void handle(ActionEvent event) {
			switch(_outcome) {
				case Success:
					_root.getChildren().remove(_checkViewer);
					break;
				case Partial:
					_root.getChildren().remove(_minusViewer);
					break;
				case Failure:
					_root.getChildren().remove(_xViewer);
					break;
			}
			_flashable = true;
			event.consume();
		}

	}

	void initialize(int x, int y) {
		initialX = x;
		initialY = y;

		setImageViewLoc(_tileViewer, x, y);
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
		return _tileViewer;
	}

	int getValue() {
		return _value;
	}

	void hide() {
		_tileViewer.setOpacity(0);
	}

	String getLetter() {
		return _letter;
	}

	void stopOverlapFlash() {
		_overlapFlash.stop();
		_overlapScale.stop();
		_tileViewer.setScaleX(1);
		_tileViewer.setScaleY(1);
		_tileViewer.setOpacity(1.0);
	}

	void resetShadow() {
		_tileViewer.setEffect(_pieceShadow);
	}

	void moveDown(String letter) {
		if (_xIndex == -1 && _yIndex == -1) {
			initialY++;
			_tileViewer.setLayoutY(initialY * GRID_FACTOR + TILE_PADDING);
			_checkViewer.setLayoutY(initialY * GRID_FACTOR + TILE_PADDING);
			_minusViewer.setLayoutY(initialY * GRID_FACTOR + TILE_PADDING);
			_xViewer.setLayoutY(initialY * GRID_FACTOR + TILE_PADDING);
		} else {
			System.out.printf("\nNOT INCREMENTED! %s had indices (%s, %s)", letter, _xIndex, _yIndex);
		}
	}
	
	void moveUp(String letter) {
		if (_xIndex == -1 && _yIndex == -1) {
			initialY--;
			_tileViewer.setLayoutY(initialY * GRID_FACTOR + TILE_PADDING);
			_checkViewer.setLayoutY(initialY * GRID_FACTOR + TILE_PADDING);
			_minusViewer.setLayoutY(initialY * GRID_FACTOR + TILE_PADDING);
			_xViewer.setLayoutY(initialY * GRID_FACTOR + TILE_PADDING);
		} else {
			System.out.printf("\nNOT INCREMENTED! %s had indices (%s, %s)", letter, _xIndex, _yIndex);
		}
	}
	
	boolean isOnBoard() {
		return _scrabbleGame.played().contains(Tile.this);
	}
}