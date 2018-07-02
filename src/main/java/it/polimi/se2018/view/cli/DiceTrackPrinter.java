package it.polimi.se2018.view.cli;

import it.polimi.se2018.model.Dice;

import java.util.List;

public class DiceTrackPrinter {
    private static final int MAX_TRACK_DICES = 10;

    private DiceTrackPrinter() {
    }

    public static String buildString(List<Dice> trackDices) {
        StringBuilder sb = new StringBuilder();
        sb.append("┌");
        for (int i = 0; i < MAX_TRACK_DICES - 1; i++) {
            sb.append("──┬");
        }
        sb.append("──┐");
        sb.append("\n");

        sb.append("│");
        for (int i = 0; i < MAX_TRACK_DICES; i++) {
            if (trackDices != null && trackDices.size() > i) {
                Dice dice = trackDices.get(i);
                sb.append(dice.getNumber());
                sb.append(dice.getColor().toString().charAt(0));
            } else {
                sb.append("  ");
            }
            sb.append("│");
        }
        sb.append("\n");

        sb.append("└");
        for (int i = 0; i < MAX_TRACK_DICES - 1; i++) {
            sb.append("──┴");
        }
        sb.append("──┘");
        sb.append("\n");

        return sb.toString();
    }

    public static void print(List<Dice> trackDices) {
        System.out.println(buildString(trackDices));
    }
}