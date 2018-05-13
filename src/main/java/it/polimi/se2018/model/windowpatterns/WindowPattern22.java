package it.polimi.se2018.model.windowpatterns;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.WindowCell;
import it.polimi.se2018.model.WindowPattern;

import java.util.HashMap;
import java.util.Map;

public class WindowPattern22 implements WindowPattern {

    private final Map<Position, WindowCell> map = new HashMap<>();

    public WindowPattern22() {
        map.put(new Position(0, 0), new WindowCell(Color.BLANK, 6));
        map.put(new Position(1, 0), new WindowCell(Color.BLUE, 0));
        map.put(new Position(2, 0), new WindowCell(Color.BLANK, 0));
        map.put(new Position(3, 0), new WindowCell(Color.BLANK, 0));
        map.put(new Position(4, 0), new WindowCell(Color.BLANK, 1));

        map.put(new Position(0, 1), new WindowCell(Color.BLANK, 0));
        map.put(new Position(1, 1), new WindowCell(Color.BLANK, 5));
        map.put(new Position(2, 1), new WindowCell(Color.BLUE, 0));
        map.put(new Position(3, 1), new WindowCell(Color.BLANK, 0));
        map.put(new Position(4, 1), new WindowCell(Color.BLANK, 0));

        map.put(new Position(0, 2), new WindowCell(Color.BLANK, 4));
        map.put(new Position(1, 2), new WindowCell(Color.RED, 0));
        map.put(new Position(2, 2), new WindowCell(Color.BLANK, 2));
        map.put(new Position(3, 2), new WindowCell(Color.BLUE, 0));
        map.put(new Position(4, 2), new WindowCell(Color.BLANK, 0));

        map.put(new Position(0, 3), new WindowCell(Color.GREEN, 0));
        map.put(new Position(1, 3), new WindowCell(Color.BLANK, 6));
        map.put(new Position(2, 3), new WindowCell(Color.YELLOW, 0));
        map.put(new Position(3, 3), new WindowCell(Color.BLANK, 3));
        map.put(new Position(4, 3), new WindowCell(Color.PURPLE, 0));
    }

    @Override
    public String getName() {
        return "Water of Life";
    }

    @Override
    public int getDifficulty() {
        return 6;
    }

    @Override
    public Map<Position, WindowCell> getPattern() {
        return map;
    }
}
