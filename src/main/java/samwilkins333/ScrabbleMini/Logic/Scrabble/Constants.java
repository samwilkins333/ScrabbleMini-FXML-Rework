package main.java.samwilkins333.ScrabbleMini.Logic.Scrabble;

import javafx.scene.paint.Color;
import main.resources.ResourceLoader;
import main.resources.ResourceType;

import java.util.HashMap;

class Constants {
	
	static final PlayerType PLAYER_ONE_START_STATE = PlayerType.AI;
	static final PlayerType PLAYER_TWO_START_STATE = PlayerType.Human;

	static final HashMap<Integer, TileData> TILE_DATA;
	static {
		TILE_DATA = new HashMap<>();

		TILE_DATA.put(0, new TileData("BLANK", 2, 0));
		TILE_DATA.put(1, new TileData("A", 9, 1));
		TILE_DATA.put(2, new TileData("B", 2, 3));
		TILE_DATA.put(3, new TileData("C", 2, 3));
		TILE_DATA.put(4, new TileData("D", 4, 2));
		TILE_DATA.put(5, new TileData("E", 12, 1));
		TILE_DATA.put(6, new TileData("F", 2, 4));
		TILE_DATA.put(7, new TileData("G", 3, 2));
		TILE_DATA.put(8, new TileData("H", 2, 4));
		TILE_DATA.put(9, new TileData("I", 9, 1));
		TILE_DATA.put(10, new TileData("J", 1, 8));
		TILE_DATA.put(11, new TileData("K", 1, 5));
		TILE_DATA.put(12, new TileData("L", 4, 1));
		TILE_DATA.put(13, new TileData("M", 2, 3));
		TILE_DATA.put(14, new TileData("N", 6, 1));
		TILE_DATA.put(15, new TileData("O", 8, 1));
		TILE_DATA.put(16, new TileData("P", 2, 3));
		TILE_DATA.put(17, new TileData("Q", 1, 10));
		TILE_DATA.put(18, new TileData("R", 6, 1));
		TILE_DATA.put(19, new TileData("S", 4, 1));
		TILE_DATA.put(20, new TileData("T", 6, 1));
		TILE_DATA.put(21, new TileData("U", 4, 1));
		TILE_DATA.put(22, new TileData("V", 2, 4));
		TILE_DATA.put(23, new TileData("W", 2, 4));
		TILE_DATA.put(24, new TileData("X", 1, 8));
		TILE_DATA.put(25, new TileData("Y", 2, 4));
		TILE_DATA.put(26, new TileData("Z", 1, 10));
	}

	static final String A = "_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	static final int GRID_FACTOR = 42;
	static final int SCENE_WIDTH = (GRID_FACTOR + 5) * 31;
	static final int SCENE_HEIGHT = (GRID_FACTOR + 5) * 17 + 10;
	static final double BOARD_OPACITY = 1.0;
	static final int TILE_PADDING = 4;

	static final Color BOARD_FILL = Color.WHITE;
	static final Color DOUBLE_WORD_SCORE = Color.web("#E71308"); //RED
	static final Color DOUBLE_LETTER_SCORE = Color.web("#3333FF"); //BLUE
	static final Color TRIPLE_WORD_SCORE = Color.web("#FF9900"); //ORANGE
	static final Color TRIPLE_LETTER_SCORE = Color.web("#00CC00"); //GREEN
	static final Color SHADOW_FILL = Color.web("#000000"); //GREY

	static final int ZEROETH_ROW_OFFSET = 3;
	static final int ZEROETH_COLUMN_OFFSET = 13;

	static final int ORIGIN_LEFT = ZEROETH_COLUMN_OFFSET * GRID_FACTOR;
	static final int ORIGIN_TOP = ZEROETH_ROW_OFFSET * GRID_FACTOR;

	static final int COLLECTION_VERTICAL_OFFSET = 7;
	static final int COLLECTION_ONE_HORIZONTAL_OFFSET = ZEROETH_COLUMN_OFFSET - 2;
	static final int COLLECTION_TWO_HORIZONTAL_OFFSET = ZEROETH_COLUMN_OFFSET + 16;

	static final double LABEL_ANIMATION = 0.3;
	static final double FADED_TILE_OPACITY = 0.0;
	static final double FEEDBACK_FLASH_DURATION = 0.5;
	static final double DRAW_INTERVAL = 0.7;
	static final double SCORE_FADE_DURATION = 0.08;
	
	static final Color GRAPHITE = Color.web("#35373c");
	static final Color GREEN = Color.web("#198c19");
	static final Color RED = Color.web("#bf0000");
	
	static final double FONT_SIZE_POST_IT = 40;
	static final double FONT_SIZE_WORD_LIST = 35;

	static final String DOMESTIC_MANNERS = ResourceLoader.instance.load(ResourceType.FONT, "domesticmanners.ttf");

	static final double PLACEMENT_DURATION = 0.6;
	static final double FLASH_SPACING_DURATION = 0.6;
	static final double AI_ROTATE_DURATION = 0.1;
}