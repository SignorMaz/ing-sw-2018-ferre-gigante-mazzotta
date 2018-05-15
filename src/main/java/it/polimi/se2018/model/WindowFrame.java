package it.polimi.se2018.model;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class WindowFrame {
    private static final Logger LOGGER = Logger.getLogger("WindowFrame");
    private final Map<Position, Dice> placedDices = new HashMap<>();
    private final WindowPattern windowPattern;

    public WindowFrame(WindowPattern windowPattern) {
        this.windowPattern = windowPattern;
    }

    public WindowPattern getWindowPattern() {
        return windowPattern;
    }

    public Map<Position, Dice> getPlacedDices() {
        return placedDices;
    }

    public void placeDice(Dice dice, Position position) {
        placeDice(dice, position, null);
    }

    public void placeDice(Dice dice, Position position, ToolCard toolCard) {
        if (!isPositionValid(dice, position, toolCard)) {
            throw new IllegalArgumentException("The new position is not valid");
        }
        placedDices.put(position, dice);
    }

    public boolean isPositionValid(Dice dice, Position position, ToolCard toolCard) {
        boolean ignoreColor = toolCard != null && toolCard.ignoreColor();
        boolean ignoreNumber = toolCard != null && toolCard.ignoreNumber();
        boolean notAdjacent = toolCard != null && toolCard.notAdjacent();

        // The position doesn't exist
        if (windowPattern.getPattern().get(position) == null) {
            LOGGER.info("Position " + position + " does not exit");
            return false;
        }

        // The position is already occupied
        if (placedDices.get(position) != null) {
            LOGGER.info("Position " + position + " already occupied");
            return false;
        }

        if (placedDices.size() == 0) {
            // First dice: edge or corner
            if (position.row != 0 && position.row != 3 && position.column != 0 && position.column != 4) {
                LOGGER.info("First dice rule, " + position + " is not valid");
                return false;
            }
        } else {
            // Every dice must be adjacent to an already placed dice
            boolean foundAdjacent = false;
            for (int row = -1; row < 2; row++) {
                if (position.row + row < 0) {
                    continue;
                }
                for (int col = -1; col < 2; col++) {
                    if (position.column + col < 0) {
                        continue;
                    }
                    Position positionToCheck = new Position(position.row + row, position.column + col);
                    Dice placedDice = placedDices.get(positionToCheck);
                    if (placedDice != null) {
                        foundAdjacent = true;

                        // Orthogonally adjacent dices must have different color and number
                        if (position.row == positionToCheck.row || position.column == positionToCheck.column) {
                            if ((!ignoreColor && placedDice.getColor() == dice.getColor()) ||
                                    (!ignoreNumber && placedDice.getNumber() == dice.getNumber())) {
                                LOGGER.info("Orthogonally adjacent dice rule, " + position + " is not valid");
                                return false;
                            }
                        }
                    }
                }
            }
            if (!foundAdjacent && !notAdjacent) {
                LOGGER.info("Adjacent rule " + "(foundAdjacent=" + foundAdjacent + ", notAdjacent=" + notAdjacent + "), " + position + " is not valid");
                return false;
            }
        }

        // Every dice must respect the restrictions of the position
        Color positionColor = windowPattern.getPattern().get(position).color;
        int positionNumber = windowPattern.getPattern().get(position).number;
        if (!ignoreColor && positionColor != Color.BLANK && dice.getColor() != positionColor) {
            LOGGER.info("Color rule, " + position + " is not valid");
            return false;
        }
        if (!ignoreNumber && positionNumber != 0 && dice.getNumber() != positionNumber) {
            LOGGER.info("Number rule, " + position + " is not valid");
            return false;
        }

        return true;
    }
}
