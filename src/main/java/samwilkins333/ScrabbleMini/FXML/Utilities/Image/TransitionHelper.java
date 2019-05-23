
package main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image;

import javafx.animation.TranslateTransition;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.PauseTransition;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * A convenience class that acts as a factory
 * for <code>Transition</code>s.
 */
public final class TransitionHelper {
  private TransitionHelper() {
    //prevents instantiation
  }

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

  public static RotateTransition rotate(Node target, double duration, double byAngle, int cycles, boolean autoreverse) {
    RotateTransition rotate
            = new RotateTransition(Duration.seconds(duration), target);
    rotate.setByAngle(byAngle);
    rotate.setCycleCount(cycles);
    rotate.setAutoReverse(autoreverse);
    return rotate;
  }

  public static TranslateTransition translate(Node target, double duration, double byX, double byY) {
    TranslateTransition translate =
            new TranslateTransition(Duration.seconds(duration), target);
    translate.setByX(byX);
    translate.setByY(byY);
    return translate;
  }
}