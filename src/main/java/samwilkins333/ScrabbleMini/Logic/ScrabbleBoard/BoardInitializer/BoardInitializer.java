package main.java.samwilkins333.ScrabbleMini.Logic.ScrabbleBoard.BoardInitializer;

import javafx.geometry.Point2D;
import main.java.samwilkins333.ScrabbleMini.Logic.ScrabbleBoard.Multiplier;

import java.util.Map;

public interface BoardInitializer<D, C> {
  Map<Multiplier, C> initialize(Map<Point2D, D> customValueMapping, int boardSize);
}
