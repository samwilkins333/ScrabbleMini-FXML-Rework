package main.java.samwilkins333.ScrabbleMini.Logic.Board.Initializer;

import java.util.Map;

public interface BoardInitializer<D, C> {
  BoardAttributes<D, C> initialize();

  class BoardAttributes<D, C> {
    int squareCount;
    int squareSize;
    D[][] locationMapping;
    Map<D, C> attributeMapping;

    BoardAttributes(int squareCount, int squareSize, D[][] locationMapping, Map<D, C> attributeMapping) {
      this.squareCount = squareCount;
      this.squareSize = squareSize;
      this.locationMapping = locationMapping;
      this.attributeMapping = attributeMapping;
    }

    public int squareCount() {
      return squareCount;
    }

    public int squareSize() {
      return squareSize;
    }

    public D[][] locationMapping() {
      return locationMapping;
    }

    public Map<D, C> attributeMapping() {
      return attributeMapping;
    }
  }
}
