package main.resources;

/**
 * A repository enumerating all
 * immediate child folders (and
 * correspondingly file types) of the
 * resources directory.
 */
public enum ResourceType {
  IMAGE("images"),
  FONT("fonts"),
  CONFIG("configuration");

  private final String text;

  ResourceType(final String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
