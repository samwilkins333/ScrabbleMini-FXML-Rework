package main.java.samwilkins333.ScrabbleMini.Logic.Elements.Tiles.Initializer;

import main.java.samwilkins333.ScrabbleMini.Main;
import main.resources.ResourceCreator;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * An implementer of <code>TileBagInitializer</code> that
 * reads the appropriate information from a tilebag_config.txt
 * file in the project's configuration folder.
 */
public class TileBagReader implements TileBagInitializer {

  private static final String CONFIG_FILE = "tilebag_config.txt";
  private static final String VALUE_DELIMITER = ", ";

  private static final Pattern VALIDATOR =
          Pattern.compile("[A-Z_], \\d+, \\d+");

  private static final String PREFIX =
          "Invalid tile configuration! ";
  private static final String INVALID_HEADER =
          PREFIX + "The given file's header is invalid.";
  private static final String INVALID_MAPPING =
          PREFIX + "Line %d's specifications do not match requirements.";
  private static final String DUPLICATE_MAPPING =
          PREFIX + "The character %s already occupied a specified mapping.";
  private static final String EXCESS_INFO =
          PREFIX + "The file contains more than the necessary information.";

  @Override
  public TileBagAttributes initialize() {
    Map<String, TileMetaData> metadataMapping = new HashMap<>();
    Set<String> encountered = new HashSet<>();

    try {
      BufferedReader reader = ResourceCreator.read(CONFIG_FILE);

      String line = reader.readLine().trim();
      if (!line.equals("SCRABBLE TILE CONFIGURATION")) {
        throw new IOException(INVALID_HEADER);
      }

      reader.readLine();
      int count = 1;
      while ((line = reader.readLine()) != null && !line.isEmpty()) {
        line = line.trim();
        if (!VALIDATOR.matcher(line).find()) {
          throw new IOException(String.format(INVALID_MAPPING, count));
        }

        String[] components = line.split(VALUE_DELIMITER);
        String letter = components[0].toLowerCase();

        if (encountered.contains(letter)) {
          throw new IOException(DUPLICATE_MAPPING);
        }

        int frequency = Integer.valueOf(components[1]);
        int score = Integer.valueOf(components[2]);

        encountered.add(letter);
        metadataMapping.put(letter, new TileMetaData(score, frequency));
        count++;
      }

      if (line != null) {
        String trailing;
        while ((trailing = reader.readLine()) != null) {
          if (!trailing.trim().isEmpty()) {
            throw new IOException(EXCESS_INFO);
          }
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
      Main.exit(null);
    }

    return new TileBagAttributes(metadataMapping);
  }
}
