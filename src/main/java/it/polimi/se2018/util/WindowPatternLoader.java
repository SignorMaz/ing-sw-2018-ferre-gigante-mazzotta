package it.polimi.se2018.util;

import it.polimi.se2018.model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WindowPatternLoader {
    private static final Logger LOGGER = Logger.getLogger("WindowPatternLoader");

    private WindowPatternLoader() {
    }

    private static Color colorNameToEnum(String colorName) {
        // We can use just valueOf() since we use the same name, but lowercase
        return Color.valueOf(colorName.toUpperCase());
    }

    private static Map<Position, WindowCell> parsePattern(JSONArray jsonMap) throws JSONException {
        final Map<Position, WindowCell> pattern = new HashMap<>();
        for (int i = 0; i < jsonMap.length(); i++) {
            JSONArray columnArray = jsonMap.getJSONArray(i);
            for (int j = 0; j < columnArray.length(); j++) {
                JSONObject cell = columnArray.getJSONObject(j);
                Color color = colorNameToEnum(cell.getString("color"));
                int number = cell.getInt("number");
                WindowCell windowCell = new WindowCell(color, number);
                Position position = new Position(i, j);
                pattern.put(position, windowCell);
            }
        }
        return pattern;
    }

    public static WindowPatternCard load(final String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);

        JSONObject frontObject = jsonObject.getJSONObject("front");
        final Map<Position, WindowCell> frontPattern = parsePattern(frontObject.getJSONArray("map"));
        final int frontDifficulty = frontObject.getInt("difficulty");
        final String frontName = frontObject.getString("name");
        WindowPattern windowPatternFront = new WindowPattern() {
            @Override
            public String getName() {
                return frontName;
            }

            @Override
            public int getDifficulty() {
                return frontDifficulty;
            }

            @Override
            public Map<Position, WindowCell> getPattern() {
                return frontPattern;
            }
        };

        JSONObject backObject = jsonObject.getJSONObject("back");
        final Map<Position, WindowCell> backPattern = parsePattern(backObject.getJSONArray("map"));
        final int backDifficulty = backObject.getInt("difficulty");
        final String backName = backObject.getString("name");
        WindowPattern windowPatternBack = new WindowPattern() {
            @Override
            public String getName() {
                return backName;
            }

            @Override
            public int getDifficulty() {
                return backDifficulty;
            }

            @Override
            public Map<Position, WindowCell> getPattern() {
                return backPattern;
            }
        };

        return new WindowPatternCard() {
            @Override
            public WindowPattern getFront() {
                return windowPatternFront;
            }

            @Override
            public WindowPattern getBack() {
                return windowPatternBack;
            }
        };
    }

    private static String streamToString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    public static WindowPatternCard loadFromResource(String resourceName) {
        InputStream is = WindowPatternLoader.class.getResourceAsStream("/windowpatterns/" + resourceName);
        try {
            return load(streamToString(is));
        } catch (JSONException | IOException e) {
            // This should never happen since we are loading from a resource
            // file that we already tested.
            LOGGER.log(Level.SEVERE, "Could not log the map", e);
            return null;
        }
    }

}
