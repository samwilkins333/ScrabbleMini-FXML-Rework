package main.resources;

import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ResourceCreator {

  public static Image image(String url) {
    return new Image(ResourceLoader.instance.load(ResourceType.IMAGE, url).toExternalForm());
  }

  public static BufferedReader read(String url) throws FileNotFoundException {
    return new BufferedReader(new FileReader(ResourceLoader.instance.load(ResourceType.CONFIG, url).getFile()));
  }

}
