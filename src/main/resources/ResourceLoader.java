package main.resources;

import java.net.URL;

public class ResourceLoader {
  public static ResourceLoader instance = new ResourceLoader();

  private ResourceLoader() {}

  public URL load(ResourceType type, String relativePath) {
    String url = String.format("%s/%s", type, relativePath);
    return getClass().getResource(url);
  }

}
