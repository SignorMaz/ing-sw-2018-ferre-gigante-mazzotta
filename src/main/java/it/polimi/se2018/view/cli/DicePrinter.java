package it.polimi.se2018.view.cli;

import it.polimi.se2018.model.Dice;

import java.util.List;

public class DicePrinter {
    private static final int MAX_TRACK_DICES = 10;

    private DicePrinter() {
    }

    public static String buildString(List<Dice> trackDices, int squares) {
        StringBuilder sb = new StringBuilder();
        sb.append("┌");
        for (int i = 0; i < squares - 1; i++) {
            sb.append("──┬");
        }
        sb.append("──┐");
        sb.append("\n");

        sb.append("│");
        for (int i = 0; i < squares; i++) {
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
        for (int i = 0; i < squares - 1; i++) {
            sb.append("──┴");
        }
        sb.append("──┘");
        sb.append("\n");

        return sb.toString();
    }

    public static void printTrack(List<Dice> trackDices) {
        System.out.println(buildString(trackDices, MAX_TRACK_DICES));
    }

    public static void printDraftPool(List<Dice> draftPool) {
        System.out.println(buildString(draftPool, draftPool.size()));
    }
}