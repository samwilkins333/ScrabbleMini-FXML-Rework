package main.java.samwilkins333.ScrabbleMini.Logic.Scrabble;

import javafx.scene.image.Image;
import main.java.samwilkins333.ScrabbleMini.FXML.Utilities.Image.ImageHelper;

class Alpha {
    private final String _letter;
    private final int frequency;
    private final int _value;
    private final Image _image;

    Alpha(String let, int frequency, int val) {
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

    Image getImage() {
        return _image;
    }
}
