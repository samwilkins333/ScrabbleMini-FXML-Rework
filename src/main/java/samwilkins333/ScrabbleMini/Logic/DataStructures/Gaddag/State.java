package main.java.samwilkins333.ScrabbleMini.Logic.DataStructures.Gaddag;

import java.util.Map;
import java.util.TreeMap;

public class State {
  private Map<ArcLetter, Arc> outgoingArcs = new TreeMap<>();

  class MalformedGADDAGException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MalformedGADDAGException(ArcLetter letter,
                                    State wrongDest,
                                    State rightDest) {
      super("Tried to force arc for " + letter + " from "
              + State.this.toString() + " to " + rightDest.toString()
              + ", but an arc already exists going to " + wrongDest.toString()
              + ".");
    }
  }
  
  public Arc getArc(ArcLetter letter) {
    return outgoingArcs.get(letter);
  }

  Arc addArc(ArcLetter letter) {
    Arc arc = getArc(letter);
    if (arc == null) {
      arc = new Arc(new State());
      outgoingArcs.put(letter, arc);
    }
    return arc;
  }

  Arc forceArc(ArcLetter letter, State destination) {
    Arc arc = getArc(letter);
    if (arc == null) {
      arc = new Arc(destination);
      outgoingArcs.put(letter, arc);
    } else {
      State arcDestination = arc.destination();
      if (!arcDestination.equals(destination)) {
        throw new MalformedGADDAGException(letter, arcDestination, destination);
      }
    }
    return arc;
  }

}
