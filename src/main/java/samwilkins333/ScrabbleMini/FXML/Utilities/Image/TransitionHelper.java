
package main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

public final class TransitionHelper {

  public static FadeTransition flash(Node target, double pulseDuration, int cycles) {
    FadeTransition flash = new FadeTransition(Duration.seconds(pulseDuration), target);
    flash.setFromValue(1.0);
    flash.setToValue(0.0);
    flash.setAutoReverse(true);
    flash.setCycleCount(cycles);
    return flash;
  }

  public static ScaleTransition scale(Node target, double duration, double byX, double byY) {
    ScaleTransition scale = new ScaleTransition(Duration.seconds(duration), target);
    scale.setByX(byX);
    scale.setByY(byY);
    scale.setAutoReverse(true);
    return scale;
  }

  public static PauseTransition pause(double duration, EventHandler<ActionEvent> onFinished) {
    PauseTransition pause = new PauseTransition(Duration.seconds(duration));
    pause.setOnFinished(onFinished);
    return pause;
  }
}