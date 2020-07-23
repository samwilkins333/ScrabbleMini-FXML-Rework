package main.resources;

import java.net.URL;

/**
 * Uses the class's location inside the
 * resources directory to load paths relative
 * to the resource directory via getClass().
 */
public final class ResourceLoader {
  public static ResourceLoader instance = new ResourceLoader();

  private ResourceLoader() {
    //prevents instantiation
  }

  /**
   * Prepends the ResourceType enum's string value as a child directory
   * to the remaining relative path to arrive at a full path relative to
   * the resources directory.
   *
   * @param type         the type corresponding to a specific first-level child folder
   * @param relativePath the remaining relative path to the desired resource
   *                     within this child folder
   * @return the URL associated with the desired resource, or
   * <code>null</code> if otherwise
   */
  public URL load(ResourceType type, String relativePath) {
    String url = String.format("%s/%s", type, relativePath);
    return getClass().getResource(url);
  }

}
