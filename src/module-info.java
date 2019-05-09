module ScrabbleMini {
  requires javafx.graphics;
  requires javafx.base;
  requires javafx.controls;
  requires javafx.fxml;
  requires kotlin.stdlib;

  exports main.java.samwilkins333.ScrabbleMini.Display.FXMLControllers;

  opens main.java.samwilkins333.ScrabbleMini;
}