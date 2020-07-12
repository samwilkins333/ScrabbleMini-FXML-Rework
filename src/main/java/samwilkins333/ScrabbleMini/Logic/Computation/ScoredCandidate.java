package main.java.samwilkins333.ScrabbleMini.Logic.Computation;

import java.util.List;
import java.util.stream.Collectors;

public class ScoredCandidate {

  private final List<TilePlacement> placements;
  private final DirectionName direction;
  private final int score;

  public ScoredCandidate(List<TilePlacement> placements, DirectionName direction, int score) {
    this.placements = placements;
    this.direction = direction;
    this.score = score;
  }

  public List<TilePlacement> getPlacements() {
    return placements;
  }

  public DirectionName getDirection() {
    return direction;
  }

  public int getScore() {
    return score;
  }

  @Override
  public String toString() {
    return this.placements.stream().map(p -> String.format("%c(%d, %d)", p.getTile().getLetter(), p.getX(), p.getY())).collect(Collectors.joining(" "));
  }
}
