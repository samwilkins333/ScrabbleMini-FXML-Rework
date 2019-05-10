module ScrabbleMini {
  requires javafx.graphics;
  requires javafx.base;
  requires javafx.controls;
  requires javafx.fxml;
  requires kotlin.stdlib;

  exports main.java.samwilkins333.ScrabbleMini.FXML.Controllers;
  exports main.java.samwilkins333.ScrabbleMini.Logic.Scrabble;

  opens main.java.samwilkins333.ScrabbleMini;
}