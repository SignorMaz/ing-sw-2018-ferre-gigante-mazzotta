package it.polimi.se2018.model.windowpatterns;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.WindowCell;
import it.polimi.se2018.model.WindowPattern;

import java.util.HashMap;
import java.util.Map;

public class WindowPattern12 implements WindowPattern {

    private final Map<Position, WindowCell> map = new HashMap<>();

    public WindowPattern12() {
        map.put(new Position(0, 0), new WindowCell(Color.PURPLE, 0));
        map.put(new Position(1, 0), new WindowCell(Color.BLANK, 6));
        map.put(new Position(2, 0), new WindowCell(Color.BLANK, 0));
        map.put(new Position(3, 0), new WindowCell(Color.BLANK, 0));
        map.put(new Position(4, 0), new WindowCell(Color.BLANK, 3));

        map.put(new Position(0, 1), new WindowCell(Color.BLANK, 5));
        map.put(new Position(1, 1), new WindowCell(Color.PURPLE, 0));
        map.put(new Position(2, 1), new WindowCell(Color.BLANK, 3));
        map.put(new Position(3, 1), new WindowCell(Color.BLANK, 0));
        map.put(new Position(4, 1), new WindowCell(Color.BLANK, 0));

        map.put(new Position(0, 2), new WindowCell(Color.BLANK, 0));
        map.put(new Position(1, 2), new WindowCell(Color.BLANK, 2));
        map.put(new Position(2, 2), new WindowCell(Color.PURPLE, 0));
        map.put(new Position(3, 2), new WindowCell(Color.BLANK, 1));
        map.put(new Position(4, 2), new WindowCell(Color.BLANK, 0));

        map.put(new Position(0, 3), new WindowCell(Color.BLANK, 0));
        map.put(new Position(1, 3), new WindowCell(Color.BLANK, 1));
        map.put(new Position(2, 3), new WindowCell(Color.BLANK, 5));
        map.put(new Position(3, 3), new WindowCell(Color.PURPLE, 0));
        map.put(new Position(4, 3), new WindowCell(Color.BLANK, 4));
    }

    @Override
    public String getName() {
        return "Firmitas";
    }

    @Override
    public int getDifficulty() {
        return 5;
    }

    @Override
    public Map<Position, WindowCell> getPattern() {
        return map;
    }
}
