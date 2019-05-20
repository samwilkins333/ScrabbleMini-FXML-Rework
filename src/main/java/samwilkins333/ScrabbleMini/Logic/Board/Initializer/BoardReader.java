package main.java.samwilkins333.ScrabbleMini.Logic.Board.Initializer;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import main.java.samwilkins333.ScrabbleMini.Logic.Board.Multiplier;
import main.java.samwilkins333.ScrabbleMini.Main;
import main.resources.ResourceCreator;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BoardReader implements BoardInitializer<Multiplier, Paint> {

  private static final String CONFIG_FILE = "board_config.txt";

  private static final String SIZING_DELIMITER = " squares @ ";
  private static final String PAIR_DELIMITER = " _ ";
  private static final String VALUE_DELIMITER = ",";
  private static final String UNIT = "px";

  private static final Pattern SIZING = Pattern.compile("\\d+" + SIZING_DELIMITER + "\\d+px");
  private static final Pattern CONTROL = Pattern.compile("( _ )?(\\d+,\\d+)");
  private static final Pattern COLOR_MAPPING = Pattern.compile("\\d+,\\d+ _ #[A-Za_z0-9]{3,6}");

  private static final String PREFIX = "Invalid boardPane configuration! ";

  private static final String INVALID_HEADER =  PREFIX + "The given file's header is invalid.";
  private static final String INVALID_SIZING = PREFIX + "The given file's dimensions are invalid.";
  private static final String INSUFFICIENT_PAIRS = PREFIX + " Line %d did not contain %d value pairs.";
  private static final String INSUFFICIENT_ROWS = PREFIX + "File specifies only %d of the required %s rows.";
  private static final String COLORS_ABSENT = PREFIX + "No color mapping header encountered.";
  private static final String INVALID_MAPPING = PREFIX + "Color mapping entry improperly formatted.";
  private static final String EXCESS_INFO = PREFIX + "The file contains more than the necessary information.";

  @Override
  public BoardAttributes<Multiplier, Paint> initialize() {
    int squareCount = 0;
    int squareSize = 0;
    Multiplier[][] multiplierMapping = new Multiplier[15][15];
    Map<Multiplier, Paint> colorMapping = new HashMap<>();

    try {
      Set<Multiplier> multipliers = new HashSet<>();
      int row = 0;
      BufferedReader reader = ResourceCreator.read(CONFIG_FILE);

      if (!reader.readLine().trim().equals("SCRABBLE BOARD CONFIGURATION"))
        throw new IOException(INVALID_HEADER);

      reader.readLine();
      String sizing;
      if (!SIZING.matcher((sizing = reader.readLine().trim())).find())
        throw new IOException(INVALID_SIZING);

      String[] sizes = sizing.split(SIZING_DELIMITER);
      squareCount = Integer.valueOf(sizes[0]);
      squareSize = Integer.valueOf(sizes[1].replace(UNIT, ""));
      multiplierMapping = new Multiplier[squareCount][squareCount];

      reader.readLine();
      String line;
      while (!(line = reader.readLine().trim()).equals("")) {
        int pairs = 0;
        Matcher controller = CONTROL.matcher(line);
        while (controller.find()) pairs++;

        if (pairs != squareCount)
          throw new IOException(String.format(INSUFFICIENT_PAIRS, row + 1, squareCount));

        String[] values = line.split(PAIR_DELIMITER);

        if (values.length != squareCount)
          throw new IOException(String.format(INSUFFICIENT_PAIRS, row + 1, squareCount));

        int column = 0;
        for (String pair : values) {
          Multiplier multiplier = Multiplier.parse(pair, VALUE_DELIMITER);
          multipliers.add(multiplier);
          multiplierMapping[column][row] = multiplier;
          column++;
        }
        row++;
      }

      if (row != squareCount)
        throw new IOException(String.format(INSUFFICIENT_ROWS, row, squareCount));

      if (!Pattern.compile("COLORS").matcher(reader.readLine().trim()).find())
        throw new IOException(COLORS_ABSENT);

      reader.readLine();
      String entry;
      for (int i = 0; i < multipliers.size(); i++) {
        entry = reader.readLine();
        if (entry == null || !COLOR_MAPPING.matcher(entry.trim()).find())
          throw new IOException(INVALID_MAPPING);
        String[] mapping = entry.split(PAIR_DELIMITER);
        Multiplier read = Multiplier.parse(mapping[0].trim(), VALUE_DELIMITER);
        Paint color = Color.web(mapping[1].trim());
        colorMapping.put(read, color);
      }

      String trailing;
      while ((trailing = reader.readLine()) != null) {
        if (!trailing.trim().isEmpty()) throw new IOException(EXCESS_INFO);
      }

    } catch (IOException e) {
      e.printStackTrace();
      Main.exit(null);
    }

    return new BoardAttributes<>(squareCount, squareSize, multiplierMapping, colorMapping);
  }

}
