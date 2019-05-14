package main.java.samwilkins333.ScrabbleMini.Logic.Scrabble;

import javafx.scene.image.ImageView;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.ImageHelper;

class TileData {
    private final String _letter;
    private final int frequency;
    private final int _value;
    private final ImageView _image;

    TileData(String let, int frequency, int val) {
        _letter = let;
        this.frequency = frequency;
        _value = val;
        _image = ImageHelper.create("Scrabble Tiles/" + let.toLowerCase() + ".png");
    }

    String getLetter() {
        return _letter;
    }

    int getValue() {
        return _value;
    }

    int getFrequency() {
        return frequency;
    }

    ImageView getImage() {
        return _image;
    }
}
