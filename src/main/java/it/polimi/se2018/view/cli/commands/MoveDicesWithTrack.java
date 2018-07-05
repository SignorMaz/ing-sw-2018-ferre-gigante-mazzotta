package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.controller.actions.MoveDicesWithTrackAction;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.cli.InputHelper;
import it.polimi.se2018.view.cli.InputResponse;
import it.polimi.se2018.view.cli.PlayerViewCli;

import java.util.Scanner;

public class MoveDicesWithTrack implements Command {

    @Override
    public String getText() {
        return "Move up to two dices of the same color of a chosen track dice (Taglierina Manuale)";
    }

    @Override
    public void handle(PlayerViewCli view) {
        Scanner input = view.getScanner();

        System.out.println("Select a dice of the track:");
        InputResponse<Dice> trackDice = InputHelper.chooseDice(input, view.getPlayerViewBase().getTrackDices());
        if (!trackDice.isValid()) {
            return;
        }

        System.out.println("Select the first dice to move:");
        InputResponse<Position> curPositionResp1 = InputHelper.choosePosition(input, view.getPlayerViewBase().getWindowFrame());
        if (!curPositionResp1.isValid()) {
            return;
        }
        Dice dice1 = view.getPlayerViewBase().getWindowFrame().getPlacedDices().get(curPositionResp1.getValue());
        if (dice1 == null) {
            System.out.println("No dice found at the selected position");
            return;
        }
        System.out.println("Select the target position of this dice:");
        InputResponse<Position> newPositionResp1 = InputHelper.choosePosition(input, view.getPlayerViewBase().getWindowFrame());
        if (!newPositionResp1.isValid()) {
            return;
        }
        Position newPosition1 = newPositionResp1.getValue();
        Position curPosition1 = curPositionResp1.getValue();

        System.out.println("Select the second dice to move (don't insert anything to skip this):");
        InputResponse<Position> curPositionResp2 = InputHelper.choosePosition(input, view.getPlayerViewBase().getWindowFrame());
        Position curPosition2 = null;
        Position newPosition2 = null;
        Dice dice2 = null;
        if (curPositionResp2.isValid()) {
            curPosition2 = curPositionResp2.getValue();
            System.out.println("Select the target position of this dice:");
            InputResponse<Position> newPositionResp2 = InputHelper.choosePosition(input, view.getPlayerViewBase().getWindowFrame());
            if (!newPositionResp2.isValid()) {
                return;
            }
            dice2 = view.getPlayerViewBase().getWindowFrame().getPlacedDices().get(curPosition2);
            if (dice2 == null) {
                System.out.println("No dice found at the selected position");
                return;
            }
            newPosition2 = newPositionResp2.getValue();
        }

        ToolCard toolcard = view.getPlayerViewBase().getToolCard();
        if (!toolcard.canMoveTwoPlacedDicesWithTrack() || dice1.equals(dice2) || newPosition1.equals(newPosition2)) {
            System.out.println("The move is not valid");
        } else {
            view.getPlayerViewBase().send(new MoveDicesWithTrackAction(trackDice.getValue(),
                    curPosition1, newPosition1, curPosition2, newPosition2));
        }
    }

    @Override
    public boolean canPerform(PlayerViewCli view) {
        ToolCard toolCard = view.getPlayerViewBase().getToolCard();
        return view.isMyTurn() && toolCard != null && toolCard.canMoveTwoPlacedDicesWithTrack();
    }
}
