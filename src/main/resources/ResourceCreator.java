package main.resources;

import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

/**
 * A convenience class that builds off of the project's
 * <code>ResourceLoader</code> instance to streamline the
 * initialization of common resources.
 */
public final class ResourceCreator {

  //prevents instantiation
  private ResourceCreator() { }

  /**
   * Creates an <code>Image</code> instance by loading
   * the file at the specified location.
   * @param url the relative path to the image within the
   *            resources/images directory
   * @return a new <code>Image</code> instance if the url corresponds
   * to a valid file, or null otherwise.
   */
  public static Image image(String url) {
    URL loaded = ResourceLoader.instance.load(ResourceType.IMAGE, url);
    return new Image(loaded.toExternalForm());
  }

  /**
   * Creates a <code>BufferedReader</code> instance
   * by loading the text file at the specified location.
   * @param url the relative path to the text file within the
   *            resources/configuration directory
   * @return a new <code>BufferedReader</code> instance if the url
   * corresponds to valid file
   * @throws FileNotFoundException thrown if the url does not correspond
   * to a valid file in the configuration directory.
   */
  public static BufferedReader read(String url) throws FileNotFoundException {
    URL loaded = ResourceLoader.instance.load(ResourceType.CONFIG, url);
    return new BufferedReader(new FileReader(loaded.getFile()));
  }

}
